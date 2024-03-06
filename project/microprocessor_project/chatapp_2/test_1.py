import socket
import threading

# Server setup
server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
server.bind(('0.0.0.0', 12345))  # Listen on all available network interfaces
server.listen(5)


hostname = socket.gethostname()    
IPAddr = socket.gethostbyname(hostname) 
print(IPAddr)

# Store connected clients
clients = []

def handle_client(client_socket):
    # Handle messages from a client
    while True:
        try:
            data = client_socket.recv(1024)
            if not data:
                break
            # Broadcast the message to all clients
            for client in clients:
                client.send(data)
        except Exception as e:
            print("Error:", e)
            break

def accept_clients():
    # Accept incoming connections and create threads for each client
    while True:
        client_socket, client_address = server.accept()
        print("Accepted connection from:", client_address)
        clients.append(client_socket)
        client_handler = threading.Thread(target=handle_client, args=(client_socket,))
        client_handler.start()

# Start accepting clients
accept_clients()
