# Java Client - Spring Security Rest for Grails
 
Java library to interact with [Spring Security Rest for Grails](http://alvarosanchez.github.io/grails-spring-security-rest/latest/docs/) plugin.

It uses the [JSON Web Token (JWT)](http://alvarosanchez.github.io/grails-spring-security-rest/latest/docs/#_json_web_token) capabilities offered by plugin. 

## Login

[Authentication Request](http://alvarosanchez.github.io/grails-spring-security-rest/latest/docs/#_authentication_endpoint):
````
def authenticationRequest = new AuthenticationRequest.Builder()
                .serverUrl(serverUrl)
                .username(username)
                .password(password)
                .build();
JwtResponse rsp = client.authenticate(authenticationRequest);
````

### Correct Credentials

If authentication is successful a _JwtResponseOK_ object is returned. This object
contains a Jwt property. 

````
public interface Jwt {
    String getUsername();

    List<String> getRoles();

    String getTokenType();

    String getAccessToken();

    int getExpiresIn();

    String getRefreshToken();
}
````

### Incorrect Credentials

If the credentials are incorrect a _JwtResponseUnauthorized_ object is returned.

## Token Refresh

To refresh the credentials issue this request: 

````
def refreshRequest = new RefreshRequest.Builder()
                .serverUrl(serverUrl)
                .refreshToken(jwt.refreshToken)
                .build();
def rsp = client.refreshToken(refreshRequest);
((JwtResponseOK) rsp).getJwt().getAccessToken();

````

## Dependencies

The library uses internally [OkHttp](http://square.github.io/okhttp/) and [Gson](https://github.com/google/gson)

## Author

Sergio del Amo - delamos@ociweb.com
 
