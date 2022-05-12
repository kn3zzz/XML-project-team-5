package domain

import (
	"go.mongodb.org/mongo-driver/bson/primitive"
	"time"
)

type Gender int8

const (
	Female Gender = iota
	Male
)

func (gender Gender) String() string {
	switch gender {
	case Female:
		return "Female"
	case Male:
		return "Male"
	}
	return "Other"
}

type User struct {
	Id          primitive.ObjectID `bson:"_id"`
	Name        string             `bson:"name"`
	LastName    string             `bson:"last_name"`
	Email       string             `bson:"email"`
	PhoneNumber string             `bson:"phone_number"`
	Gender      Gender             `bson:"gender"`
	BirthDate   time.Time          `bson:"birth_date"`
	Username    string             `bson:"username"`
	Password    string             `bson:"password"`
	Biography   string             `bson:"biography"`
}
