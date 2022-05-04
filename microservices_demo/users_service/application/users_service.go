package application

import (
	"users_service/domain"
	"go.mongodb.org/mongo-driver/bson/primitive"
)

type UsersService struct {
	store domain.UsersStore
}

func NewUsersService(store domain.UsersStore) *UsersService {
	return &UsersService{
		store: store,
	}
}

func (service *UsersService) Get(id primitive.ObjectID) (*domain.User, error) {
	return service.store.Get(id)
}

func (service *UsersService) GetAll() ([]*domain.User, error) {
	return service.store.GetAll()
}
