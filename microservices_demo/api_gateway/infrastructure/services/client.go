package services

import (
	auth "github.com/tamararankovic/microservices_demo/common/proto/auth_service"
	catalogue "github.com/tamararankovic/microservices_demo/common/proto/catalogue_service"
	ordering "github.com/tamararankovic/microservices_demo/common/proto/ordering_service"
	shipping "github.com/tamararankovic/microservices_demo/common/proto/shipping_service"

	"google.golang.org/grpc"
	"google.golang.org/grpc/credentials/insecure"
	"log"
)

func NewCatalogueClient(address string) catalogue.CatalogueServiceClient {
	conn, err := getConnection(address)
	if err != nil {
		log.Fatalf("Failed to start gRPC connection to Catalogue service: %v", err)
	}
	return catalogue.NewCatalogueServiceClient(conn)
}

func NewOrderingClient(address string) ordering.OrderingServiceClient {
	conn, err := getConnection(address)
	if err != nil {
		log.Fatalf("Failed to start gRPC connection to Ordering service: %v", err)
	}
	return ordering.NewOrderingServiceClient(conn)
}

func NewShippingClient(address string) shipping.ShippingServiceClient {
	conn, err := getConnection(address)
	if err != nil {
		log.Fatalf("Failed to start gRPC connection to Shipping service: %v", err)
	}
	return shipping.NewShippingServiceClient(conn)
}

func NewAuthClient(address string) auth.AuthServiceClient {
	conn, err := getConnection(address)
	if err != nil {
		log.Fatalf("Failed to start gRPC connection to Auth service: %v", err)
	}
	return auth.NewAuthServiceClient(conn)
}

func getConnection(address string) (*grpc.ClientConn, error) {
	return grpc.Dial(address, grpc.WithTransportCredentials(insecure.NewCredentials()))
}
