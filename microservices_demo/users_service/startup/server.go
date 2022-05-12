package startup

import (
	"fmt"
	users_service "github.com/tamararankovic/microservices_demo/common/proto/users_service"
	"go.mongodb.org/mongo-driver/mongo"
	"google.golang.org/grpc"
	"log"
	"net"
	"users_service/application"
	"users_service/domain"
	"users_service/infrastructure/api"
	"users_service/infrastructure/persistence"
	"users_service/startup/config"
)

type Server struct {
	config *config.Config
}

func NewServer(config *config.Config) *Server {
	return &Server{
		config: config,
	}
}

func (server *Server) Start() {
	mongoClient := server.initMongoClient()
	usersStore := server.initUsersStore(mongoClient)

	usersService := server.initUsersService(usersStore)

	usersHandler := server.initUsersHandler(usersService)

	server.startGrpcServer(usersHandler)
}

func (server *Server) initMongoClient() *mongo.Client {
	client, err := persistence.GetClient(server.config.UsersDBHost, server.config.UsersDBPort)
	if err != nil {
		log.Fatal(err)
	}
	return client
}

func (server *Server) initUsersStore(client *mongo.Client) domain.UsersStore {
	store := persistence.NewUsersMongoDBStore(client)
	store.DeleteAll()
	for _, user := range users {
		err := store.Insert(user)
		if err != nil {
			log.Fatal(err)
		}
	}
	return store
}

func (server *Server) initUsersService(store domain.UsersStore) *application.UsersService {
	return application.NewUsersService(store)
}

func (server *Server) initUsersHandler(service *application.UsersService) *api.UsersHandler {
	return api.NewUsersHandler(service)
}

func (server *Server) startGrpcServer(usersHandler *api.UsersHandler) {
	listener, err := net.Listen("tcp", fmt.Sprintf(":%s", server.config.Port))
	if err != nil {
		log.Fatalf("failed to listen: %v", err)
	}
	grpcServer := grpc.NewServer()
	users_service.RegisterUsersServiceServer(grpcServer, usersHandler)
	if err := grpcServer.Serve(listener); err != nil {
		log.Fatalf("failed to serve: %s", err)
	}
}
