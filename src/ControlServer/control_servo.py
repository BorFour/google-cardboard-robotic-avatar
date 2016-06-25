
import serial
import pygame 
import sys
from pygame.locals import *
from time import sleep

#device = "/dev/ttyACM0"
device = "/dev/ttyACM1"
RIGHT_KEYCODE = (102 or 85)
LEFT_KEYCODE = (100 or 83)

grid = 45
moveValues = {
	0: [90],
	-45: [79], 
	45: [99],
	-90: [73],
	90: [104],
	-135: [73, 79],
	135: [104, 99],
	-180: [60],
	180:  [117]
}

def normalize_value(value):
	return ((value + 180) % 360) - 180

device = "/dev/ttyACM0"
class ServoControl(object):
	"""docstring for ServoControl"""
	currAngle = 0.0
	def __init__(self):
		super(ServoControl, self).__init__()
		self.ser = serial.Serial(device, 9600, timeout=None)# open first serial port
	def write(self, value):
		self.ser.write(str(value).encode())
	def writeNum(self, value):
		self.write(str(value) + "\n")
	def moveLeft(self, value):
		for x in xrange(1, value+1):
			self.writeNum(180)
	def moveRight(self, value):
		for x in xrange(1, value+1):
			self.writeNum(0)

	def getNearestKey(self, value):
		minDiff = 360
		minKey = 0
		for key in moveValues:
			diff = abs(key - value)
			if(diff < minDiff):
				minDiff = diff
				minKey = key
		#if key == self.currAngle:
		#	return None
		return minKey

	# angle -> [-180, 180]
	def moveAngle(self, angle):
		self.moveTo(angle)

	def moveTo(self, angle):
		diffAngle = (angle - self.currAngle)
		if diffAngle < -180:
			diffAngle += 360
		elif diffAngle > 180:
			diffAngle -= 360
		nearestKey = self.getNearestKey(diffAngle)
		
		if nearestKey == None or nearestKey == 0:
			return
	
		print "Target angle: ", angle
		print "Current angle: ", self.currAngle
		print "NearestKey: ", nearestKey
		
		values = moveValues[nearestKey]
		for value in values:
			self.writeNum(value)

		self.currAngle += nearestKey 
		self.currAngle = normalize_value(self.currAngle)

	def showDebug(self):
		print "CurrAngle: ", self.currAngle
			
def main():
	try:
		sc = ServoControl()
	except Exception, e:
		print "Dispositivo no conectado"
		raise
	#values = [90, 95, 90, 85]#, 90, 110, 90, 70]
	#values = [70, 90, 110, 90, 75, 90, 105, 90]
	#values = [80, 90, 100, 90, 85, 90, 95, 90]

	#valid pairs
	values = [60, 117]
	values = [73, 104] # 90g
	values = [79, 99] # 45g
	count = 0
	try:
		while True:
			ml = raw_input("more or less: ")
			if(ml == '+'):
				sc.writeNum(values[1])
			elif(ml == '-'):
				sc.writeNum(values[0])
	except KeyboardInterrupt:
		sc.write(str(90) + "\n")
		sys.exit()

if __name__ == '__main__':
	main()