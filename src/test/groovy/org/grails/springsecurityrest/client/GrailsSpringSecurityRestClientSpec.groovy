package org.grails.springsecurityrest.client

import spock.lang.IgnoreIf
import spock.lang.Specification


class GrailsSpringSecurityRestClientSpec extends Specification {

    @IgnoreIf({ !System.getProperty('username') || !System.getProperty('password') || !System.getProperty('server') })
    def "test login"() {

        when:
        String serverUrl = System.getProperty('server')
        String username = System.getProperty('username')
        String password = System.getProperty('password')
        def client = new GrailsSpringSecurityRestClient()

        def authenticationRequest = new AuthenticationRequest.Builder()
                .serverUrl(serverUrl)
                .username(username)
                .password(password)
                .build()

        JwtResponse rsp = client.authenticate(authenticationRequest)

        then:
        rsp instanceof JwtResponseOK

        when:
        JwtImpl jwt = (rsp as JwtResponseOK).jwt

        then:
        jwt
        jwt.expiresIn > 0
        jwt.username
        jwt.accessToken
        jwt.refreshToken
        jwt.roles
        jwt.roles.size() > 0

        when:
        rsp = client.authenticate(new AuthenticationRequest.Builder()
                .serverUrl(serverUrl)
                .username(username)
                .password('bogus')
                .build())

        then:
        rsp instanceof JwtResponseUnauthorized

        when:
        def refreshRequest = new RefreshRequest.Builder()
                .serverUrl(serverUrl)
                .refreshToken(jwt.refreshToken)
                .build()

        def refreshedResponse = client.refreshToken(refreshRequest)

        then:
        refreshedResponse instanceof JwtResponseOK

        when:
        def newJwt = (refreshedResponse as JwtResponseOK).jwt

        then:
        newJwt
        newJwt.expiresIn > 0
        newJwt.username
        newJwt.accessToken
        newJwt.refreshToken
        newJwt.roles
        newJwt.roles.size() > 0
    }
}
