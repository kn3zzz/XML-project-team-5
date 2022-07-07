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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;


@EnableMongoRepositories("com.dislinkt.authenticationservice.repository")
@GrpcService
public class AuthenticationService extends AuthenticationServiceGrpc.AuthenticationServiceImplBase {
    @Autowired
    private UserRepository userRepository;

    @Override
    public void registerUser(UserRegister request, StreamObserver<UserRegisterResponse> responseObserver) {
        UserRegisterResponse res;
        Date birthDate;
        Gender gender = Gender.MALE;
        if (request.getGender().equalsIgnoreCase("FEMALE"))
            gender = Gender.FEMALE;
        try {
            birthDate = new SimpleDateFormat("yyyy-MM-dd").parse(request.getBirthDate());
        } catch (Exception e){
            birthDate = new Date();
        }
        if (userRepository.getUserByUsername(request.getUsername()) == null) {
            userRepository.save(new User(
                    generateUserId(),
                    request.getName(),
                    request.getLastname(),
                    request.getUsername(),
                    request.getPassword(),
                    request.getEmail(),
                    request.getPhoneNumber(),
                    Role.USER,
                    gender,
                    birthDate,
                    request.getPrivateProfile()));
            res = UserRegisterResponse.newBuilder().setMessage("Success").setSuccess(true).build();
        } else {
            res = UserRegisterResponse.newBuilder().setMessage("Error").setSuccess(false).build();
        }
        responseObserver.onNext(res);
        responseObserver.onCompleted();
    }

    @Override
    public void login(UserLogin request, StreamObserver<UserLoginResponse> responseObserver) {
        User user = userRepository.getUserByUsername(request.getUsername());
        UserLoginResponse res = null;
        if (user != null && user.getUsername().equalsIgnoreCase(request.getUsername()) && user.getPassword().equals(request.getPassword())) {
            res = UserLoginResponse.newBuilder()
                    .setId(user.getId())
                    .setRole(user.getRole().toString())
                    .setNotificationsOn(user.isNotificationsOn())
                    .setUsername(user.getUsername()).build();
        } else {
            res = UserLoginResponse.newBuilder()
                    .setRole("NO USER").build();
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
                        .setPrivateProfile(u.isProfilePrivacy())
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
        Date birthDate;
        System.out.println(request.getBirthDate());
        try {
            birthDate = new SimpleDateFormat("yyyy-MM-dd").parse(request.getBirthDate());
        } catch (Exception e){
            birthDate = new Date();
        }
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
            u.setBirthDate(birthDate);
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
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
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
            .setBirthDate(dateFormat.format(u.getBirthDate()))
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

    @Override
    public void getMessagesUsers(ConnectedUsers request, StreamObserver<MessagesUsers> responseObserver) {
        List<MessagesUser> users = new ArrayList<>();
        for (UserID id : request.getUserIdsList()) {
            User u = userRepository.findById(id.getId()).get();
            users.add(MessagesUser.newBuilder()
                    .setId(u.getId())
                    .setName(u.getName())
                    .setLastname(u.getLastname())
                    .setUsername(u.getUsername())
                    .build());
        }
        MessagesUsers res = MessagesUsers.newBuilder().addAllUsers(users).build();
        responseObserver.onNext(res);
        responseObserver.onCompleted();
    }

    private long generateUserId() {
        for (long i = 1; i < 1000000000; i++) {
            try {
                userRepository.findById(i).get();
            } catch (NoSuchElementException e){
                return i;
            }
        }
        return 0;
    }
}
