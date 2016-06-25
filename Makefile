
all: redeploy_video_servers deploy_control_server 

redeploy_video_servers:
	bash src/VideoServers/kill_all.sh
	bash src/VideoServers/deploy.sh

deploy_control_server:
	bash control_server.sh