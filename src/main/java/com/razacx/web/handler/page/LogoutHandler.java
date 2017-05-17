package com.razacx.web.handler.page;

import com.razacx.domain.model.Person;
import com.razacx.domain.service.concrete.DomainServiceHolder;
import com.razacx.web.Action;
import com.razacx.web.ActionHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Action(value = "logout", requiresLoggedIn = true)
public class LogoutHandler extends ActionHandler {

    public LogoutHandler(DomainServiceHolder serviceHolder) {
        super(serviceHolder);
    }

    @Override
    public void handleImpl(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String username = (String) request.getSession().getAttribute("person");        
        Person person = getServiceHolder().getPersonService().getPerson(username);
        
        person.setStatus("Offline");
        getServiceHolder().getPersonService().updatePerson(person);

        request.getSession().invalidate();
        redirect(response, "Controller?action=requestLogin");

    }

}
