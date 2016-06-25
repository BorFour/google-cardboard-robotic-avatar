#!/bin/bash

PIDS=$(pgrep vlc)
sudo fuser -k 8554/udp || true
sudo fuser -k 8556/udp || true
for i in $PIDS; do
	kill -9 $i
done
rm -f ~*