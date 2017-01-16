package org.grails.springsecurityrest.client;

import java.util.List;

public class JwtImpl implements Jwt {
    private String username;
    private List<String> roles;
    private String token_type;
    private String access_token;
    private int expires_in;
    private String refresh_token;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public void setTokenType(String token_type) {
        this.token_type = token_type;
    }

    public void setAccessToken(String access_token) {
        this.access_token = access_token;
    }

    public void setExpiresIn(int expires_in) {
        this.expires_in = expires_in;
    }

    public void setRefreshToken(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public List<String> getRoles() {
        return this.roles;
    }

    @Override
    public String getTokenType() {
        return this.token_type;
    }

    @Override
    public String getAccessToken() {
        return this.access_token;
    }

    @Override
    public int getExpiresIn() {
        return this.expires_in;
    }

    @Override
    public String getRefreshToken() {
        return this.refresh_token;
    }
}
