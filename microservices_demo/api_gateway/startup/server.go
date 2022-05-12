package startup

import (
	"context"
	"fmt"
	"github.com/grpc-ecosystem/grpc-gateway/v2/runtime"
	"github.com/tamararankovic/microservices_demo/api_gateway/infrastructure/api"
	cfg "github.com/tamararankovic/microservices_demo/api_gateway/startup/config"
	catalogueGw "github.com/tamararankovic/microservices_demo/common/proto/catalogue_service"
	inventoryGw "github.com/tamararankovic/microservices_demo/common/proto/inventory_service"
	orderingGw "github.com/tamararankovic/microservices_demo/common/proto/ordering_service"
	shippingGw "github.com/tamararankovic/microservices_demo/common/proto/shipping_service"
	usersGw "github.com/tamararankovic/microservices_demo/common/proto/users_service"
	"google.golang.org/grpc"
	"google.golang.org/grpc/credentials/insecure"
	"log"
	"net/http"
)

type Server struct {
	config *cfg.Config
	mux    *runtime.ServeMux
}

func NewServer(config *cfg.Config) *Server {
	server := &Server{
		config: config,
		mux:    runtime.NewServeMux(),
	}
	server.initHandlers()
	server.initCustomHandlers()
	return server
}

func (server *Server) initHandlers() {
	opts := []grpc.DialOption{grpc.WithTransportCredentials(insecure.NewCredentials())}
	catalogueEndpoint := fmt.Sprintf("%s:%s", server.config.CatalogueHost, server.config.CataloguePort)
	err := catalogueGw.RegisterCatalogueServiceHandlerFromEndpoint(context.TODO(), server.mux, catalogueEndpoint, opts)
	if err != nil {
		panic(err)
	}
	orderingEndpoint := fmt.Sprintf("%s:%s", server.config.OrderingHost, server.config.OrderingPort)
	err = orderingGw.RegisterOrderingServiceHandlerFromEndpoint(context.TODO(), server.mux, orderingEndpoint, opts)
	if err != nil {
		panic(err)
	}
	shippingEndpoint := fmt.Sprintf("%s:%s", server.config.ShippingHost, server.config.ShippingPort)
	err = shippingGw.RegisterShippingServiceHandlerFromEndpoint(context.TODO(), server.mux, shippingEndpoint, opts)
	if err != nil {
		panic(err)
	}
	inventoryEndpoint := fmt.Sprintf("%s:%s", server.config.InventoryHost, server.config.InventoryPort)
	err = inventoryGw.RegisterInventoryServiceHandlerFromEndpoint(context.TODO(), server.mux, inventoryEndpoint, opts)
	if err != nil {
		panic(err)
	}
	usersEndpoint := fmt.Sprintf("%s:%s", server.config.UsersHost, server.config.UsersPort)
	err = usersGw.RegisterUsersServiceHandlerFromEndpoint(context.TODO(), server.mux, usersEndpoint, opts)
	if err != nil {
		panic(err)
	}
}

func (server *Server) initCustomHandlers() {
	catalogueEndpoint := fmt.Sprintf("%s:%s", server.config.CatalogueHost, server.config.CataloguePort)
	orderingEndpoint := fmt.Sprintf("%s:%s", server.config.OrderingHost, server.config.OrderingPort)
	shippingEndpoint := fmt.Sprintf("%s:%s", server.config.ShippingHost, server.config.ShippingPort)
	orderingHandler := api.NewOrderingHandler(orderingEndpoint, catalogueEndpoint, shippingEndpoint)
	orderingHandler.Init(server.mux)
}

func (server *Server) Start() {
	log.Fatal(http.ListenAndServe(fmt.Sprintf(":%s", server.config.Port), server.mux))
}
