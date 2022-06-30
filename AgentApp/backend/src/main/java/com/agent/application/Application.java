package com.agent.application;


import com.agent.application.model.Permission;
import com.agent.application.model.UserType;
import com.agent.application.repository.UserTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.agent.application.model.User;
import com.agent.application.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;


@SpringBootApplication
public class Application implements CommandLineRunner {
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserTypeRepository userTypeRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args)  {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Set<Permission> adminPermissions = getAdminPermissions();
		Set<Permission> userPermissions = getUserPermissions();

		UserType userRole = new UserType();
		userRole.setName("ROLE_USER");
		userRole.setPermissions(userPermissions);
		userTypeRepository.save(userRole);

		UserType adminRole = new UserType();
		adminRole.setName("ROLE_ADMIN");
		adminRole.setPermissions(adminPermissions);
		userTypeRepository.save(adminRole);

		UserType companyOwnerRole = new UserType();
		companyOwnerRole.setName("ROLE_COMPANY_OWNER");
		userTypeRepository.save(companyOwnerRole);

		User admin = new User(1, "neki@gmail.com", passwordEncoder.encode("123"), adminRole, null, true, 0, Timestamp.from(Instant.now()));
		userRepository.save(admin);

		User user = new User(2, "user@gmail.com", passwordEncoder.encode("123"), userRole, null, true, 0, Timestamp.from(Instant.now()));
		userRepository.save(user);

	}

	private Set<Permission> getUserPermissions(){
		Set<Permission> userPermissions = new HashSet<>();

		Permission findAll = new Permission("findAll");
		userPermissions.add(findAll);

//		Permission downloadCertificate = new Permission("downloadCertificate");
//		userPermissions.add(downloadCertificate);
//
//		Permission issueCertificate = new Permission("issueCertificate");
//		userPermissions.add(issueCertificate);
//
//		Permission revokeCerificate = new Permission("revokeCerificate");
//		userPermissions.add(revokeCerificate);

		return userPermissions;
	}

	private Set<Permission> getAdminPermissions(){
		Set<Permission> adminPermissions = new HashSet<>();

//		Permission createSelfSigned = new Permission("createSelfSigned");
//		adminPermissions.add(createSelfSigned);
//
//		Permission downloadCertificate = new Permission("downloadCertificate");
//		adminPermissions.add(downloadCertificate);
//
//		Permission issueCertificate = new Permission("issueCertificate");
//		adminPermissions.add(issueCertificate);
//
//		Permission findAllRootsAndCA = new Permission("findAllRootsAndCA");
//		adminPermissions.add(findAllRootsAndCA);
//
//		Permission revokeCerificate = new Permission("revokeCerificate");
//		adminPermissions.add(revokeCerificate);

		Permission addAdmin = new Permission("addAdmin");
		adminPermissions.add(addAdmin);

		return adminPermissions;
	}

}
