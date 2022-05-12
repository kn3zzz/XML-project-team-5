package api

import (
	"context"
	pb "github.com/tamararankovic/microservices_demo/common/proto/users_service"
	"go.mongodb.org/mongo-driver/bson/primitive"
	"users_service/application"
)

type UsersHandler struct {
	pb.UnimplementedUsersServiceServer
	service *application.UsersService
}

func NewUsersHandler(service *application.UsersService) *UsersHandler {
	return &UsersHandler{
		service: service,
	}
}

func (handler *UsersHandler) Get(ctx context.Context, request *pb.GetRequest) (*pb.GetResponse, error) {
	id := request.Id
	objectId, err := primitive.ObjectIDFromHex(id)
	if err != nil {
		return nil, err
	}
	user, err := handler.service.Get(objectId)
	if err != nil {
		return nil, err
	}
	userPb := mapUser(user)
	response := &pb.GetResponse{
		User: userPb,
	}
	return response, nil
}

func (handler *UsersHandler) GetAll(ctx context.Context, request *pb.GetAllRequest) (*pb.GetAllResponse, error) {
	users, err := handler.service.GetAll()
	if err != nil {
		return nil, err
	}
	response := &pb.GetAllResponse{
		Users: []*pb.User{},
	}
	for _, user := range users {
		current := mapUser(user)
		response.Users = append(response.Users, current)
	}
	return response, nil
}
