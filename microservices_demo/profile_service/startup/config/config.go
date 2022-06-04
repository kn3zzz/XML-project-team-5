package config

import (
	"os"
)

type Config struct {
	Port          string
	ProfileDBHost string
	ProfileDBPort string
	NatsHost      string
	NatsPort      string
	NatsUser      string
	NatsPass      string
}

func NewConfig() *Config {
	return &Config{
		Port:          os.Getenv("PROFILE_SERVICE_PORT"),
		ProfileDBHost: os.Getenv("PROFILE_DB_HOST"),
		ProfileDBPort: os.Getenv("PROFILE_DB_PORT"),
		NatsHost:      os.Getenv("NATS_HOST"),
		NatsPort:      os.Getenv("NATS_PORT"),
		NatsUser:      os.Getenv("NATS_USER"),
		NatsPass:      os.Getenv("NATS_PASS"),
	}
}
