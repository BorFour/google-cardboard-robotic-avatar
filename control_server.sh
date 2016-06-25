#!/bin/bash

IP=$(ifconfig eth0 | grep -oP 'inet addr:\K\S+')
PORT="8558"
python src/ControlServer/udp_server.py $IP $PORT