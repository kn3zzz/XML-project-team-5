package com.dislinkt.apigateway.service;

import com.dislinkt.apigateway.dto.NewUserDTO;
import com.dislinkt.apigateway.grpc.*;
import com.google.protobuf.Descriptors;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.client.inject.GrpcClient;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@Component
public class AuthenticationService extends AuthenticationServiceGrpc.AuthenticationServiceImplBase{

    @GrpcClient("authentication-grpc-service")
    private AuthenticationServiceGrpc.AuthenticationServiceBlockingStub authStub;

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

    @Override
    public void registerUser(UserRegister request, StreamObserver<UserRegisterResponse> responseObserver) {
        System.out.println("HELLO");
    }

    @Override
    public void login(UserLogin request, StreamObserver<UserLoginResponse> responseObserver) {
        System.out.println("HELLO");
    }
}
