package config

import "os"

type Config struct {
	Port        string
	UsersHost   string
	UsersPort   string
	ProfileHost string
	ProfilePort string
}

func NewConfig() *Config {
	return &Config{
		Port:        os.Getenv("GATEWAY_PORT"),
		UsersHost:   os.Getenv("USERS_SERVICE_HOST"),
		UsersPort:   os.Getenv("USERS_SERVICE_PORT"),
		ProfileHost: os.Getenv("PROFILE_SERVICE_HOST"),
		ProfilePort: os.Getenv("PROFILE_SERVICE_PORT"),
	}
}
