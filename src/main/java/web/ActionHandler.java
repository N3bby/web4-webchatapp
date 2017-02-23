package web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class ActionHandler {

    public abstract void handle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

    public void redirect(HttpServletResponse response, String url) throws ServletException, IOException {
        response.sendRedirect(url);
    }

    public void forward(HttpServletRequest request, HttpServletResponse response, String destination) throws ServletException, IOException {
        request.getRequestDispatcher(destination).forward(request, response);
    }

}
