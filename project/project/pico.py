import socket
import network
from machine import Pin
from time import sleep

led1 = Pin(0,Pin.OUT)
led2 = Pin(1,Pin.OUT) 
led3 = Pin(2,Pin.OUT)
led4 = Pin(5,Pin.OUT)
led5 = Pin(4,Pin.OUT)

ssid = "KANHA"
password = "Huihui147224"

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
    
patterns = []
pat_bool = [0,0,0,0]
b1 = False

def run_pattern():
    global b1
    global patterns
    while len(patterns) != 0:
        p1 = patterns.pop(0)
        if(not b1 and p1 == "Init"):
            b1 = True
            print("Stop Others")
            pat_bool[0] = 1
            pat_bool[1] = 0
            pat_bool[2] = 0
            pat_bool[3] = 0
            
            
        if(b1):
            if(p1 == "V"):
                print("V")
                pat_bool[1] = 1
                pat_bool[0] = 0
                b1 = False
            elif(p1 == "SpiderMan"):
                print("SpiderMan")
                pat_bool[2] = 1
                pat_bool[0] = 0
                b1 = False
            elif(p1 == "Perp"):
                print("Perp")
                pat_bool[3] = 1
                pat_bool[0] = 0
                b1 = False
        

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
        #print("Test 1:", data)
        patterns.append(data)
        run_pattern()
        
        if(pat_bool[0] == 1):
            led2.on()
            led3.on()
            led5.on()
            led4.off()
            led1.off()
        elif(pat_bool[1] == 1):
            led2.on()
            led3.on()
            led5.on()
            led4.on()
            led1.on()
        elif(pat_bool[2] == 1):
            led2.on()
            led3.off()
            led5.on()
            led4.on()
            led1.on()
        elif(pat_bool[3] == 1):
            led2.off()
            led3.on()
            led5.on()
            led4.on()
            led1.on()

        # Process the received data (e.g., send a response)
        #response = input("Test 2: ")#"Received: " + data
        response = "Data Received"
        client_socket.send(response.encode("utf-8"))
        #if data.lower() == "close" or response.lower() == "exit":
            # Close the client socket
        #    client_socket.close()
        #    print("Connection closed")
        #    break

    except KeyboardInterrupt:
        break

# Clean up
server_socket.close()


