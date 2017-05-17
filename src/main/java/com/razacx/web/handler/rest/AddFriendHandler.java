package com.razacx.web.handler.rest;

import com.razacx.domain.model.Person;
import com.razacx.domain.service.concrete.DomainServiceHolder;
import com.razacx.web.Action;
import com.razacx.web.ActionHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Action(value = "addFriend", requiresLoggedIn = true)
public class AddFriendHandler extends ActionHandler {

    public AddFriendHandler(DomainServiceHolder serviceHolder) {
        super(serviceHolder);
    }

    @Override
    public void handleImpl(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String username = (String) request.getSession().getAttribute("person");
        Person person = getServiceHolder().getPersonService().getPerson(username);

        String friendUsername = request.getParameter("username");
        Person friend = getServiceHolder().getPersonService().getPerson(friendUsername);

        try {
            person.addFriend(friend);
            getServiceHolder().getPersonService().updatePerson(person);
        } catch (NullPointerException e) {
            response.getWriter().write("User does not exist");
        } catch (IllegalArgumentException e) {
            response.getWriter().write("User is already a friend");
        }
        response.getWriter().flush();

    }

}
