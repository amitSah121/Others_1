import urllib.request
import urllib.parse
  
# trying to read the URL
try:
    x = urllib.request.urlopen('https://www.google.com/search?q=test')
    print(x.read())
  
# Catching the exception generated    
except Exception as e :
    print(str(e))