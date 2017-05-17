package com.razacx.web;

import com.razacx.domain.service.concrete.DomainServiceHolder;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Controller extends HttpServlet {

    private DomainServiceHolder serviceHolder;
    private HandlerFactory handlerFactory;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        this.serviceHolder = DomainServiceHolder.getInstance();
        this.handlerFactory = new HandlerFactory(serviceHolder);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String action = request.getParameter("action");
        if (action == null || action.trim().isEmpty()) action = "index";

        handlerFactory.getHandler(action).handle(request, response);

    }

    @Override
    public void destroy() {
        serviceHolder.destroy();
    }
    
}
