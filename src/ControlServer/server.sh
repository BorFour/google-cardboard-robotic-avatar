#!/bin/bash

IP=$(ifconfig eth0 | grep -oP 'inet addr:\K\S+')
PORT="8558"
python udp_server.py $IP $PORT