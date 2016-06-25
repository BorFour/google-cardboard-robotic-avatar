#!/bin/bash

IPS=(rtsp://:8554/ rtsp://:8556/)
N=1
for i in $IPS; do
	vlc -vvv --network-caching 200 $i >/dev/null 2>/dev/null
done
