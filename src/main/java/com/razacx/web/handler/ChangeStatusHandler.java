package com.razacx.web.handler;

import com.razacx.domain.model.Person;
import com.razacx.domain.service.concrete.DomainServiceHolder;
import com.razacx.web.Action;
import com.razacx.web.ActionHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Action(value = "changeStatus", requiresLoggedIn = true)
public class ChangeStatusHandler extends ActionHandler {

    public ChangeStatusHandler(DomainServiceHolder serviceHolder) {
        super(serviceHolder);
    }

    @Override
    public void handleImpl(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Person person = (Person) request.getSession().getAttribute("person");
        if (person == null) {
            redirect(response, "Controller?action=requestLogin");
        }

        String status = request.getParameter("status");

        try {
            person.setStatus(status);
        } catch (Exception e) {
            //TODO set status error handling
            throw new RuntimeException(e);
        }

    }

}
