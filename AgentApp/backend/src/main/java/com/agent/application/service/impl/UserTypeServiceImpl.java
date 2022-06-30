package com.agent.application.service.impl;

import com.agent.application.model.UserType;
import com.agent.application.repository.UserTypeRepository;
import com.agent.application.service.intereface.UserTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserTypeServiceImpl implements UserTypeService {
    @Autowired
    private UserTypeRepository userTypeRepository;

    @Override
    public UserType findUserTypeByName(String roleUser) {
        return userTypeRepository.findByName(roleUser);
    }
}
