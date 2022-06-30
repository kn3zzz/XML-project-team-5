package com.agent.application.repository;

import com.agent.application.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepo extends JpaRepository<User, Integer> {

}
