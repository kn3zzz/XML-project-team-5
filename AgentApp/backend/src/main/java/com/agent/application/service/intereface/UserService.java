package com.agent.application.service.intereface;

import com.agent.application.dto.ChangePasswordDTO;
import com.agent.application.dto.SignUpUserDTO;
import com.agent.application.model.Company;
import com.agent.application.model.User;

import java.util.List;

public interface UserService {
    User register(SignUpUserDTO dto) throws Exception;

    User findUserById(Integer id);

    User login(String email, String password);

    User findByEmail(String subjectName);

    List<User> findAll();

    boolean checkPasswordCriteria(String password);

    boolean verifyUserAccount(String verificationToken);

    boolean verifyRecoveryAccount(String verificationToken, String newPassword) throws Exception;

    boolean checkUser(String email);

    boolean recoveryPass(String email) throws Exception;

    User registerAdmin(SignUpUserDTO dto) throws Exception;

    void changePassword(ChangePasswordDTO dto, String userEmail) throws Exception;

    Company getCompanyByOwnerUsername(String username);
}
