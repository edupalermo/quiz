function connectRoom() {
    var socket = new SockJS('/websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        //setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/user/topic/room', function (message) {
            var message = JSON.parse(message.body);
            if (message.type == "UPDATE_USERS") {
                handleUpdateUsers(message);
            }
            else {
                handleUnknownMessage(message);
            }
        });
    });
}

function handleUpdateUsers(message) {
    console.log("Update users... ");
    console.log(message);
    $("#rank-panel").remove();
    $("#temporary-panel").remove();

    $("body").append(`
        <div id="rank-panel" class="rank">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <h3 class="panel-title">Rank</h3>
                </div>
                <div class="panel-body">
                    <table id="user-table" class="table table-striped">
                    </table>
                </div>
                <div class="panel-footer text-right">
                    Room ${message.roomIdentifier}
                </div>
            </div>
        </div>
    `);

    var i = 0;
    for (i = 0; i < message.users.length; i++) {
        $("#user-table").append(`
            <tr>
                <td>${message.users[i].nickname} <span class="badge" style="float: right">${message.users[i].points}</span></td>
            </tr>
        `);
    }
}

function handleUnknownMessage(message) {
    console.log("?? Unknown Message ??");
    console.log(message);
}
