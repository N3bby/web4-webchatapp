<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" type="text/css" href="resources/css/base.css"/>
</head>
<body>
<main>
    <div id="loginBackground"></div>
    <div id="loginRegisterNav">
        <a href="Controller?action=requestLogin">Login</a>
        <a href="Controller?action=requestRegister" class="navActive">Register</a>        
    </div>
    <div id="loginPanel">
        <form action="Controller" method="post">
            <c:if test="${not empty errors}">
                <ul class="errors">
                    <c:forEach var="error" items="${errors}">
                        <li>${error}</li>
                    </c:forEach>
                </ul>
            </c:if>
            <input type="hidden" name="action" value="register">
            <input id="username" name="username" type="text" placeholder="Username" value="${err_username}">
            <input id="password" name="password" type="password" placeholder="Password">
            <input id="re-type_password" name="re-type_password" type="password" placeholder="Re-type password">
            <input id="username" name="email" type="text" placeholder="Email" value="${err_email}">
            <h3>Gender</h3>
            <input type="radio" id="radio_gender_male" name="gender" value="male" checked> <label for="radio_gender_male">Male</label><br>
            <input type="radio" id="radio_gender_female" name="gender" value="female"> <label for="radio_gender_female">Female</label>
            <input id="submit" type="submit" value="Register">
        </form>
    </div>
</main>
</body>
</html>
