package com.dislinkt.authenticationservice.service;

import com.dislinkt.authenticationservice.dto.NewUserDTO;
import com.dislinkt.authenticationservice.enums.Gender;
import com.dislinkt.authenticationservice.enums.Role;
import com.dislinkt.authenticationservice.grpc.UserRegisterResponse;
import com.dislinkt.authenticationservice.model.User;
import com.dislinkt.authenticationservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@EnableMongoRepositories("com.dislinkt.authenticationservice.repository")
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void save(User user) {
        userRepository.save(user);
    }

    public void saveAll(List<User> users) {
        userRepository.saveAll(users);
    }

    public void delete(User user) {
        userRepository.delete(user);
    }

    public void deleteById(long id) {
        userRepository.deleteById(id);
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public User findById(long id){
        return userRepository.findById(id).get();
    }

    public User getByUsername(String username){
        return userRepository.getUserByUsername(username);
    }

    public UserRegisterResponse registerUser(NewUserDTO newUserDTO){
        userRepository.save(new User(2, newUserDTO.name, newUserDTO.lastname, newUserDTO.username, newUserDTO.password, newUserDTO.email, newUserDTO.phoneNumber, Role.USER,
                Gender.MALE, new Date(), newUserDTO.privateProfile));
        return UserRegisterResponse.newBuilder()
                .setSuccess(true)
                .setMessage("Success").build();
    }
}
