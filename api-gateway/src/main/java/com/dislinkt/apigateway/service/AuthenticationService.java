package com.dislinkt.apigateway.service;

import com.dislinkt.apigateway.dto.NewUserDTO;
import com.dislinkt.apigateway.grpc.*;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.client.inject.GrpcClient;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationService extends AuthenticationServiceGrpc.AuthenticationServiceImplBase implements IAuthenticationService{

    @GrpcClient("authenticationgrpcservice")
    private AuthenticationServiceGrpc.AuthenticationServiceBlockingStub stub;

    @Override
    public UserRegisterResponse registerUser(NewUserDTO newUserDTO) {
        System.out.println("USAO");
        System.out.println(newUserDTO.birthDate);
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
        System.out.println("PROSAO SAM PRAVLJENJE");
        return this.stub.registerUser(newUser);
    }

    @Override
    public UserLoginResponse login(String username, String password) {
        return null;
    }

    @Override
    public void registerUser(UserRegister request, StreamObserver<UserRegisterResponse> responseObserver) {
        super.registerUser(request, responseObserver);
    }

    @Override
    public void login(UserLogin request, StreamObserver<UserLoginResponse> responseObserver) {
        super.login(request, responseObserver);
    }
}
