package com.dislinkt.apigateway.service;

import com.dislinkt.apigateway.dto.NewUserDTO;
import com.dislinkt.apigateway.grpc.*;

public interface IAuthenticationService {

    UserRegisterResponse register (NewUserDTO newUserDTO);

    UserLoginResponse login (String username, String password);
}
