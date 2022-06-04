package startup

import (
	"go.mongodb.org/mongo-driver/bson/primitive"
	"profile_service/domain"
	"time"
)

var profiles = []*domain.Profile{
	{
		Id:             getObjectId("93605d1bh23b8ae748f23ab5"),
		Username:       "admin",
		FirstName:      "Stevan",
		LastName:       "Stevanovic",
		FullName:       "Stevan Stevanovic",
		DateOfBirth:    time.Time{},
		PhoneNumber:    "0658966543",
		Email:          "sustinski@gmail.com",
		Gender:         "male",
		Biography:      "Survivor",
		Education:      nil,
		WorkExperience: nil,
		Skills:         nil,
		Interests:      nil,
	},
}

func getObjectId(id string) primitive.ObjectID {
	if objectId, err := primitive.ObjectIDFromHex(id); err == nil {
		return objectId
	}
	return primitive.NewObjectID()
}
