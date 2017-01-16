package org.grails.springsecurityrest.client;

import java.util.List;

public interface Jwt {
    String getUsername();

    List<String> getRoles();

    String getTokenType();

    String getAccessToken();

    int getExpiresIn();

    String getRefreshToken();
}
