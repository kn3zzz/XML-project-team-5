package com.agent.application.service.intereface;

import com.agent.application.model.UserType;

public interface UserTypeService {
    UserType findUserTypeByName(String role_user);
}
