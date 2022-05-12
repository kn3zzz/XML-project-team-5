package config

import "os"

type Config struct {
	Port        string
	UsersDBHost string
	UsersDBPort string
}

func NewConfig() *Config {
	return &Config{
		Port:        os.Getenv("USERS_SERVICE_PORT"),
		UsersDBHost: os.Getenv("USERS_DB_HOST"),
		UsersDBPort: os.Getenv("USERS_DB_PORT"),
	}
}
