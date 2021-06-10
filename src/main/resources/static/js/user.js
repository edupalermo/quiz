function connectUser() {
    var socket = new SockJS('/websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        //setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/user', function (message) {
            handleIncomingMessage(message);
        });
    });
}

function handleIncomingMessage(message) {
    console.log(JSON.parse(message.body));
}
