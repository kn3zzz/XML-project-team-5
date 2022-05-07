package api

import (
	pb "common/proto/connection_service"
	"connection_service/domain"
)

func mapConnection(connection *domain.Connection) *pb.Connection {
	productPb := &pb.Connection{
		Id            user.Id.Hex(),
		Name          user.Name,
		Lastname      user.Lastname,
		Email          user.Email,
		PhoneNumber	   user.PhoneNumber,
		Gender		   user.Gender,
		BirthDate	   user.BirthDate,
		Username	   user.Username,
		Biography	   user.Biography
	}
	return productPb
}
