
#include <VarSpeedServo.h> 

VarSpeedServo myservo;  // create servo object to control a servo

const int INTERVAL = 500;
const int MIN_VALUE = 50;
const int MAX_VALUE = 150;
const int STILL_VALUE = 90;

int myRead() {
  //return (int) Serial.read();
    String inString = "";   
    while (Serial.available() > 0) {
      int inChar = Serial.read();
      if (isDigit(inChar)) {
        inString += (char)inChar;
      }
      if (inChar == '\n') {
        Serial.println(inString.toInt());
        int ret = inString.toInt();
        if(ret < MIN_VALUE || ret > MAX_VALUE) return STILL_VALUE;
        return ret;
      }
    }
    return STILL_VALUE;
  //return Serial.read();
}

void setup() {
  Serial.begin(9600);
  while (!Serial) {
    ; // wait for serial port to connect. Needed for native USB port only
  }
  myservo.attach(3);  // attaches the servo on pin 3 to the servo object
}

void loop() {
  int value = myRead();
//  myservo.write(value);
  myservo.write(value, 50, true);
  delay(INTERVAL);
}


