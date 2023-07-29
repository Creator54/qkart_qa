#!/usr/bin/env bash

# Function to check if Docker Compose services are running
check_services_running() {
	if [[ $(docker-compose ps -q | wc -l) -gt 0 ]]; then
		echo "Docker Compose services are already running."
		return 0
	else
		return 1
	fi
}

# Function to execute Docker Compose build and up commands
start_services() {
	echo "Building Docker Compose services..."
	docker-compose build

	echo "Starting Docker Compose services..."
	docker-compose up -d
}

# Main script logic
if ! check_services_running; then
	start_services
	sleep 3
fi

rm -rf ./screenshots/
echo 'VNC URL: http://localhost:7900/?autoconnect=1&password=secret&resize=scale'
