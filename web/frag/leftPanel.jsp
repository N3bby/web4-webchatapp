<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="leftPanel">
    <div id="statusPanel">
        <p>${from.username}</p>
        <select id="statusSelect">
            <%--This code sucks--%>
            <c:if test="${from.status ne 'Online' && from.status ne 'Busy' && from.status ne 'Offline'}">
                <option value="temp" disabled selected>${from.status}</option>
            </c:if>
            <option value="Online" <c:if test="${from.status eq 'Online'}">selected</c:if>>Online</option>
            <option value="Busy" <c:if test="${from.status eq 'Busy'}">selected</c:if>>Busy</option>
            <option value="Offline" <c:if test="${from.status eq 'Offline'}">selected</c:if>>Offline</option>
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
</div>