$(document).ready(function () {

    console.log("leftPanel.js");

    pollFriends();
    setInterval(pollFriends, 2000);

    document.getElementById("statusSelect").onchange = updateStatus;
    document.getElementById("addFriendForm").onsubmit = addFriend;

});

function pollFriends() {

    var request = new XMLHttpRequest();

    request.open("GET", "Controller?action=getFriends", true);
    request.overrideMimeType("application/json"); //Needed for firefox (otherwise syntax error)

    request.onreadystatechange = function () {
        if (request.readyState == 4) {
            var friendsUl = document.getElementById("friends");
            friendsUl.innerHTML = "";
            var friends = JSON.parse(request.responseText);
            for (var i = 0; i < friends.length; i++) {
                var friendLi = document.createElement("li");
                friendLi.innerHTML = friends[i].username + " - " + friends[i].status;
                var username = friends[i].username;
                friendLi.onclick = (function (username) { //What the actual ...
                    return function() {  
                        createPrivateChat(username);
                    }
                })(username);
                friendsUl.appendChild(friendLi);
            }
        }
    };

    request.send();

}

function updateStatus() {

    //Get status list and the newly selected status
    var statusList = document.getElementById("statusSelect");
    var status = statusList[statusList.selectedIndex].value;

    //Remove temporary option if it was not selected
    if (status.value !== "temp") {
        for (var i = 0; i < statusList.childElementCount; i++) {
            var option = statusList.children[i];
            if (option.value === "temp") statusList.removeChild(option);
        }
    }

    //If custom status selected
    if (status === "Custom...") {

        //Get user input
        status = window.prompt("Enter your status.");

        //Create temporary option and add it
        var tempOption = document.createElement("option");
        tempOption.value = "temp";
        tempOption.innerText = status;
        tempOption.setAttribute("disabled", "true");

        statusList.insertBefore(tempOption, statusList.firstChild);

        //Select new option
        statusList.value = "temp";

    }

    //Commit new status
    var request = new XMLHttpRequest();
    request.open("POST", "Controller?action=changeStatus", true);
    request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    request.overrideMimeType("text/plain");
    request.send("status=" + status);

}

function addFriend(evt) {

    evt.preventDefault();

    var textField = document.getElementById("addFriendForm_user");
    var friendUsername = textField.value;
    textField.value = ""; //Reset text field

    var request = new XMLHttpRequest();
    request.open("POST", "Controller?action=addFriend", true);
    request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    request.overrideMimeType("text/plain");
    request.onreadystatechange = function () { //Show errors in alert window
        if (request.readyState === 4) {
            if (request.responseText !== "") {
                window.alert(request.responseText);
            }
        }
    };
    request.send("username=" + friendUsername);

}
