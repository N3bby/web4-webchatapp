package web.handler;

import web.Action;
import web.ActionHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Action("index")
public class IndexHandler extends ActionHandler {

    public void handle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        forward(request, response, "index.jsp");
    }

}
