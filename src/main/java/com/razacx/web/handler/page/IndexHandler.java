package com.razacx.web.handler.page;

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
        
        String username = (String) request.getSession().getAttribute("person");
        Person person = getServiceHolder().getPersonService().getPerson(username);
        
        request.setAttribute("person", person);
        request.setAttribute("topics", getServiceHolder().getTopicService().getAllTopics());
        forward(request, response, "index.jsp");
    }

}
