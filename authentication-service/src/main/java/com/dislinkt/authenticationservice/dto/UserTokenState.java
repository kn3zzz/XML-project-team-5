package com.dislinkt.authenticationservice.dto;

import com.dislinkt.authenticationservice.enums.Role;

public class UserTokenState {
    private String accessToken;
    private Long expiresIn;
    private Role role;
    private boolean notificationsOn;
    private String username;
    private long id;

    public UserTokenState() {
        this.accessToken = null;
        this.expiresIn = null;
    }

    public UserTokenState(String accessToken, long expiresIn, Role role, boolean notificationsOn, boolean on, String username, long id) {
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
        this.role = role;
        this.notificationsOn = notificationsOn;
        this.username = username;
        this.id = id;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean getNotificationsOn() {
        return notificationsOn;
    }

    public void setNotificationsOn(boolean notificationsOn) {
        this.notificationsOn = notificationsOn;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String email) {
        this.username = username;
    }
}