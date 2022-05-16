package api

import (
	"context"
	"fmt"
	"github.com/mihailomajstorovic47/XML-project-team-5/microservices_demo/auth_service/application"
	"github.com/mihailomajstorovic47/XML-project-team-5/microservices_demo/auth_service/domain"
	pb "github.com/tamararankovic/microservices_demo/common/proto/auth_service"
	"net/http"
	_ "net/http"
)

type UserHandler struct {
	pb.UnimplementedAuthServiceServer
	service *application.AuthService
}

func NewUserHandler(service *application.AuthService) *UserHandler {
	return &UserHandler{
		service: service,
	}
}

func (handler *UserHandler) Register(ctx context.Context, request *pb.RegisterRequest) (*pb.RegisterResponse, error) {

	var user domain.User
	user1, _ := handler.service.GetByUsername(ctx, request.Data.Username)
	if user1 != nil {
		return &pb.RegisterResponse{
			Status: http.StatusUnprocessableEntity,
			Error:  "Username is not unique",
			UserID: "",
		}, nil
	}
	user.Username = request.Data.GetUsername()
	user.Password = request.Data.GetPassword()

	userID, err := handler.service.Create(ctx, &user) //userID
	if err != nil {
		fmt.Println(err.Error())
		return &pb.RegisterResponse{
			Status: http.StatusUnauthorized,
			UserID: "",
		}, err
	}
	return &pb.RegisterResponse{
		Status: http.StatusCreated,
		UserID: userID,
	}, nil

}
