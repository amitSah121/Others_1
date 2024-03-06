import socket

# Raspberry Pi Pico's IP address and port
server_ip = "192.168.0.192"
server_port = 8080

# Create a socket to connect to the server
client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

try:
    # Connect to the server
    client_socket.connect((server_ip, server_port))
    print("Connected to Chat Server")

    while True:
        message = input("Test 1: ")
        if message.lower() == "exit":
            break

        # Send the message to the server
        client_socket.send(message.encode("utf-8"))

        # Receive and print the server's response
        response = client_socket.recv(1024).decode("utf-8")
        print("Test 2:", response)
        if response.lower() == "close":
            break

except Exception as e:
    print("Error:", str(e))

finally:
    # Close the client socket
    client_socket.close()
