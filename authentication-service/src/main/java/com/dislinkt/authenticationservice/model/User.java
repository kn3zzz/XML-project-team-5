package com.dislinkt.authenticationservice.model;

import com.dislinkt.authenticationservice.enums.Gender;
import com.dislinkt.authenticationservice.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;

@Document("users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
    @Id
    private long id;
    private String name;
    private String lastname;
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private Role role;
    private Gender gender;
    private Date birthDate;
    private String biography;
    private String education;
    private String workingExperience;
    private String skills;
    private String interests;
    private boolean profilePrivacy;
    private boolean notificationsOn;
    private String photo;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public String getUsername() {
        return username;
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
        return true;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getWorkingExperience() {
        return workingExperience;
    }

    public void setWorkingExperience(String workingExperience) {
        this.workingExperience = workingExperience;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getInterests() {
        return interests;
    }

    public void setInterests(String interests) {
        this.interests = interests;
    }

    public boolean isProfilePrivacy() {
        return profilePrivacy;
    }

    public void setProfilePrivacy(boolean profilePrivacy) {
        this.profilePrivacy = profilePrivacy;
    }

    public boolean isNotificationsOn() {
        return notificationsOn;
    }

    public void setNotificationsOn(boolean notificationsOn) {
        this.notificationsOn = notificationsOn;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User(long id, String name, String lastname, String username, String password, String email, String phoneNumber, Role role, Gender gender, Date birthDate, boolean profilePrivacy) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.gender = gender;
        this.birthDate = birthDate;
        this.profilePrivacy = profilePrivacy;
        this.biography = "";
        this.education = "";
        this.workingExperience = "";
        this.photo = "";
        this.skills = "";
        this.notificationsOn = true;
        this.interests = "";
    }
}
