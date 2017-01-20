# Java Client - Spring Security Rest for Grails
 
Java library to interact with [Spring Security Rest for Grails](http://alvarosanchez.github.io/grails-spring-security-rest/latest/docs/) plugin.

It uses the [JSON Web Token (JWT)](http://alvarosanchez.github.io/grails-spring-security-rest/latest/docs/#_json_web_token) capabilities offered by plugin. 

## Installation

```grovoy
repositories {
    jcenter()
}

dependencies {
    compile 'org.grails:java-client-grails-spring-security-rest:0.3'
}
```

## Login

[Authentication Request](http://alvarosanchez.github.io/grails-spring-security-rest/latest/docs/#_authentication_endpoint):

```java
String serverUrl = "http://localhost:8080";
String username = "sherlock";
String password = "elementary";
def authenticationRequest = new AuthenticationRequest.Builder()
                .serverUrl(serverUrl)
                .username(username)
                .password(password)
                .build();
JwtResponse rsp = client.authenticate(authenticationRequest);
```

### Correct Credentials

If authentication is successful a _JwtResponseOK_ object is returned. This object
contains a Jwt property. 

```java
public interface Jwt {
    String getUsername();

    List<String> getRoles();

    String getTokenType();

    String getAccessToken();

    int getExpiresIn();

    String getRefreshToken();
}
```

### Incorrect Credentials

If the credentials are incorrect a _JwtResponseUnauthorized_ object is returned.

## Token Refresh

To refresh the credentials issue this request: 

```java
String refreshToken = "eyJhbGciOiJIUzI1NiJ9.eyJwcmluY2lwYWwiOiJINHNJQUFBQUFBQUFBSlZTUFVcL2NRQlI4dmh5Q0NJbVBTSW1VQXBwQUZcL21rcEx3cWgwZ2taSEVJY3cxSW9EMzdZUmJXdTJaM0RYY051aW9wS0loSWtCRDhCZjVKMHVRSFJLR2dwYWJOVzhQaGd3YXhsZjEyUEROdnhoZlhNR1EwZkV3MDQ4TDRtY2dUTG4yVGFTNFRnMUd1dWUzNnVVRWRveTBRWHdwZ2l5WndlN3dLZUFGVWVHemhWYkROOWxoTk1KblVtdTF0akd5OW8rR0Qwc2tkNDZabUtlNHJ2ZVBmYzBkSzR3T0JrdG83cThEd0treXlLRks1dEl0S3puY3lyakZlaFlseUZxaG94NDFlUjNTRDBuSW16Q0IwR0NWckM0d0RHR1c1M1ZLa3l0RllHTDgxbTFzdWFpSGFlZ0FqR1RPRzNEM2FKTFRPdXJ0M05pVnRzQXNIVU8xa0hoM0tidFpCZmNmanp5a2hhR3V1cEpscHlWVEZmSk03Y2VMdlRSM1wvT1RydnRTb0FsTW43cDc4cDUyOGIwUHUxZmpOZEJPMUZGdDRNV0M5aDlVNUdiaVpMNWhXTlR2bnY2ZEtQayt0dmF5OUkyU0UrUDcrUG1VOTN5WFhuVkpveHphd2E2SWhvOTZ2dW1jZ2JUNVAzVytqNklVOHpnZlJIU1l2eHZVUkpUT3RXdFJMOXZDMjhYRzRHOHh1TlpoaTZ0eEd6aFZwUVwvYVE3VnF6dCt2SURSVzBkWG4zXC9mZlR1SDNFc3dOQWVFemxTNmhNbGFERlAyNmlcL1hweE1qZjY4UEN4MjZQXC9QXC93R3dmS0JsRXdNQUFBPT0iLCJzdWIiOiJzaGVybG9jayIsInJvbGVzIjpbIlJPTEVfQk9TUyJdLCJpYXQiOjE0ODQ1NjA1MTB9.PB3epeHlU_dC22-X5hkidR5IF19WTcpRilKkgUjgqGM";
def refreshRequest = new RefreshRequest.Builder()
                .serverUrl(serverUrl)
                .refreshToken()
                .build();
def rsp = client.refreshToken(refreshRequest);
((JwtResponseOK) rsp).getJwt().getAccessToken();
```

## Dependencies

The library uses internally [OkHttp](http://square.github.io/okhttp/) and [Gson](https://github.com/google/gson)

## Author

Sergio del Amo - delamos@ociweb.com
 
