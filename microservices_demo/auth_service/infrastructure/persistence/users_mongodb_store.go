package persistence

import (
	"context"
	"fmt"
	"github.com/mihailomajstorovic47/XML-project-team-5/microservices_demo/auth_service/domain"
	"go.mongodb.org/mongo-driver/bson"
	"go.mongodb.org/mongo-driver/mongo"
	"go.mongodb.org/mongo-driver/mongo/options"
	"log"
)

const (
	DATABASE   = "auth"
	COLLECTION = "users"
)

type UserMongoDBStore struct {
	users *mongo.Collection
}

func NewUserMongoDBStore(client *mongo.Client) domain.UsersStore {
	users := client.Database(DATABASE).Collection(COLLECTION)
	return &UserMongoDBStore{
		users: users,
	}
}

func (store *UserMongoDBStore) AddNew(user *domain.User) {
	insertResult, err := store.users.InsertOne(context.TODO(), user)
	if err != nil {
		log.Fatal(err)
	}
	fmt.Println("Inserted a single document: ", insertResult.InsertedID)
	return
}

func (store *UserMongoDBStore) FindByUsername(username string) (domain.User, error) {
	var user domain.User
	filter := bson.D{{"username", username}}
	err := store.users.FindOne(context.TODO(), filter).Decode(&user)
	return user, err
}

func (store *UserMongoDBStore) FindAll() ([]domain.User, error) {
	cur, err := store.users.Find(context.TODO(), bson.D{{}}, options.Find())
	if err != nil {
		log.Fatal(err)
	}
	var users []domain.User
	for cur.Next(context.TODO()) {
		var elem domain.User
		err := cur.Decode(&elem)
		if err != nil {
			log.Fatal(err)
		}
		users = append(users, elem)
	}
	cur.Close(context.TODO())
	fmt.Println(users)
	return users, err
}
