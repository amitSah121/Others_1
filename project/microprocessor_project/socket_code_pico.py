import socket
import network
import machine

ssid = "The Night Action"
password = "EX-NIGHT AGENT"

# Create a socket to listen for incoming connections
server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
server_socket.bind(("", 8080))  # Bind to port 8080
server_socket.listen(5)  # Allow up to 5 client connections


wlan = network.WLAN(network.STA_IF)
wlan.active(True)
wlan.connect(ssid, password)

while not wlan.isconnected():
    pass

print("Connected to Wi-Fi")
if wlan.active():
    # Get the IP address assigned to the Pico
    ip_address = wlan.ifconfig()[0]
    print("Pico IP Address:", ip_address)
else:
    print("Pico is not connected to Wi-Fi")
    

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
        print("Received message:", data)

        # Process the received data (e.g., send a response)
        response = "Received: " + data
        client_socket.send(response.encode("utf-8"))
        if data.lower() == "close":
            # Close the client socket
            client_socket.close()
            print("Connection closed")
            break

    except KeyboardInterrupt:
        break

# Clean up
server_socket.close()
wlan.disconnect()
wlan.active(False)
