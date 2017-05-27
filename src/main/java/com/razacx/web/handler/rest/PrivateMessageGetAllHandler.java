package com.razacx.web.handler.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.razacx.domain.model.Person;
import com.razacx.domain.model.PrivateMessage;
import com.razacx.domain.service.concrete.DomainServiceHolder;
import com.razacx.web.Action;
import com.razacx.web.ActionHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Action(value = "pmGetAll", requiresLoggedIn = true)
public class PrivateMessageGetAllHandler extends ActionHandler {

    public PrivateMessageGetAllHandler(DomainServiceHolder serviceHolder) {
        super(serviceHolder);
    }

    @Override
    public void handleImpl(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //Get users for which the pms have to be fetched
        String username = (String) request.getSession().getAttribute("person");
        Person person = getServiceHolder().getPersonService().getPerson(username);
        
        String toUsername = request.getParameter("user");        
        Person toPerson = getServiceHolder().getPersonService().getPerson(toUsername);

        //Fetch pms
        List<PrivateMessage> pms = getServiceHolder().getMessageService().getPrivateMessagesBetween(person, toPerson);

        //Serialize to json format
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .create();
        String json = gson.toJson(pms);
        
        //Write to response
        response.getWriter().write(json);
        response.getWriter().flush();

    }
    
}
