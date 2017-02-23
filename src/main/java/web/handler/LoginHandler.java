package web.handler;

import domain.model.Person;
import service.ChatService;
import web.Action;
import web.ActionHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Action(value = "login", requiresLoggedIn = false)
public class LoginHandler extends ActionHandler {

    public LoginHandler(ChatService chatService) {
        super(chatService);
    }

    @Override
    public void handleImpl(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String name = request.getParameter("username");
        String password = request.getParameter("password");

        List<String> errors = new ArrayList<>();

        Person person = getChatService().getPerson(name);
        if (person == null) {
            errors.add("Invalid username or password");
        } else {
            if (person.passwordMatches(password)) {
                request.getSession().setAttribute("person", person);
                person.setStatus("Online");
                redirect(response, "Controller?action=index");
                return;
            } else {
                errors.add("Invalid username or password");
            }
        }

        request.setAttribute("errors", errors);
        forward(request, response, "login.jsp");

    }

}
