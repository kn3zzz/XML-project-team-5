package persistence

import (
	"connection_service/domain"
	"context"
	"go.mongodb.org/mongo-driver/bson"
	"go.mongodb.org/mongo-driver/bson/primitive"
	"go.mongodb.org/mongo-driver/mongo"
)

const (
	DATABASE   = "connections"
	COLLECTION = "connection"
)

type ConnectionsMongoDBStore struct {
	connections *mongo.Collection
}

func NewConnectionsMongoDBStore(client *mongo.Client) domain.ConnectionStore {
	connections := client.Database(DATABASE).Collection(COLLECTION)
	return &ConnectionsMongoDBStore{
		connections: connections,
	}
}

func (store *ConnectionsMongoDBStore) Get(id primitive.ObjectID) (*domain.Connection, error) {
	filter := bson.M{"_id": id}
	return store.filterOne(filter)
}

func (store *ConnectionsMongoDBStore) GetAll() ([]*domain.Connection, error) {
	filter := bson.D{{}}
	return store.filter(filter)
}

func (store *ConnectionsMongoDBStore) Insert(connection *domain.Connection) error {
	result, err := store.connections.InsertOne(context.TODO(), connection)
	if err != nil {
		return err
	}
	connection.Id = result.InsertedID.(primitive.ObjectID)
	return nil
}

func (store *ConnectionsMongoDBStore) DeleteAll() {
	store.connections.DeleteMany(context.TODO(), bson.D{{}})
}

func (store *ConnectionsMongoDBStore) filter(filter interface{}) ([]*domain.Connection, error) {
	cursor, err := store.connections.Find(context.TODO(), filter)
	defer cursor.Close(context.TODO())

	if err != nil {
		return nil, err
	}
	return decode(cursor)
}

func (store *ConnectionsMongoDBStore) filterOne(filter interface{}) (connection *domain.Connection, err error) {
	result := store.connections.FindOne(context.TODO(), filter)
	err = result.Decode(&connection)
	return
}

func decode(cursor *mongo.Cursor) (connections []*domain.Connection, err error) {
	for cursor.Next(context.TODO()) {
		var connection domain.Connection
		err = cursor.Decode(&connection)
		if err != nil {
			return
		}
		connections = append(connections, &connection)
	}
	err = cursor.Err()
	return
}
