package com.razacx.web;

import com.razacx.domain.model.Person;
import com.razacx.domain.service.concrete.DomainServiceHolder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class ActionHandler {

    private final DomainServiceHolder serviceHolder;

    public ActionHandler(DomainServiceHolder serviceHolder) {
        this.serviceHolder = serviceHolder;
    }

    public void handle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (this.getClass().getAnnotation(Action.class).requiresLoggedIn()) {
            if(redirectIfNotLoggedIn(request, response)) return;
        }
        handleImpl(request, response);
    }

    public abstract void handleImpl(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

    protected void redirect(HttpServletResponse response, String url) throws ServletException, IOException {
        response.sendRedirect(url);
    }

    protected void forward(HttpServletRequest request, HttpServletResponse response, String destination) throws ServletException, IOException {
        request.getRequestDispatcher(destination).forward(request, response);
    }

    protected DomainServiceHolder getServiceHolder() {
        return serviceHolder;
    }

    //Returns true if redirected
    private boolean redirectIfNotLoggedIn(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Person person = (Person) request.getSession().getAttribute("person");
        if (person == null) {
            redirect(response, "Controller?action=requestLogin");
            return true;
        }
        return false;
    }

}
