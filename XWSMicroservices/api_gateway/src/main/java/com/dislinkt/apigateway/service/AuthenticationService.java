package com.dislinkt.apigateway.service;

import com.dislinkt.apigateway.dto.*;
import com.dislinkt.grpc.*;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AuthenticationService {
    @GrpcClient("authentication-grpc-service")
    AuthenticationServiceGrpc.AuthenticationServiceBlockingStub authStub;
    @GrpcClient("connection-grpc-service")
    ConnectionServiceGrpc.ConnectionServiceBlockingStub connectionStub;

    public Boolean registerUser(NewUserDTO user) {
        System.out.println(user);
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
        if (res.getSuccess())
            return true;
        return  false;
    }

    public LoginResponseDTO login(LoginDTO login) {
        UserLogin req = UserLogin.newBuilder()
                .setUsername(login.username)
                .setPassword(login.password)
                .build();
        UserLoginResponse res = authStub.login(req);
        return convertToLoginDTO(res);
    }

    public List<SearchProfileResponseDTO> searchProfile(String searchQuery) {
        List<SearchProfileResponseDTO> retList = new ArrayList<>();
        SearchProfile req = SearchProfile.newBuilder()
                .setSearchQuery(searchQuery)
                .build();
        SearchProfileResponse res = authStub.searchProfile(req);
        return convertToSearchProfileResponseDTO(res);
    }

    public Boolean updateUser(UserInfoChangeDTO info) {
        UserInfoChange infoChange = UserInfoChange.newBuilder()
                .setName(info.name)
                .setLastname(info.lastname)
                .setBiography(info.biography)
                .setBirthDate(info.birthDate)
                .setEmail(info.email)
                .setGender(info.gender)
                .setUsername(info.username)
                .setEducation(info.education)
                .setId(info.id)
                .setPhoneNumber(info.phoneNumber)
                .setWorkingExperience(info.workingExperience)
                .setNotificationsOn(info.notificationsOn)
                .setPrivateProfile(info.privateProfile)
                .setInterests(info.interests)
                .setSkills(info.skills)
                .build();
        UserInfoChangeResponse res = authStub.changeUserInfo(infoChange);
        if (res.getSuccess())
            return true;
        return  false;
    }

    public LoginResponseDTO convertToLoginDTO(UserLoginResponse res) {
        return new LoginResponseDTO(res.getId(), res.getUsername(), res.getNotificationsOn(), res.getRole());
    }

    public List<SearchProfileResponseDTO> convertToSearchProfileResponseDTO(SearchProfileResponse responses) {
        List<SearchProfileResponseDTO> retList = new ArrayList<SearchProfileResponseDTO>();
        for (int i = 0; i < responses.getProfilesCount(); i++) {
            SearchProfileEntity res = responses.getProfiles(i);
            retList.add(new SearchProfileResponseDTO(res.getUsername(), res.getName(), res.getLastname(), res.getBiography(), res.getInterests(), res.getId(), res.getPrivateProfile()));
        }
        return retList;
    }

    public UserInfoChangeDTO getUser(long id) {
        UserID userId = UserID.newBuilder()
                .setId(id)
                .build();
        UserInfoChangeDTO res = convertToUserInfoChangeDTO(authStub.getUser(userId));
        return res;
    }

    private UserInfoChangeDTO convertToUserInfoChangeDTO(UserResponse res) {
        return new UserInfoChangeDTO(
                res.getName(),
                res.getLastname(),
                res.getUsername(),
                res.getEmail(),
                res.getPhoneNumber(),
                res.getGender(),
                res.getBirthDate(),
                res.getBiography(),
                res.getWorkingExperience(),
                res.getEducation(),
                res.getSkills(),
                res.getInterests(),
                res.getPrivateProfile(),
                res.getNotificationsOn(),
                res.getId());
    }

    public List<MessagesUserDTO> getMessagesUsers(long id) {
        List<UserID> ids = new ArrayList<>();
        GetConnectionsResponse resCon = connectionStub.getConnections(GetConnections.newBuilder().setUserId(id).build());
        for (ConnectionEntity ce : resCon.getConnectionsList()){
            if (ce.getState().equalsIgnoreCase("CONNECTED"))
                if (ce.getReceiver() == id)
                    ids.add(UserID.newBuilder().setId(ce.getSender()).build());
                else if (ce.getSender() == id)
                    ids.add(UserID.newBuilder().setId(ce.getReceiver()).build());
        }
        MessagesUsers res = authStub.getMessagesUsers(ConnectedUsers.newBuilder().addAllUserIds(ids).build());
        return convertToMessagesUsersDTO(res);
    }

    private List<MessagesUserDTO> convertToMessagesUsersDTO(MessagesUsers res) {
        List<MessagesUserDTO> users = new ArrayList<>();
        for (MessagesUser mu : res.getUsersList())
            users.add(new MessagesUserDTO(mu.getId(), mu.getName(), mu.getLastname(), mu.getUsername()));
        return users;
    }
}
