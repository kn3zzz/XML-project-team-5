package config

import "os"

type Config struct {
	Port          string
	AuthDBHost    string
	AuthDBPort    string
	Email         string
	PasswordEmail string
	ApiGatwayHost string
}

func NewConfig() *Config {
	return &Config{
		Port:          os.Getenv("AUTH_SERVICE_PORT"),
		AuthDBHost:    os.Getenv("AUTH_DB_HOST"),
		AuthDBPort:    os.Getenv("AUTH_DB_PORT"),
		Email:         os.Getenv("DISLINKT_EMAIL"),
		PasswordEmail: os.Getenv("EMAIL_PASSWORD"),
		ApiGatwayHost: os.Getenv("API_GATEWAY_HOST"),
	}
}
