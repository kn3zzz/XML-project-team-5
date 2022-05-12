package domain

import (
	"go.mongodb.org/mongo-driver/bson/primitive"
)

type UsersStore interface {
	Get(id primitive.ObjectID) (*User, error)
	GetAll() ([]*User, error)
	Insert(user *User) error
	DeleteAll()
}
