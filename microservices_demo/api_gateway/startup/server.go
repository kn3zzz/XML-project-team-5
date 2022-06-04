package startup

import (
	"context"
	"fmt"
	"github.com/grpc-ecosystem/grpc-gateway/v2/runtime"
	cfg "github.com/tamararankovic/microservices_demo/api_gateway/startup/config"
	profileGw "github.com/tamararankovic/microservices_demo/common/proto/profile_service"
	usersGw "github.com/tamararankovic/microservices_demo/common/proto/users_service"
	"google.golang.org/grpc"
	"google.golang.org/grpc/credentials/insecure"
	"log"
	"net/http"
)

type Server struct {
	config *cfg.Config
	mux    *runtime.ServeMux
}

func NewServer(config *cfg.Config) *Server {
	server := &Server{
		config: config,
		mux:    runtime.NewServeMux(),
	}
	server.initHandlers()
	return server
}

func (server *Server) initHandlers() {
	opts := []grpc.DialOption{grpc.WithTransportCredentials(insecure.NewCredentials())}
	usersEndpoint := fmt.Sprintf("%s:%s", server.config.UsersHost, server.config.UsersPort)
	err := usersGw.RegisterUsersServiceHandlerFromEndpoint(context.TODO(), server.mux, usersEndpoint, opts)
	if err != nil {
		panic(err)
	}
	profileEndpoint := fmt.Sprintf("%s:%s", server.config.ProfileHost, server.config.ProfilePort)
	err = profileGw.RegisterProfileServiceHandlerFromEndpoint(context.TODO(), server.mux, profileEndpoint, opts)
	if err != nil {
		panic(err)
	}
}

func (server *Server) Start() {
	log.Fatal(http.ListenAndServe(fmt.Sprintf(":%s", server.config.Port), server.mux))
}
