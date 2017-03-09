window.onload = function () {

    pollFriends();
    setInterval(pollFriends, 2000);

    document.getElementById("statusSelect").onchange = updateStatus;

};

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
    request.open("POST", "Controller?action=updateStatus", true);
    request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    request.send("status=" + status);

}