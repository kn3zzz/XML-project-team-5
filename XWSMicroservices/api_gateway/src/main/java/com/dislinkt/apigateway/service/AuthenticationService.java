package com.dislinkt.apigateway.service;

import com.dislinkt.apigateway.dto.LoginDTO;
import com.dislinkt.apigateway.dto.NewUserDTO;
import com.dislinkt.grpc.*;
import com.google.protobuf.Descriptors;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@Service
public class AuthenticationService {
    @GrpcClient("authentication-grpc-service")
    AuthenticationServiceGrpc.AuthenticationServiceBlockingStub authStub;

    public Map<Descriptors.FieldDescriptor, Object> registerUser(@RequestBody NewUserDTO user) {
        UserRegister req = UserRegister.newBuilder()
                .setName(user.name)
                .setLastname(user.lastname)
                .setEmail(user.email)
                .setUsername(user.username)
                .setPassword(user.password)
                .setPrivateProfile(user.privateProfile)
                .setBirthDate(user.birthDate)
                .setGender(user.gender)
                .setPhoneNumber(user.phoneNumber)
                .build();
        UserRegisterResponse res = authStub.registerUser(req);
        return  res.getAllFields();
    }

    public Map<Descriptors.FieldDescriptor, Object> login(@RequestBody LoginDTO login) {
        UserLogin req = UserLogin.newBuilder()
                .setUsername(login.username)
                .setPassword(login.password)
                .build();
        UserLoginResponse res = authStub.login(req);
        return  res.getAllFields();
    }
}
