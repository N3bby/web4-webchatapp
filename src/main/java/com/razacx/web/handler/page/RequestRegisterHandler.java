package com.razacx.web.handler.page;

import com.razacx.domain.service.concrete.DomainServiceHolder;
import com.razacx.web.Action;
import com.razacx.web.ActionHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Action(value = "requestRegister", requiresLoggedIn = false)
public class RequestRegisterHandler extends ActionHandler {

    public RequestRegisterHandler(DomainServiceHolder serviceHolder) {
        super(serviceHolder);
    }

    @Override
    public void handleImpl(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    
        forward(request, response, "register.jsp");
        
    }
    
}
