package com.agent.application.repository;

import com.agent.application.model.UserType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTypeRepository extends JpaRepository<UserType, Long> {
    UserType findByName(String roleUser);
}
