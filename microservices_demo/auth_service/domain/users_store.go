package domain

type UsersStore interface {
	AddNew(user *User)
	FindByUsername(username string) (User, error)
	FindAll() ([]User, error)
}
