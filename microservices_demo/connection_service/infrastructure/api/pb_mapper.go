package api

import (
	pb "../../../XML-project-team-5/microservices_demo/common/proto/connection_service"
	"connection_service/domain"
)

func mapConnection(connection *domain.Connection) *pb.Connection {
	productPb := &pb.Connection{}
	return productPb
}
