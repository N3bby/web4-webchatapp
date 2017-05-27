//On page load
$(function () {
   
    //Get new messages
    setInterval(function () {
        
        getNewMessages(function (pms) {

            $.each(pms, function (i, obj) {
                var window = getChatWindowFor(obj.from.username);
                if(window === undefined) {
                    new ChatWindow(obj.from.username);
                } else {
                    window.addMessage(obj);
                }
            });
            
        });
        
    }, 1000);
    
    myUsername = $("#statusPanel>p").text(); //Bit cheaty, but works :p
    
});

//Fetching and sending data

function sendPrivateMessage(to, message) {

    var pm = {
        "to": to,
        "message": message
    };

    $.post("Controller?action=pmSend", pm, function (data) {
        console.log("Send message to " + to + ": " + message);
    });

}

function getNewMessages(callback) {

    $.get("Controller?action=pmGetNew", function (data) {

        var pms = JSON.parse(data);

        //All new pms get marked as 'seen'
        $.each(pms, function (i, obj) {
            var params = {"id": obj.id};
            $.post("Controller?action=pmSeen", params);
        });
        
        callback(pms);

    })

}

function getAllMessages(username, callback) {

    $.get("Controller?action=pmGetAll&user=" + username, function (data) {
        
        var pms = JSON.parse(data);        
        callback(pms);

    })

}

//UI Stuff

var chatWindowContainerId = "pmWindowContainer";
var myUsername = "Me";

var chatWindows = [];

function getChatWindowFor(user) {
    
    for(var i = 0; i < chatWindows.length; i++) {
        if(chatWindows[i].user === user) return chatWindows[i];
    }
    
}

function ChatWindow(user) {

    var _this = this;
    
    this.user = user;
    
    this.window = undefined;
    this.closeButton = undefined;
    this.pmMessages = undefined;
    this.form = undefined;    
    
    generateChatWindow(this);
    
    //Event handlers
    this.form.submit(function () {
        var msg = _this.form.find(".pmMessage").val();
        if(msg === "") return false;
        _this.form.find(".pmMessage").val("");
        sendPrivateMessage(_this.user, msg);
        _this.addMessage({"from" : {"username" : myUsername}, "message" : msg});
        return false; // == preventDefault
    });
    
    this.closeButton.click(function () {
        _this.window.slideToggle(250);
        setTimeout(function () {
            _this.window.remove();
            chatWindows.splice(chatWindows.indexOf(_this), 1);
        }, 500)
    });
    
    //Load all messages
    getAllMessages(this.user, function (pms) {
        $.each(pms, function (i, obj) {
            _this.addMessage(obj);
        });
    });
    
    //Add window to chatWindows and open the panel
    chatWindows.push(this);
    this.window.hide();
    this.window.slideToggle(250);
    
    //Methods
    this.addMessage = function (message) {
        var message = $("<div class='pm'><span class='pmUser'>" + message.from.username + "</span> " + message.message + "</div>");
        this.pmMessages.append(message);
        this.pmMessages.scrollTop(this.pmMessages[0].scrollHeight);        
    }
    
}

function generateChatWindow(chatWindow) {

    //Create html
    var panel = $("<div class='pmPanel'></div>");
    var pmHeader = $("<div class='pmHeader'>" + chatWindow.user + "</div>");
    var closeButton = $("<div class='pmCloseButton'>&#10006;</div>");    
    var pmMessages =  $("<div class='pmMessages'></div>");    
    var form = $("<form class='pmForm'>" +
        "<input type='text' class='pmMessage'> <input type='submit' value='Send'>" +
        "</form>");
    
    //Appends
    pmHeader.append(closeButton);
    panel.append(pmHeader);
    panel.append(pmMessages);
    panel.append(form);
    
    //Append panel to container
    var id = "#" + chatWindowContainerId;
    $(id).append(panel);
    
    //Set ChatWindow attributes
    chatWindow.window = panel;
    chatWindow.closeButton = closeButton;
    chatWindow.pmMessages = pmMessages;
    chatWindow.form = form;
    
}

function createPrivateChat(username) {

    var window = getChatWindowFor(username);
    if(window === undefined) {
        new ChatWindow(username);
    }
    
}