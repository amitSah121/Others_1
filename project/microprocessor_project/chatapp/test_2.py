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
        if data and data != "__n__": 
            print("Test 1:", data)

        # Process the received data (e.g., send a response)
        try:
            with open('file_2.txt', 'r') as file:
                # Read the entire file as a string
                message = file.read()
            with open('file_2.txt', 'w') as file:
                pass 

                # Do something with the file contents
            if message:
                print("Test 2: ",message)
        except FileNotFoundError:
            print("File not found.")
        except Exception as e:
            print("An error occurred:", str(e))

        if message:
            client_socket.send(message.encode("utf-8"))
        else:
            client_socket.send("__n__".encode("utf-8"))

        if data.lower() == "close" or message.lower() == "exit":
            # Close the client socket
            client_socket.close()
            print("Connection closed")
            break

    except KeyboardInterrupt:
        break

# Clean up
server_socket.close()
