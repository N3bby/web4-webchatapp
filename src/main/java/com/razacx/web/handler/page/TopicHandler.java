package com.razacx.web.handler.page;

import com.razacx.domain.model.Person;
import com.razacx.domain.model.Topic;
import com.razacx.domain.service.concrete.DomainServiceHolder;
import com.razacx.web.Action;
import com.razacx.web.ActionHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Action(value = "topic", requiresLoggedIn = true)
public class TopicHandler extends ActionHandler {

    public TopicHandler(DomainServiceHolder serviceHolder) {
        super(serviceHolder);
    }

    @Override
    public void handleImpl(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Person person = (Person) request.getSession().getAttribute("person");
        request.setAttribute("person", person);

        String topicStr = request.getParameter("topic");
        Topic topic = getServiceHolder().getTopicService().getTopicByName(topicStr);
        
        request.setAttribute("topic", topic);
        request.setAttribute("messages", topic.getMessages());

        forward(request, response, "topic.jsp");
    }

}
