package api

import (
	pb "github.com/tamararankovic/microservices_demo/common/proto/users_service"
	"google.golang.org/protobuf/types/known/timestamppb"
	"users_service/domain"
)

func mapUser(user *domain.User) *pb.User {
	userPb := &pb.User{
		Id:          user.Id.Hex(),
		Name:        user.Name,
		LastName:    user.LastName,
		Email:       user.Email,
		PhoneNumber: user.PhoneNumber,
		Gender:      mapGender(user.Gender),
		BirthDate:   timestamppb.New(user.BirthDate),
		Username:    user.Username,
		Biography:   user.Biography,
	}
	return userPb
}

func mapGender(gender domain.Gender) pb.Gender {
	switch gender {
	case domain.Female:
		return pb.Gender_Female
	case domain.Male:
		return pb.Gender_Male
	}
	return pb.Gender_Male
}
