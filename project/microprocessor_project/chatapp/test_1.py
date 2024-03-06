import socket

server_ip = "127.0.1.1"
server_port = 8080

# Create a socket to connect to the server
client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

try:
    # Connect to the server
    client_socket.connect((server_ip, server_port))
    print("Connected to Chat Server")

    while True:
        # message = input("Test 1: ")
        try:
            with open('file_1.txt', 'r') as file:
                # Read the entire file as a string
                message = file.read()
            with open('file_1.txt', 'w') as file:
                pass 

                # Do something with the file contents
            if message:
                print("Test 1: ",message)
        except FileNotFoundError:
            print("File not found.")
        except Exception as e:
            print("An error occurred:", str(e))

        if message.lower() == "exit":
            break

        # Send the message to the serverif message:
        if message:
            client_socket.send(message.encode("utf-8"))
        else:
            client_socket.send("__n__".encode("utf-8"))
        # print("test_1 received")
        # Receive and print the server's response
        response = client_socket.recv(1024).decode("utf-8")
        # print("test_1 responsed")
        if response and response != "__n__" : 
            print("Test 2:", response)
        if response.lower() == "close":
            break

except Exception as e:
    print("Error:", str(e))

finally:
    # Close the client socket
    client_socket.close()
