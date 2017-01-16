package org.grails.springsecurityrest.client;

import java.util.List;

public class JwtImpl implements Jwt {
    private String username;
    private List<String> roles;
    private String token_type;
    private String access_token;
    private int expires_in;
    private String refresh_token;

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
