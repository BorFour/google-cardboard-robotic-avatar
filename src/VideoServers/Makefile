
all: redeploy
test: redeploy test_client
test_client:
	@echo "Running VLC RTP Client"
	@./test_client.sh
redeploy:
	@echo "............................."
	@sleep 3
	@echo "Killing VLC processes"
	@./kill_all.sh
	@echo "Deploying RTP Webcam Servers"
	@./deploy.sh
status:
	pgrep vlc
	sudo fuser 8554/udp
	sudo fuser 8554/udp