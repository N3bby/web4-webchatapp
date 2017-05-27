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
import java.util.stream.Collectors;

@Action(value = "pmGetNew", requiresLoggedIn = true)
public class PrivateMessageGetNewHandler extends ActionHandler {

    public PrivateMessageGetNewHandler(DomainServiceHolder serviceHolder) {
        super(serviceHolder);
    }

    @Override
    public void handleImpl(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //Get current user
        String username = (String) request.getSession().getAttribute("person");
        Person person = getServiceHolder().getPersonService().getPerson(username);

        //Fetch all pms with current user as from attribute and filter out all the seen ones        
        List<PrivateMessage> pms = getServiceHolder().getMessageService().getPrivateMessagesFor(person);
        List<PrivateMessage> newPms = pms.stream()
                                         .filter(pm -> !pm.isSeen())
                                         .collect(Collectors.toList());

        //Serialize to json format
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .create();
        String json = gson.toJson(newPms);

        //Write to response
        response.getWriter().write(json);
        response.getWriter().flush();

    }

}
