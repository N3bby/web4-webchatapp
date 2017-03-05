window.onload = function() {

    pollFriends();
    setInterval(pollFriends, 2000);

    document.getElementById("statusSelect").onchange = updateStatus;

};


var request = new XMLHttpRequest();

function pollFriends() {

    request.open("GET", "Controller?action=getFriends", true);
    request.onreadystatechange = getData;
    request.send();

}

function getData() {
    if(request.readyState == 4) {
        var friendsUl = document.getElementById("friends");
        friendsUl.innerHTML = "";
        var friends = JSON.parse(request.responseText);
        console.log(friends);
        for(var i = 0; i < friends.length; i++) {
            var friendLi = document.createElement("li");
            friendLi.innerHTML = friends[i].username + " - " + friends[i].status;
            friendsUl.appendChild(friendLi);
        }
    }
}

function updateStatus() {

    var statusList = document.getElementById("statusSelect");
    var status = statusList[statusList.selectedIndex].value;

    var request = new XMLHttpRequest();
    request.open("POST", "Controller?action=updateStatus", true);
    request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    request.send("status=" + status);

}