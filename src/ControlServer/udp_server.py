
import socket
from control_servo import ServoControl
import sys

# A UDP server

UDPSock = socket.socket(socket.AF_INET,socket.SOCK_DGRAM)
listen_addr = (sys.argv[1], int(sys.argv[2]))
UDPSock.bind(listen_addr)

try:
	sc = ServoControl()
except Exception, e:
	print "Dispositivo no conectado"
	raise

try:
	while True:
		data,addr = UDPSock.recvfrom(1024)
		print data
		#print data.strip(),addr
		parsedAngle = int(float(data.replace(',', '.')))
		sc.moveAngle(parsedAngle)
		#sc.showDebug()
except KeyboardInterrupt:
	sc.write(str(90) + "\n")
	sys.exit()