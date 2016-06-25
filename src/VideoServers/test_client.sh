#!/bin/bash

IPS=(rtp://127.0.0.1:5004/ rtp://127.0.0.1:5005/)
N=1
for i in $IPS; do
	vlc -vvv --network-caching 200 $i >/dev/null 2>/dev/null
done
