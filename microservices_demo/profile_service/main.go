package main

import (
	"profile_service/startup"
	cfg "profile_service/startup/config"
)

func main() {
	config := cfg.NewConfig()
	server := startup.NewServer(config)
	server.Start()
}
