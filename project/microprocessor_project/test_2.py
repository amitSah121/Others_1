import socket


# Create a socket to listen for incoming connections
server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
server_socket.bind(("", 8080))  # Bind to port 8080
server_socket.listen(5)  # Allow up to 5 client connections


hostname = socket.gethostname()    
IPAddr = socket.gethostbyname(hostname) 
print(IPAddr)

while True:
    try:
        client_socket, client_address = server_socket.accept()
        print("Accepted connection from:", client_address)
        break
    except:
        break

while True:
    try:
        # Receive data from the client
        data = client_socket.recv(1024).decode("utf-8")
        print("Test 1:", data)

        # Process the received data (e.g., send a response)
        response = input("Test 2: ")#"Received: " + data
        client_socket.send(response.encode("utf-8"))
        if data.lower() == "close" or response.lower() == "exit":
            # Close the client socket
            client_socket.close()
            print("Connection closed")
            break

    except KeyboardInterrupt:
        break

# Clean up
server_socket.close()
