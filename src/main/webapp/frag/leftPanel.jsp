<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="leftPanel">
    <div id="statusPanel">
        <p>${person.username}</p>
        <select id="statusSelect">
            <%--This code sucks--%>
            <c:if test="${person.status ne 'Online' && person.status ne 'Busy' && person.status ne 'Offline'}">
                <option value="temp" disabled selected>${person.status}</option>
            </c:if>
            <option value="Online" <c:if test="${person.status eq 'Online'}">selected</c:if>>Online</option>
            <option value="Busy" <c:if test="${person.status eq 'Busy'}">selected</c:if>>Busy</option>
            <option value="Offline" <c:if test="${person.status eq 'Offline'}">selected</c:if>>Offline</option>
            <option value="Custom...">Custom...</option>
        </select>
    </div>
    <div id="friendPanel">
        <p>Friends</p>
        <ul id="friends">
        </ul>
    </div>
    <div id="addFriendPanel">
        <form id="addFriendForm" action="Controller" method="post">
            <input id="addFriendForm_user" type="text" name="username" placeholder="Username">
            <input type="submit" value="Add Friend">
        </form>
    </div>
    <a href="Controller?action=logout" id="logoutButton">Log Out</a>
</div>