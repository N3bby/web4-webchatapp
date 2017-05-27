package com.razacx.web.handler.rest;

import com.razacx.domain.model.PrivateMessage;
import com.razacx.domain.service.concrete.DomainServiceHolder;
import com.razacx.web.Action;
import com.razacx.web.ActionHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Action(value = "pmSeen", requiresLoggedIn = true)
public class PrivateMessageSeenHandler extends ActionHandler {

    public PrivateMessageSeenHandler(DomainServiceHolder serviceHolder) {
        super(serviceHolder);
    }

    @Override
    public void handleImpl(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //Get message based on id
        long id = Long.parseLong(request.getParameter("id"));
        PrivateMessage message = (PrivateMessage) getServiceHolder().getMessageService().getMessageById(id);
        
        //Set seen attribute to true and update
        message.setSeen(true);
        getServiceHolder().getMessageService().updateMessage(message);

    }
    
}
