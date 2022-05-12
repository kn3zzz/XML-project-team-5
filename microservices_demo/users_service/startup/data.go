package startup

import (
	"go.mongodb.org/mongo-driver/bson/primitive"
	"time"
	"users_service/domain"
)

var users = []*domain.User{
	{
		Id:          getObjectId("623b0cc3a34d25d8567f9f82"),
		Name:        "Milos",
		LastName:    "Markovic",
		Email:       "milos1701@gmail.com",
		PhoneNumber: "060999000",
		Gender:      domain.Male,
		BirthDate:   time.Date(2000, 1, 17, 0, 0, 0, 0, time.UTC),
		Username:    "misa1701",
		Password:    "misa",
		Biography:   "biografija",
	},
}

func getObjectId(id string) primitive.ObjectID {
	if objectId, err := primitive.ObjectIDFromHex(id); err == nil {
		return objectId
	}
	return primitive.NewObjectID()
}
