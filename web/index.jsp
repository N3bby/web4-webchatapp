<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Chat</title>
    <link rel="stylesheet" type="text/css" href="resources/css/base.css">
    <script src="resources/js/index.js"></script>
</head>
<body>

<main>
    <div id="leftPanel">
        <div id="statusPanel">
            <p>${person.username}</p>
            <select id="statusSelect">
                <option value="Online" <c:if test="${person.status eq 'Online'}">selected</c:if>>Online</option>
                <option value="Busy" <c:if test="${person.status eq 'Busy'}">selected</c:if>>Busy</option>
                <option value="Offline" <c:if test="${person.status eq 'Offline'}">selected</c:if>>Offline</option>
            </select>
        </div>
        <div id="friendPanel">
            <p>Friends</p>
            <ul id="friends">
            </ul>
        </div>
        <div id="addFriendPanel">
            <form action="Controller" method="post">
                <input type="hidden" name="action" value="addFriend">
                <input type="text" name="username" placeholder="Username">
                <input type="submit" value="Add Friend">
            </form>
        </div>
    </div>
    <div id="mainPanel">
    </div>
</main>

</body>
</html>
