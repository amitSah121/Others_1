import socket

# Raspberry Pi Pico's IP address and port
server_ip = "192.168.0.105"
server_port = 8080

# Create a socket to connect to the server
client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
# client_socket.settimeout(10)

try:
    # Connect to the server
    client_socket.connect((server_ip, server_port))
    print("Connected to Chat Server")

    while True:
        # message = input("Test 1: ")
        file_path = "sample.txt"  # Replace with the path to your text file

        while True:
            try:
                with open(file_path, 'r') as file:
                    # Read the entire content of the file
                    file_content = file.read()
                    # Print the content to the console
                    if(len(file_content) == 0): continue
                    # print(file_content)
                    message = file_content
                    with open(file_path, 'w') as file:
                        pass 
                    break
            except FileNotFoundError:
                print(f"File '{file_path}' not found.")
            except Exception as e:
                print(f"An error occurred: {str(e)}")


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
