    function setDeleteId(value) {
        console.log("Value : "+value);
        document.getElementById("deleteChatId").value = value;
    }

document.addEventListener("DOMContentLoaded", function () {
    const stompClient = new StompJs.Client({
        brokerURL: 'ws://localhost:8080/gs-guide-websocket'
    });

    stompClient.onConnect = (frame) => {
        console.log('Connected: ' + frame);
        setConnected(true);

        // Subscribe to user-specific queue dynamically
        stompClient.subscribe('/user/queue/specific-user', (messagePayload) => {
            showMessage(JSON.parse(messagePayload.body).content, "assistant");
            document.getElementById("send").disabled = false;
        });
    };

    stompClient.onWebSocketError = (error) => {
        console.error('Error with websocket', error);
        disconnect();
    };

    stompClient.onStompError = (frame) => {
        console.error('Broker reported error: ' + frame.headers['message']);
        console.error('Additional details: ' + frame.body);
    };

    function setConnected(connected) {
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

    function sendMessage() {
        const chatIdInput = document.getElementById("chatId").value;
        const messageInput = document.getElementById("message").value;
        document.getElementById("message").value = "";
        document.getElementById("send").disabled = true;
        showMessage(messageInput, "user");

        const messagePayload = {
            chatId: chatIdInput,
            role: "client",
            timestamp: new Date().toISOString(),
            content: messageInput
        };

        stompClient.publish({
            destination: "/app/hello-user",
            body: JSON.stringify(messagePayload)
        });
    }

    function showMessage(message, messageType) {
        // Select the chat list
        const chatList = document.getElementById("chatTable");

        // Create a new list item
        const listItem = document.createElement("li");
        listItem.className = "d-flex justify-content-between mb-4";

        // Create the card container
        const cardDiv = document.createElement("div");
        cardDiv.className = "card bg-dark border-secondary";

        // Create the card header
        const cardHeader = document.createElement("div");
        cardHeader.className = "card-header d-flex justify-content-between p-3 bg-secondary";

        const typeP = document.createElement("p");
        typeP.className = "fw-bold mb-0";
        typeP.textContent = messageType;

        const timeP = document.createElement("p");
        timeP.className = "small mb-0";
        timeP.innerHTML = `<i class="far fa-clock">Just now</i>`;

        cardHeader.appendChild(typeP);
        cardHeader.appendChild(timeP);

        // Create the card body
        const cardBody = document.createElement("div");
        cardBody.className = "card-body";

        const contentP = document.createElement("p");
        contentP.className = "mb-0";
        contentP.textContent = message;

        cardBody.appendChild(contentP);

        // Assemble the card
        cardDiv.appendChild(cardHeader);
        cardDiv.appendChild(cardBody);

        // Append the card to the list item
        listItem.appendChild(cardDiv);

        // Append the new list item to the chat list
        chatList.appendChild(listItem);
    }

    document.getElementById("send").addEventListener("click", sendMessage);
    document.getElementById("send").disabled = true;

    document.getElementById("logout-button").addEventListener("click", function () {
        if (stompClient) {
            stompClient.deactivate(); // Close WebSocket connection before logout
        }
        document.getElementById("logoutForm").submit(); // Submit logout form
    });

    stompClient.activate();
});