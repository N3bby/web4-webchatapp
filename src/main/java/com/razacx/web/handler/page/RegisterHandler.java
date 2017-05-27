package com.razacx.web.handler.page;

import com.razacx.domain.model.Person;
import com.razacx.domain.service.concrete.DomainServiceHolder;
import com.razacx.web.Action;
import com.razacx.web.ActionHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Action(value = "register", requiresLoggedIn = false)
public class RegisterHandler extends ActionHandler {

    public final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public RegisterHandler(DomainServiceHolder serviceHolder) {
        super(serviceHolder);
    }

    @Override
    public void handleImpl(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String password2 = request.getParameter("re-type_password");
        String email = request.getParameter("email");
        String birthDate = request.getParameter("birthdate");
        String gender = request.getParameter("gender");

        request.setAttribute("err_username", username);
        request.setAttribute("err_email", email);
        request.setAttribute("err_birthdate", birthDate);

        List<String> errors = new ArrayList<>();

        if (!password.equals(password2)) {
            errors.add("Passwords don't match");
        }
        if (!validateEmail(email)) {
            errors.add("Invalid email address");
        }
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(birthDate);
        } catch (ParseException e) {
            errors.add("Invalid birthdate");
        }
        Person.Gender genderVal = Person.Gender.valueOf(gender.toUpperCase());

        if (!errors.isEmpty()) {

            request.setAttribute("errors", errors);
            forward(request, response, "register.jsp");

        } else {

            try {
                Person person = new Person();
                person.setUsername(username);
                person.setPassword(password);
                person.setEmail(email);
                person.setBirthDate(date);
                person.setGender(genderVal);
                getServiceHolder().getPersonService().addPerson(person);
                response.sendRedirect("Controller?action=requestLogin");
            } catch (Exception e) {
                errors.add(e.getMessage());
                request.setAttribute("errors", errors);
                forward(request, response, "register.jsp");
            }

        }

    }

    private boolean validateEmail(String email) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.find();
    }

}
