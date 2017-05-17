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
    <div id="loginPanel">
        <form action="Controller" method="post">
            <c:if test="${not empty errors}">
                <ul class="errors">
                    <c:forEach var="error" items="${errors}">
                        <li>${error}</li>
                    </c:forEach>
                </ul>
            </c:if>
            <input type="hidden" name="action" value="login">
            <input id="loginUsername" name="username" type="text" placeholder="Username">
            <input id="loginPassword" name="password" type="password" placeholder="Password">
            <input id="loginSubmit" type="submit" value="Login">
        </form>
    </div>
</main>
</body>
</html>
