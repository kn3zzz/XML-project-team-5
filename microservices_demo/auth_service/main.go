package main

import (
	"github.com/mihailomajstorovic47/XML-project-team-5/microservices_demo/auth_service/startup"
	cfg "github.com/mihailomajstorovic47/XML-project-team-5/microservices_demo/auth_service/startup/config"
)

func main() {
	config := cfg.NewConfig()
	server := startup.NewServer(config)
	server.Start()
}
