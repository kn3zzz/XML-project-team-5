package application

import (
	"connection_service/domain"
	"go.mongodb.org/mongo-driver/bson/primitive"
)

type ConnectionService struct {
	store domain.ConnectionStore
}

func NewConnectionService(store domain.ConnectionStore) *ConnectionService {
	return &ConnectionService{
		store: store,
	}
}

func (service *ConnectionService) Get(id primitive.ObjectID) (*domain.Connection, error) {
	return service.store.Get(id)
}