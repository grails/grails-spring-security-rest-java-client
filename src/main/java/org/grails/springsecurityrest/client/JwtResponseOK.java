package org.grails.springsecurityrest.client;

public class JwtResponseOK implements JwtResponse {
    JwtImpl jwt;

    public void setJwt(JwtImpl jwt) {
        this.jwt = jwt;
    }

    public JwtImpl getJwt() {
        return this.jwt;
    }
}
