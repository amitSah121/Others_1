# Python 3
import http.server
import socketserver
import ssl

# Define the SSL certificate and key file
certfile = 'server.pem'

# Create an HTTP server with HTTPS support
httpd = socketserver.TCPServer(('0.0.0.0', 443), http.server.SimpleHTTPRequestHandler)

# Wrap the server with SSL/TLS using the certificate and key
httpd.socket = ssl.wrap_socket(httpd.socket, keyfile=certfile, certfile=certfile, server_side=True)

# Start the server
print("Serving at https://0.0.0.0:443")
httpd.serve_forever()
