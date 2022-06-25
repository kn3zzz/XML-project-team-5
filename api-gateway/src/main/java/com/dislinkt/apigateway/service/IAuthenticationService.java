package com.dislinkt.apigateway.service;

import com.dislinkt.apigateway.dto.NewUserDTO;
import com.dislinkt.apigateway.grpc.*;

public interface IAuthenticationService {

    UserRegisterResponse registerUser (NewUserDTO newUserDTO);

    UserLoginResponse login (String username, String password);
}
