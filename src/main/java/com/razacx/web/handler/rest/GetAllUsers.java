package com.razacx.web.handler.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.razacx.domain.model.Person;
import com.razacx.domain.service.concrete.DomainServiceHolder;
import com.razacx.web.Action;
import com.razacx.web.ActionHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Action(value = "getAllUsers", requiresLoggedIn = false)
public class GetAllUsers extends ActionHandler {

    public GetAllUsers(DomainServiceHolder serviceHolder) {
        super(serviceHolder);
    }

    @Override
    public void handleImpl(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Person> allPersons = getServiceHolder().getPersonService().getAllPersons();

        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .serializeNulls()
                .setPrettyPrinting()
                .create();

        String json = gson.toJson(allPersons);
        
        response.setContentType("application/json");
        response.getWriter().write(json);
        response.getWriter().flush();

    }
    
}
