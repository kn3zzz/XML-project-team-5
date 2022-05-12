package main

import (
	"users_service/startup"
	cfg "users_service/startup/config"
)

func main() {
	config := cfg.NewConfig()
	server := startup.NewServer(config)
	server.Start()
}
