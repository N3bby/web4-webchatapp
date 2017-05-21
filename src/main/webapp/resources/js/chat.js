$(document).ready( function () {

    console.log("chat.js");

    openSocket();
    document.getElementById("addMessageForm").onsubmit = sendMessage;

});

var chatSocket;

function openSocket() {

    var topic = document.getElementById("topicIdentifier").innerText;
    chatSocket = new WebSocket("ws://" + location.host + "/topicchat/" + topic);

    //Register event handlers
    chatSocket.onmessage = onMessage;

}

function onMessage(event) {

    var message = JSON.parse(event.data);
    
    var messageDiv =
        "<div class='message'>" +
            "<span class='message_sender'>" + message.from.username + "</span> - " +
            "<span class='message_content'>" + message.message + "</span>" +
        "</div>";

    //Add messageDiv to messagesPanel and scroll to top of panel
    var messagesPanel = document.getElementsByClassName("messagesPanel")[0];
    messagesPanel.innerHTML += messageDiv;
    messagesPanel.scrollTop = messagesPanel.scrollHeight;

}

function sendMessage(event) {

    event.preventDefault();

    //Get message and clear input field
    var messageInputField = document.getElementById("addMessageForm_message");
    var messageText = messageInputField.value;
    messageInputField.value = "";

    //Don't send if message is empty
    if(messageText === "") return;

    //Send message
    chatSocket.send(messageText);

}