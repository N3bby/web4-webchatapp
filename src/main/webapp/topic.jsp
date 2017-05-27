<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Chat - ${topic.name}</title>
    <link rel="stylesheet" type="text/css" href="resources/css/base.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="resources/js/privateChat.js"></script>
    <script src="resources/js/leftPanel.js"></script>
    <script src="resources/js/chat.js"></script>
    <div id="topicIdentifier" style="display: none;">${topic.name}</div>
</head>
<body>

<jsp:include page="frag/leftPanel.jsp"/>

<main id="topicPanel">
    <a id="homeButton" href="Controller?action=index">&#8962;</a>
    <div class="messagesPanel">
        <c:forEach var="message" items="${messages}">
            <div class="message">
                <span class="message_sender">${message.from.username}</span> -
                <span class="message_content">${message.message}</span>
            </div>
        </c:forEach>
    </div>
    <div class="addMessagePanel">
        <form id="addMessageForm" action="Controller" method="post">
            <input id="addMessageForm_message" type="text" name="message" placeholder="Message" autocomplete="off">
            <input id="addMessageForm_send" type="submit" value="Send">
        </form>
    </div>
    <div id="pmWindowContainer"></div>
</main>

</body>
</html>
