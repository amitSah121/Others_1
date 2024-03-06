import socket
import threading

name = input("Enter your name: ")

def receive_messages(client_socket):
    while True:
        data = (client_socket.recv(1024)).decode('utf-8')
        if not (name in data):
            print(data)

# Client setup
client = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
#client.connect(('127.0.1.1', 12345))  # Replace CHAT_SERVER_IP with the server's IP address
client.connect(('192.168.0.192',8080))

# Start a thread to receive messages
message_receiver = threading.Thread(target=receive_messages, args=(client,))
message_receiver.start()

# Send messages
while True:
    message = input(name + " : ")
    message = name + " : " + message
    client.send(message.encode('utf-8'))
