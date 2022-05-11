package application

import (
	"github.com/mihailomajstorovic47/XML-project-team-5/microservices_demo/auth_service/domain"
)

type AuthService struct {
	store domain.UsersStore
}

func NewAuthService(store domain.UsersStore) *AuthService {
	return &AuthService{
		store: store,
	}
}

func (service *AuthService) GetAll() ([]domain.User, error) {
	return service.store.FindAll()
}

func (service *AuthService) AddNewUser(user *domain.User) {
	service.store.AddNew(user)
}
