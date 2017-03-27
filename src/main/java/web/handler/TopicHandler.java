package web.handler;

import domain.model.Message;
import domain.model.Person;
import domain.model.Topic;
import service.ChatService;
import web.Action;
import web.ActionHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Action(value = "topic", requiresLoggedIn = true)
public class TopicHandler extends ActionHandler {

    public TopicHandler(ChatService chatService) {
        super(chatService);
    }

    @Override
    public void handleImpl(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Person person = (Person) request.getSession().getAttribute("person");
        request.setAttribute("person", person);

        String topicStr = request.getParameter("topic");
        Topic topic = getChatService().getTopic(topicStr);
        request.setAttribute("topic", topic);

        request.setAttribute("messages", getChatService().getMessagesById(topic.getMessageIds()));

        forward(request, response, "topic.jsp");
    }

}
