package startup

import (
	"fmt"
	profile "github.com/tamararankovic/microservices_demo/common/proto/profile_service"
	"go.mongodb.org/mongo-driver/mongo"
	"google.golang.org/grpc"
	"google.golang.org/grpc/reflection"
	"log"
	"net"
	"profile_service/application"
	"profile_service/domain"
	"profile_service/infrastructure/api"
	"profile_service/infrastructure/persistence"
	"profile_service/startup/config"
)

type Server struct {
	config *config.Config
}

func NewServer(config *config.Config) *Server {
	return &Server{
		config: config,
	}
}

const (
	QueueGroup = "profile_service"
)

func accessibleRoles() map[string]string {
	const profileServicePath = "/profile.ProfileService/"

	return map[string]string{
		profileServicePath + "GetAll":        "search:all-profiles",
		profileServicePath + "GenerateToken": "write:profile-token",
		//profileServicePath + "Get":    {"user"},
		//profileServicePath + "Create": {"user"},
		//profileServicePath + "Update": {"user"},
	}
}

func (server *Server) Start() {
	mongoClient := server.initMongoClient()
	profileStore := server.initProfileStore(mongoClient)

	profileService := server.initProfileService(profileStore)

	profileHandler := server.initProfileHandler(profileService)

	server.startGrpcServer(profileHandler)
}

func (server *Server) initMongoClient() *mongo.Client {
	client, err := persistence.GetClient(server.config.ProfileDBHost, server.config.ProfileDBPort)
	if err != nil {
		log.Fatal(err)
	}
	return client
}

func (server *Server) initProfileStore(client *mongo.Client) domain.ProfileStore {
	store := persistence.NewProfileMongoDBStore(client)
	err := store.DeleteAll()
	if err != nil {
		return nil
	}
	for _, Profile := range profiles {
		err := store.Create(Profile)
		if err != nil {
			log.Fatal(err)
		}
	}
	return store
}

func (server *Server) initProfileService(store domain.ProfileStore) *application.ProfileService {
	return application.NewProfileService(store)
}

func (server *Server) initProfileHandler(service *application.ProfileService) *api.ProfileHandler {
	return api.NewProfileHandler(service)
}

func (server *Server) startGrpcServer(userHandler *api.ProfileHandler) {
	listener, err := net.Listen("tcp", fmt.Sprintf(":%s", server.config.Port))
	if err != nil {
		log.Fatalf("failed to listen: %v", err)
	}
	grpcServer := grpc.NewServer()
	reflection.Register(grpcServer)
	profile.RegisterProfileServiceServer(grpcServer, userHandler)
	if err := grpcServer.Serve(listener); err != nil {
		log.Fatalf("failed to serve: %s", err)
	}
}