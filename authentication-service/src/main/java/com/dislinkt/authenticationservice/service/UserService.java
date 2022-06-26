package com.dislinkt.authenticationservice.service;

import com.dislinkt.authenticationservice.dto.NewUserDTO;
import com.dislinkt.authenticationservice.dto.UserTokenState;
import com.dislinkt.authenticationservice.enums.Gender;
import com.dislinkt.authenticationservice.enums.Role;
import com.dislinkt.authenticationservice.grpc.*;
import com.dislinkt.authenticationservice.model.User;
import com.dislinkt.authenticationservice.repository.UserRepository;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.security.auth.login.CredentialNotFoundException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@EnableMongoRepositories("com.dislinkt.authenticationservice.repository")
@GrpcService
public class UserService extends AuthenticationServiceGrpc.AuthenticationServiceImplBase {
    @Autowired
    private UserRepository userRepository;

    @Override
    public void registerUser(UserRegister request, StreamObserver<UserRegisterResponse> responseObserver) {
        Date birthDate;
        try {
            birthDate = new SimpleDateFormat("yyyy-MM-dd").parse(request.getBirthDate());
        } catch (Exception e){
            birthDate = new Date();
        }
        userRepository.save(new User(
                2,
                request.getName(),
                request.getLastname(),
                request.getUsername(),
                request.getPassword(),
                request.getEmail(),
                request.getPhoneNumber(),
                Role.USER,
                Gender.MALE,
                birthDate,
                request.getPrivateProfile()));
        UserRegisterResponse res = UserRegisterResponse.newBuilder().setMessage("Success").setSuccess(true).build();
        responseObserver.onNext(res);
        responseObserver.onError(new Exception("Something went wrong."));
        responseObserver.onCompleted();
    }

    @Override
    public void login(UserLogin request, StreamObserver<UserLoginResponse> responseObserver) {
        UserTokenState state = null;
        User user = userRepository.getUserByUsername(request.getUsername());
            if (user.getUsername().equalsIgnoreCase(request.getUsername()) && user.getPassword().equals(request.getPassword())) {
                UserLoginResponse res = UserLoginResponse.newBuilder()
                        .setId(user.getId())
                        .setRole(user.getRole().toString())
                        .setNotificationsOn(user.isNotificationsOn())
                        .setUsername(user.getUsername()).build();
                responseObserver.onNext(res);
            } else {
                responseObserver.onError(new CredentialNotFoundException("Your credentials are wrong. Please, try again"));
            }
            responseObserver.onCompleted();
    }
}
