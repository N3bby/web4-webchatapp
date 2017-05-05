package com.razacx.web.handler;

import com.razacx.domain.model.Person;
import com.razacx.domain.service.concrete.DomainServiceHolder;
import com.razacx.web.Action;
import com.razacx.web.ActionHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Action(value = "index", requiresLoggedIn = true)
public class IndexHandler extends ActionHandler {

    public IndexHandler(DomainServiceHolder serviceHolder) {
        super(serviceHolder);
    }

    public void handleImpl(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Person person = (Person) request.getSession().getAttribute("person");
        request.setAttribute("person", person);
        request.setAttribute("topics", getServiceHolder().getTopicService().getAllTopics());
        forward(request, response, "index.jsp");
    }

}
