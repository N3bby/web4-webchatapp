package com.razacx.web.handler.rest;

import com.razacx.domain.model.Person;
import com.razacx.domain.model.PrivateMessage;
import com.razacx.domain.service.concrete.DomainServiceHolder;
import com.razacx.web.Action;
import com.razacx.web.ActionHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Action(value = "pmSend", requiresLoggedIn = true)
public class PrivateMessageSendHandler extends ActionHandler{

    public PrivateMessageSendHandler(DomainServiceHolder serviceHolder) {
        super(serviceHolder);
    }

    @Override
    public void handleImpl(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //Get current user
        String username = (String) request.getSession().getAttribute("person");
        Person person = getServiceHolder().getPersonService().getPerson(username);

        //Get to user
        String toUsername = request.getParameter("to");
        Person toPerson = getServiceHolder().getPersonService().getPerson(toUsername);

        //Construct new PrivateMessage and persist
        PrivateMessage message = new PrivateMessage();
        message.setFrom(person);
        message.setTo(toPerson);
        message.setMessage(request.getParameter("message"));

        getServiceHolder().getMessageService().addMessage(message);
        
    }

}
