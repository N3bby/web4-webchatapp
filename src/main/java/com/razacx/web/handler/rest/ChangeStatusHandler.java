package com.razacx.web.handler.rest;

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

        String username = (String) request.getSession().getAttribute("person");
        Person person = getServiceHolder().getPersonService().getPerson(username);

        String status = request.getParameter("status");

        try {
            person.setStatus(status);
            getServiceHolder().getPersonService().updatePerson(person);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
