#!/bin/bash

CAMERAS=(v4l2:///dev/video0 v4l2:///dev/video1)
RTP=("sdp=rtsp://:8554/" "sdp=rtsp://:8556/")
CODEC_PARAMS="preset=ultrafast,tune=zerolatency,intra-refresh,lookahead=10,keyint=15"
PARAMS="vcodec=h264,venc=x264{$CODEC_PARAMS}"
OUTPUT="width=640,height=720,fps=5"
TRANSCODE_0="#transcode{$PARAMS,$OUTPUT}:rtp{$RTP[0]}"
TRANSCODE_1="#transcode{$PARAMS,$OUTPUT}:rtp{$RTP[1]}"
cvlc -vvv ${CAMERAS[0]} --sout $TRANSCODE_0 >/dev/null 2>/dev/null &
cvlc -vvv ${CAMERAS[1]} --sout $TRANSCODE_1 >/dev/null 2>/dev/null &

#FORMATS=(DIV3 mp4v h264)
#vb=800,pass=2,acodec=none ,scale=1, fps=10,
#TRANSCODE_0='#transcode{vcodec=h263,venc=x264{preset=ultrafast,tune=zerolatency,intra-refresh,lookahead=10,keyint=15},scale=auto,fps=10}:rtp{dst=127.0.0.1,port=5004,mux=ts, caching=50}'
#TRANSCODE_0='#transcode{vcodec=h264,venc=x264{preset=ultrafast,tune=zerolatency,intra-refresh,lookahead=10,keyint=15},scale=auto,fps=10}:rtp{dst=239.255.12.42,port=5004,mux=ts, caching=50}'
#TRANSCODE_0='#transcode{soverlay,ab=42,samplerate=44100,channels=1,acodec=mp4a,vcodec=h264,width=320,height=180,vfilter="canvas{width=320,height=180,aspect=16:9}",fps=25,vb=200,venc=x264{vbv-bufsize=500,partitions=all,level=12,no-cabac,subme=7,threads=4,ref=2,mixed-refs=1,bframes=0,min-keyint=1,keyint=50,trellis=2,direct=auto,qcomp=0.0,qpmax=51}}:gather:rtp{mp4a-latm,sdp=rtsp://0.0.0.0:5004/live.sdp}'
#TRANSCODE_0='#transcode{vcodec=h264,vb=56,fps=12,scale=0.25,acodec=none}:rtp{sdp=rtsp://:8554/} :sout-keep}'
#TRANSCODE_0='#transcode{vcodec=h264,venc=x264{8x8dct=1 aq-mode=2 b-adapt=2 bframes=1 chroma-qp-offset=2 colormatrix=smpte170m deblock=0:0 direct=auto ipratio=1.41 keyint=240 level=3.1 me=umh merange=16 min-keyint=auto mixed-refs=1 no-mbtree=0 partitions=all profile=high psy-rd=0.5:0.0 qcomp=0.6 qpmax=51 qpmin=10 qpstep=4 ratetol=10 rc-lookahead=30 ref=1 scenecut=40 subme=10 threads=0 trellis=2 weightb=1 weightp=2},width=640,height=720,fps=5}:rtp{sdp=rtsp://:8554/}'
#scale=1, fps=10,
#TRANSCODE_1='#transcode{vcodec=h263,venc=x264{preset=ultrafast,tune=zerolatency,intra-refresh,lookahead=10,keyint=15},scale=auto,fps=10}:rtp{dst=127.0.0.1,port=5006,mux=ts, caching=50}'
#TRANSCODE_1='#transcode{vcodec=h264,venc=x264{preset=ultrafast,tune=zerolatency,intra-refresh,lookahead=10,keyint=15},scale=auto,fps=10}:rtp{dst=239.255.12.42,port=5006,mux=ts, caching=50}'
#TRANSCODE_1='#transcode{vcodec=h264,vb=56,fps=12,scale=0.25,acodec=none}:rtp{sdp=rtsp://:8556/} :sout-keep}'
#TRANSCODE_1='#transcode{vcodec=h264,venc=x264{8x8dct=1 aq-mode=2 b-adapt=2 bframes=1 chroma-qp-offset=2 colormatrix=smpte170m deblock=0:0 direct=auto ipratio=1.41 keyint=240 level=3.1 me=umh merange=16 min-keyint=auto mixed-refs=1 no-mbtree=0 partitions=all profile=high psy-rd=0.5:0.0 qcomp=0.6 qpmax=51 qpmin=10 qpstep=4 ratetol=10 rc-lookahead=30 ref=1 scenecut=40 subme=10 threads=0 trellis=2 weightb=1 weightp=2}:rtp{sdp=rtsp://:8556/}'