package api

import (
	"context"
	"github.com/mihailomajstorovic47/XML-project-team-5/microservices_demo/auth_service/application"
	ab "github.com/tamararankovic/microservices_demo/common/proto/auth_service"
	_ "net/http"
)

type UserHandler struct {
	ab.UnimplementedAuthServiceServer
	service *application.AuthService
}

func NewUserHandler(service *application.AuthService) *UserHandler {
	return &UserHandler{
		service: service,
	}
}

func (handler *UserHandler) GetAll(ctx context.Context, request *ab.GetAllUsers) (*ab.GetAllUsers, error) {
	users, err := handler.service.GetAll()
	if err != nil {
		return nil, err
	}
	response := &ab.GetAllUsers{
		users: []*ab.User{},
	}
}
