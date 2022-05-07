package domain

import (
	"go.mongodb.org/mongo-driver/bson/primitive"
)

type ConnectionStore interface {
	Get(id primitive.ObjectID) (*Connection, error)
	GetAll() ([]*Connection, error)
	Insert(product *Connection) error
	DeleteAll()
}
