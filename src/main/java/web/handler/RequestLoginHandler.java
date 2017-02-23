package web.handler;

import service.ChatService;
import web.Action;
import web.ActionHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Action(value = "requestLogin", requiresLoggedIn = false)
public class RequestLoginHandler extends ActionHandler {

    public RequestLoginHandler(ChatService chatService) {
        super(chatService);
    }

    @Override
    public void handleImpl(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        forward(request, response, "login.jsp");
    }

}
