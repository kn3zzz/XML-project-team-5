package com.agent.application;


import com.agent.application.model.Company;
import com.agent.application.model.Permission;
import com.agent.application.model.User;
import com.agent.application.model.UserType;
import com.agent.application.repository.CompanyRepository;
import com.agent.application.repository.UserRepository;
import com.agent.application.repository.UserTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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

	@Autowired
	private CompanyRepository companyRepository;

	public static void main(String[] args)  {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Set<Permission> adminPermissions = getAdminPermissions();
		Set<Permission> userPermissions = getUserPermissions();
		Set<Permission> companyOwnerPermissions = getCompanyOwnerPermissions();

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
		companyOwnerRole.setPermissions(companyOwnerPermissions);
		userTypeRepository.save(companyOwnerRole);

		Company newCo = new Company(1L, "Knjaz", "Voda", "+381623456", null, null, null, null, "o@gmail.com", true, false);
		companyRepository.save(newCo);

		User admin = new User(1, "neki@gmail.com", passwordEncoder.encode("123"), adminRole, null, true, 0, Timestamp.from(Instant.now()));
		userRepository.save(admin);

		User user = new User(2, "user@gmail.com", passwordEncoder.encode("123"), userRole, null, true, 0, Timestamp.from(Instant.now()));
		userRepository.save(user);

		User owner = new User(3, "o@gmail.com", passwordEncoder.encode("123"), companyOwnerRole, newCo, true, 0, Timestamp.from(Instant.now()));
		userRepository.save(owner);
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

	private Set<Permission> getCompanyOwnerPermissions(){
		Set<Permission> companyOwnerPermissions = new HashSet<>();

		Permission findAll = new Permission("findAll");
		companyOwnerPermissions.add(findAll);

//		Permission downloadCertificate = new Permission("downloadCertificate");
//		userPermissions.add(downloadCertificate);
//
//		Permission issueCertificate = new Permission("issueCertificate");
//		userPermissions.add(issueCertificate);
//
//		Permission revokeCerificate = new Permission("revokeCerificate");
//		userPermissions.add(revokeCerificate);

		return companyOwnerPermissions;
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
