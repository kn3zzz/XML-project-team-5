package com.agent.application.service.impl;

import com.agent.application.model.VerificationToken;
import com.agent.application.repository.VerificationTokenRepository;
import com.agent.application.service.intereface.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerificationTokenServiceImpl implements VerificationTokenService {
    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Override
    public VerificationToken saveVerificationToken(VerificationToken token) {
        return verificationTokenRepository.save(token);
    }

    @Override
    public VerificationToken findVerificationTokenByToken(String token) {
        return verificationTokenRepository.findVerificationTokenByToken(token);
    }

}

