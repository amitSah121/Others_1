from machine import Pin
from time import sleep

led1 = Pin(0,Pin.OUT)
led2 = Pin(1,Pin.OUT)
led3 = Pin(2,Pin.OUT)
led4 = Pin(5,Pin.OUT)
led5 = Pin(4,Pin.OUT)


while True:
    led1.on()
    led2.on()
    led3.on()
    led4.on()
    led5.on()
    sleep(0.5)
    led1.off()
    led2.off()
    led3.off()
    led4.off()
    led5.off()
    sleep(0.5)
    print("Helli")
