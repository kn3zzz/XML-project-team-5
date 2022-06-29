package com.dislinkt.authenticationservice.service;

import com.dislinkt.authenticationservice.enums.Gender;
import com.dislinkt.authenticationservice.enums.Role;
import com.dislinkt.authenticationservice.model.User;
import com.dislinkt.authenticationservice.repository.UserRepository;
import com.dislinkt.grpc.*;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import javax.security.auth.login.CredentialNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;

@EnableMongoRepositories("com.dislinkt.authenticationservice.repository")
@GrpcService
public class
AuthenticationService extends AuthenticationServiceGrpc.AuthenticationServiceImplBase {
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
        responseObserver.onCompleted();
    }

    @Override
    public void login(UserLogin request, StreamObserver<UserLoginResponse> responseObserver) {
        User user = userRepository.getUserByUsername(request.getUsername());
        UserLoginResponse res = null;
        if (user.getUsername().equalsIgnoreCase(request.getUsername()) && user.getPassword().equals(request.getPassword())) {
            res = UserLoginResponse.newBuilder()
                    .setId(user.getId())
                    .setRole(user.getRole().toString())
                    .setNotificationsOn(user.isNotificationsOn())
                    .setUsername(user.getUsername()).build();
        }
        responseObserver.onNext(res);
        responseObserver.onCompleted();
    }
}
