package api

import (
	"context"
	"encoding/json"
	"fmt"
	"github.com/grpc-ecosystem/grpc-gateway/v2/runtime"
	"github.com/tamararankovic/microservices_demo/api_gateway/infrastructure/DTO"
	"github.com/tamararankovic/microservices_demo/api_gateway/infrastructure/services"
	pbAuth "github.com/tamararankovic/microservices_demo/common/proto/auth_service"
	"net/http"
)

type RegistrationHandler struct {
	authClientAddress string
	//profileClientAddress     string
	//connectionsClientAddress string
}

func NewRegistrationHandler(authClientAddress string) Handler {
	return &RegistrationHandler{
		authClientAddress: authClientAddress,
	}
}

func (handler *RegistrationHandler) Init(mux *runtime.ServeMux) {
	err := mux.HandlePath("POST", "/api/register", handler.Register)
	if err != nil {
		panic(err)
	}
}

func (handler *RegistrationHandler) Register(w http.ResponseWriter, r *http.Request, pathParams map[string]string) {

	var registerDTO DTO.RegisterDTO

	err := json.NewDecoder(r.Body).Decode(&registerDTO)
	if err != nil {
		http.Error(w, err.Error(), http.StatusBadRequest)
		fmt.Println(err.Error())
		return
	}

	fmt.Println("Registration: ", registerDTO.Username)

	userID, errAuth := handler.RegisterAuth(registerDTO)
	if errAuth != nil {
		http.Error(w, errAuth.Error(), http.StatusBadRequest)
		return
	}

	fmt.Println("successfully registered new user with ID:", userID)

	response, errRes := json.Marshal(&DTO.RegisterResponsDTO{Id: userID, Username: registerDTO.Username})
	if errRes != nil {
		w.WriteHeader(http.StatusInternalServerError)
		return
	}
	w.WriteHeader(http.StatusOK)
	w.Write(response)

}

func (handler *RegistrationHandler) RegisterAuth(registerDTO DTO.RegisterDTO) (string, error) {
	authS := services.NewAuthClient(handler.authClientAddress)
	response, err := authS.Register(context.TODO(), &pbAuth.RegisterRequest{Data: &pbAuth.RegisterRequestData{Username: registerDTO.Username, Password: registerDTO.Password}}) //TODO: izbaciti neiskrostene atribute
	if err != nil {
		fmt.Println(err.Error())
		return "", err
	}
	fmt.Println(response)
	return response.UserID, err
}
