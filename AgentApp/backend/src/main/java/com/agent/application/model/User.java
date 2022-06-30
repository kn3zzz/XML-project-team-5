package com.agent.application.model;

import java.sql.Timestamp;
import java.util.Collection;

import javax.persistence.*;
import javax.validation.constraints.Email;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
public class User implements UserDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(unique = false)
	@Email
	private String email;
	
	private String password;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
	@JoinColumn(name = "role_id")
	private UserType userType;

	@OneToOne(/*cascade = CascadeType.ALL*/)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "company_id", referencedColumnName = "id")
	private Company company;

	private Boolean isActive;

	private Integer attemptLogin;

	@Column(name="last_password_reset_date", nullable = false)
	private Timestamp lastPasswordResetDate;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.userType.getPermissions();
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return isActive;
	}
}
