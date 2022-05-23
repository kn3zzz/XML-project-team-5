package api

import (
	"context"
	"fmt"
	"github.com/go-playground/validator/v10"
	"github.com/mihailomajstorovic47/XML-project-team-5/microservices_demo/auth_service/application"
	"github.com/mihailomajstorovic47/XML-project-team-5/microservices_demo/auth_service/domain"
	"github.com/mihailomajstorovic47/XML-project-team-5/microservices_demo/auth_service/utils"
	pb "github.com/tamararankovic/microservices_demo/common/proto/auth_service"
	"go.mongodb.org/mongo-driver/bson/primitive"
	"net/http"
	_ "net/http"
	"regexp"
)

type UserHandler struct {
	pb.UnimplementedAuthServiceServer
	service *application.AuthService
	Jwt     utils.JwtWrapper
}

func NewUserHandler(service *application.AuthService) *UserHandler {
	return &UserHandler{
		service: service,
	}
}

func (handler *UserHandler) Register(ctx context.Context, request *pb.RegisterRequest) (*pb.RegisterResponse, error) {

	var user domain.User
	user1, _ := handler.service.GetByUsername(ctx, request.Data.Username)
	if user1 != nil {
		return &pb.RegisterResponse{
			Status: http.StatusUnprocessableEntity,
			Error:  "Username is not unique",
			UserID: "",
		}, nil
	}
	user.Username = request.Data.GetUsername()
	user.Password = request.Data.GetPassword()

	v := validator.New()
	handler.ValidatePassword(ctx, v)
	handler.ValidateUsername(ctx, v)
	errV := v.Struct(user)

	if errV != nil {
		return &pb.RegisterResponse{
			Status: http.StatusNotAcceptable,
			Error:  "Validation error" + " " + errV.Error(),
			UserID: "",
		}, errV
	}

	userID, err := handler.service.Create(ctx, &user) //userID
	if err != nil {
		fmt.Println(err.Error())
		return &pb.RegisterResponse{
			Status: http.StatusUnauthorized,
			UserID: "",
		}, err
	}
	return &pb.RegisterResponse{
		Status: http.StatusCreated,
		UserID: userID,
	}, nil

}

func (handler *UserHandler) Login(ctx context.Context, req *pb.LoginRequest) (*pb.LoginResponse, error) {

	user, err := handler.service.GetByUsername(ctx, req.Data.Username)
	if err != nil {
		return &pb.LoginResponse{
			Status: http.StatusNotFound,
			Error:  "User not found",
		}, nil
	}

	match := req.Data.Password == user.Password

	if !match {
		return &pb.LoginResponse{
			Status: http.StatusNotFound,
			Error:  "User not found",
		}, nil
	}

	token, _ := handler.Jwt.GenerateToken(user)

	return &pb.LoginResponse{
		Status:   http.StatusOK,
		Token:    token,
		Role:     domain.ConvertRoleToString(user.Role),
		Username: user.Username,
		UserID:   user.Id.Hex(),
	}, nil
}

func (handler *UserHandler) Validate(ctx context.Context, req *pb.ValidateRequest) (*pb.ValidateResponse, error) {
	claims, err := handler.Jwt.ValidateToken(req.Token)

	if err != nil {
		return &pb.ValidateResponse{
			Status: http.StatusBadRequest,
			Error:  err.Error(),
		}, nil
	}

	user, err := handler.service.Get(ctx, getObjectId(claims.Id))
	if err != nil {
		return &pb.ValidateResponse{
			Status: http.StatusNotFound,
			Error:  "User not found",
		}, nil
	}

	return &pb.ValidateResponse{
		Status: http.StatusOK,
		UserId: user.Id.Hex(),
	}, nil
}

func (handler *UserHandler) ExtractDataFromToken(ctx context.Context, req *pb.ExtractDataFromTokenRequest) (*pb.ExtractDataFromTokenResponse, error) {
	claims, err := handler.Jwt.ValidateToken(req.Token)

	if err != nil {
		return &pb.ExtractDataFromTokenResponse{
			Id:       "",
			Username: "",
		}, err
	}

	return &pb.ExtractDataFromTokenResponse{
		Id:       claims.Id,
		Username: claims.Username,
	}, nil

}

func getObjectId(id string) primitive.ObjectID {
	if objectId, err := primitive.ObjectIDFromHex(id); err == nil {
		return objectId
	}
	return primitive.NewObjectID()
}

func (handler *UserHandler) ValidatePassword(ctx context.Context, v *validator.Validate) {
	_ = v.RegisterValidation("password_validation", func(fl validator.FieldLevel) bool {
		if len(fl.Field().String()) < 8 {
			fmt.Println("Password must contain 8 characters or more!")
			return false
		}
		result, _ := regexp.MatchString("(.*[a-z].*)", fl.Field().String())
		if !result {
			fmt.Println("Password must contain lower case characters!")
		}
		result, _ = regexp.MatchString("(.*[A-Z].*)", fl.Field().String())
		if !result {
			fmt.Println("Password must contain upper case characters!")
		}
		result, _ = regexp.MatchString("(.*[0-9].*)", fl.Field().String())
		if !result {
			fmt.Println("Password must contain numbers!")
		}

		result, _ = regexp.MatchString("(.*[!@#$%^&*(){}\\[:;\\]<>,\\.?~_+\\-\\\\=|/].*)", fl.Field().String())
		if !result {
			fmt.Println("Password must contain special characters!")
		}
		return result
	})

}

func (handler *UserHandler) ValidateUsername(ctx context.Context, v *validator.Validate) {

	_ = v.RegisterValidation("username_validation", func(fl validator.FieldLevel) bool {
		if len(fl.Field().String()) < 4 || len(fl.Field().String()) > 16 {
			fmt.Println("Your username must be between 4 and 16 characters long.")
			return false
		}
		result, _ := regexp.MatchString("(.*[!@#$%^&*(){}\\[:;\\]<>,\\.?~_+\\-\\\\=|/].*)", fl.Field().String())
		if result {
			fmt.Println("Username contains special characters that are not allowed!")
		}
		return !result
	})

}
