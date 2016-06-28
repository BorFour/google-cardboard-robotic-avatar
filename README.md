# README #



## Resumen ##

El objetivo principal de este Trabajo de Fin de Grado es desarrollar un sistema distribuido heterogéneo, combinando visión remota y realidad virtual, donde el usuario pueda ver a través de los ojos de un *avatar robótico*.

El cliente consiste en unas gafas de realidad virtual, como lo son Google Cardboard. Es una artilugio de cartón con dos huecos para un par de lentes, que apuntan a una cavidad donde se ha de situar un smarthphone. Una aplicación mostrará dos imágenes en la pantalla del dispositivo móvil que serán observadas a través de estas lentes por el usuario.

En la parte del servidor tenemos un par de videocámaras situadas en una estructura sobre un motor. Una parte del servidor envía los fotogramas capturados por las cámaras a través de un protocolo de multimedia en red hasta el cliente, donde cada transmisión se corresponde con un ojo del usuario.

Por otra parte, el servidor también se encargará de hacer rotar la estructura con las cámaras, controlando el motor según los movimientos de la cabeza del usuario. La aplicación envía periódicamente la rotación del dispositivo, que se calcula utilizando el acelerómetro y la brújula del smartphone, a este servidor de control.

## Abstract ##

The main goal of this Bachelor Thesis is to develop an heterogeneous distributed system, combining remote vision and virtual reality, where the user can look through the eyes of a *robotic avatar*.

The client consists of a pair of virtual reality glasses, such as Google Cardboard. It is a cardboard made gadget with two holes for a pair of lens that look to a cavity where the smartphone must be placed. An application will show two images on the mobile device's screen that will be observed through this lens by the user.

There is a couple of videocameras on the server side, assembled in a structure on a motor. A part of the server sends the frames captured by the cameras using a multimedia networking protocol to the client, where each stream corresponds to an eye.

On the other hand, the server is also responsible for rotating the structure with the cameras controlling the motor according to the movements of the user's head. The application periodically sends the rotation of the device, which is calculated using the smartphone's accelerometer and compass, to the control server.

## Cómo descargar el proyecto ##

```bash
git clone https://BorFour@bitbucket.org/BorFour/googlecardboardroboticavatar.git
```

## Dependencias ##

* Sistema operativo basado en UNIX
* Git
* VLC
* Python 2.7
* Arduino IDE

## Código de Arduino ##

Para compilar y subir a la placa el código del arduino, deben seguirse los siguientes pasos:

1. Conectar el arduino al PC con el cable USB
2. Abrir el código. File -> Open... -> <ruta del proyecto>/src/ArduinoCode/servo_serial_write/servo_serial_write.ino
3. Importar la librería VarSpeedServo. Sketch -> Import library... -> Add library... -> <ruta del proyecto>/src/ArduinoCode/VarSpeedServo.zip
4. Compilar y subir el código. Ctrl + U.


## Despliegue de los servidores ##

En el directorio raíz, ejecutar el siguiente comando en la terminal de Linux:

```bash
make
```

Esto hará que se ejecuten los dos siguientes scripts:

### Despliegue de los servidores de vídeo ###

El script src/VideoServers/deploy.sh despliega los servidores de vídeo en los puertos 8554 y 8556 (cámaras izquierda y cámara derecha respectivamente)

### Despliegue del servidor de control ###

Despliega el servidor UDP en el puerto 8558, asiocándolo a la terminal en la que se ejecute el script control_server.sh

## Instalación de la aplicación Android ##

1. Enviar el archivo gcra_client.apk al dispositivo móvil.
2. Al abrir el archivo, Android se encargará de instalarlo
