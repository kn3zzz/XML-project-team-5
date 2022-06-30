package com.agent.application.repository;

import com.agent.application.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken,Long> {
    VerificationToken findVerificationTokenByToken(String token);
}
