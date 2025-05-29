document.addEventListener("DOMContentLoaded", function () {
    const stompClient = new StompJs.Client({
        brokerURL: 'ws://localhost:8080/gs-guide-websocket'
    });

    stompClient.onConnect = (frame) => {
        console.log('Connected: ' + frame);
        setConnected(true);

        // Subscribe to user-specific queue dynamically
        stompClient.subscribe('/user/queue/specific-user', (greeting) => {
            showGreeting(JSON.parse(greeting.body).content);
            document.getElementById("send").disabled = false;
        });
    };

    stompClient.onWebSocketError = (error) => {
        console.error('Error with websocket', error);
    };

    stompClient.onStompError = (frame) => {
        console.error('Broker reported error: ' + frame.headers['message']);
        console.error('Additional details: ' + frame.body);
    };

    function setConnected(connected) {
        document.getElementById("connect").disabled = connected;
        document.getElementById("disconnect").disabled = !connected;
        document.getElementById("send").disabled = !connected;
    }

    function connect() {
        stompClient.activate();
    }

    function disconnect() {
        stompClient.deactivate();
        setConnected(false);
        console.log("Disconnected");
    }

    function sendName() {
        const nameInput = document.getElementById("name").value;
        document.getElementById("name").value = "";
        document.getElementById("send").disabled = true;
        showGreeting(nameInput);

        const messagePayload = {
            role: "client",
            timestamp: new Date().toISOString(),
            content: nameInput
        };

        stompClient.publish({
            destination: "/app/hello-user",
            body: JSON.stringify(messagePayload)
        });
    }

    function showGreeting(message) {
        const greetingsTable = document.getElementById("chatTable");
        const newRow = document.createElement("tr");
        const newCell = document.createElement("td");

        newCell.textContent = message;
        newRow.appendChild(newCell);
        greetingsTable.appendChild(newRow);
    }

    // Ensure buttons work properly with event listeners
    document.getElementById("connect").addEventListener("click", connect);
    document.getElementById("disconnect").addEventListener("click", disconnect);

    document.getElementById("send").addEventListener("click", sendName);
    document.getElementById("send").disabled = true;

    document.getElementById("logout-button").addEventListener("click", function () {
        if (stompClient) {
            stompClient.deactivate(); // Close WebSocket connection before logout
        }
        document.getElementById("logoutForm").submit(); // Submit logout form
    });

    // Prevent form submission from reloading page
    document.querySelectorAll("form").forEach(form => {
        form.addEventListener('submit', (event) => event.preventDefault());
    });
});