package com.dislinkt.authenticationservice.service;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.dislinkt.authenticationservice.enums.Gender;
import com.dislinkt.authenticationservice.enums.Role;
import com.dislinkt.authenticationservice.model.User;
import com.dislinkt.authenticationservice.repository.UserRepository;
import com.dislinkt.grpc.*;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.text.SimpleDateFormat;
import java.util.Date;


@EnableMongoRepositories("com.dislinkt.authenticationservice.repository")
@GrpcService
public class AuthenticationService extends AuthenticationServiceGrpc.AuthenticationServiceImplBase {
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

    @Override
    public void searchProfile(SearchProfile request, StreamObserver<SearchProfileResponse> responseObserver) {
        SearchProfileResponse.Builder res = SearchProfileResponse.newBuilder();
        for (User u : userRepository.findAll()) {
            if ((u.getName() + " " + u.getLastname()).toLowerCase().contains(request.getSearchQuery().toLowerCase()) || u.getUsername().toLowerCase().contains(request.getSearchQuery().toLowerCase())) {
                res.addProfiles(SearchProfileEntity.newBuilder()
                        .setId(u.getId())
                        .setBiography(u.getBiography())
                        .setBirthDate(u.getBirthDate().toString())
                        .setInterests(u.getInterests())
                        .setEmail(u.getEmail())
                        .setEducation(u.getEducation())
                        .setGender(u.getGender().toString())
                        .setLastname(u.getLastname())
                        .setName(u.getName())
                        .setPhoneNumber(u.getPhoneNumber())
                        .setSkills(u.getSkills())
                        .setUsername(u.getUsername())
                        .setWorkingExperience(u.getWorkingExperience())
                        .build());
            }
        }
        System.out.println(res.getProfilesCount());
        responseObserver.onNext(res.build());
        responseObserver.onCompleted();
    }

    @Override
    public void changeUserInfo(UserInfoChange request, StreamObserver<UserInfoChangeResponse> responseObserver) {
        UserInfoChangeResponse res;
        try {
            User u = userRepository.findById(request.getId()).get();
            u.setUsername(request.getUsername());
            u.setEmail(request.getEmail());
            u.setGender(Gender.MALE);
            if (request.getGender().equalsIgnoreCase("FEMALE"))
                u.setGender(Gender.FEMALE);
            u.setBiography(request.getBiography());
            u.setEducation(request.getEducation());
            u.setInterests(request.getInterests());
            u.setLastname(request.getLastname());
            u.setName(request.getName());
            u.setPhoneNumber(request.getPhoneNumber());
            u.setSkills(request.getSkills());
            u.setWorkingExperience(request.getWorkingExperience());
            u.setNotificationsOn(request.getNotificationsOn());
            u.setProfilePrivacy(request.getPrivateProfile());
            //dodaj rodjendan
            userRepository.save(u);
            System.out.println(u);

        } catch (Exception e){
            res = UserInfoChangeResponse.newBuilder().setMessage("Failed").setSuccess(false).build();
            responseObserver.onNext(res);
            responseObserver.onCompleted();
        }
        res = UserInfoChangeResponse.newBuilder().setMessage("Success").setSuccess(true).build();
        responseObserver.onNext(res);
        responseObserver.onCompleted();
    }

    @Override
    public void getUser(UserID request, StreamObserver<UserResponse> responseObserver) {
        UserResponse res;
        try {
            User u = userRepository.findById(request.getId()).get();
            res = UserResponse.newBuilder()
            .setUsername(u.getUsername())
            .setEmail(u.getEmail())
            .setGender(u.getGender().toString())
            .setBiography(u.getBiography())
            .setEducation(u.getEducation())
            .setInterests(u.getInterests())
            .setLastname(u.getLastname())
            .setName(u.getName())
            .setPhoneNumber(u.getPhoneNumber())
            .setSkills(u.getSkills())
            .setWorkingExperience(u.getWorkingExperience())
            .setNotificationsOn(u.isNotificationsOn())
            .setPrivateProfile(u.isProfilePrivacy())
            .setBirthDate(u.getBirthDate().toString())
            .setId(request.getId())
            .build();
        } catch (Exception e){
            res = UserResponse.newBuilder().build();
            responseObserver.onNext(res);
            responseObserver.onCompleted();
        }
        responseObserver.onNext(res);
        responseObserver.onCompleted();
    }
}
