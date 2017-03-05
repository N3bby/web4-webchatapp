package web.handler;

import domain.model.Person;
import service.ChatService;
import web.Action;
import web.ActionHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Action(value = "updateStatus", requiresLoggedIn = true)
public class UpdateStatusHandler extends ActionHandler {

    public UpdateStatusHandler(ChatService chatService) {
        super(chatService);
    }

    @Override
    public void handleImpl(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Person person = (Person) request.getSession().getAttribute("person");
        person.setStatus(request.getParameter("status"));

    }

}
