package com.dislinkt.apigateway.service;

import com.dislinkt.apigateway.dto.NewUserDTO;
import com.dislinkt.apigateway.grpc.*;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.client.inject.GrpcClient;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class AuthenticationService implements IAuthenticationService {

    @GrpcClient("authenticationgrpcservice")
    private AuthenticationServiceGrpc.AuthenticationServiceBlockingStub stub;

    @Override
    public UserRegisterResponse register(NewUserDTO newUserDTO) {
        UserRegister newUser = UserRegister.newBuilder()
                .setName(newUserDTO.name)
                .setLastname(newUserDTO.lastname)
                .setEmail(newUserDTO.email)
                .setPhoneNumber(newUserDTO.phoneNumber)
                .setGender(newUserDTO.gender)
                .setBirthDate(newUserDTO.birthDate)
                .setUsername(newUserDTO.username)
                .setPassword(newUserDTO.password)
                .setPrivateProfile(newUserDTO.privateProfile)
                .build();
        return this.stub.registerUser(newUser);
    }

    @Override
    public UserLoginResponse login(String username, String password) {
        return null;
    }
}
