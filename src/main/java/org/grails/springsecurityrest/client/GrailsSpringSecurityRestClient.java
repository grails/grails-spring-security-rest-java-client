package org.grails.springsecurityrest.client;

import com.google.gson.Gson;
import okhttp3.*;

import java.io.IOException;
import java.util.Map;

public class GrailsSpringSecurityRestClient {
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final String HTTP_HEADER_ACCEPT = "Accept";
    private static final String HTTP_HEADER_ACCEPT_VALUE = "application/json";
    private static final String HTTP_HEADER_CONTENT_TYPE = "Content-Type";
    private static final String HTTP_HEADER_CONTENT_TYPE_JSON = "application/json";
    private static final String HTTP_HEADER_CONTENT_TYPE_FORM_URLENCODED = "application/x-www-form-urlencoded";
    private OkHttpClient client = new OkHttpClient();

    public JwtResponse authenticate(AuthenticationRequest authenticationRequest) {
        if ( authenticationRequest.getUseJsonCredentials() ) {
            String jsonString = authenticationRequest.getCredentialsJsonString();
            Request request = new Request.Builder()
                    .header(HTTP_HEADER_ACCEPT, HTTP_HEADER_ACCEPT_VALUE)
                    .header(HTTP_HEADER_CONTENT_TYPE, HTTP_HEADER_CONTENT_TYPE_JSON)
                    .url(authenticationRequest.getApiLoginUrl())
                    .post(RequestBody.create(JSON, jsonString))
                    .build();
            try {
                Response response = client.newCall(request).execute();
                return createJwtResponse(response);

            } catch (IOException e) {
                e.printStackTrace();
                return new JwtResponseError();
            }
        }
        return new JwtResponseError();
    }

    private static JwtResponse createJwtResponseOK(Response response) {
        try {
            String bodyString = response.body().string();
            JwtResponseOK jwtResponse = new JwtResponseOK();

            Gson gson = new Gson();
            JwtImpl jwt = gson.fromJson(bodyString, JwtImpl.class);
            jwtResponse.setJwt(jwt);
            return jwtResponse;

        } catch (IOException e) {
            e.printStackTrace();
            return new JwtResponseError();
        }
    }

    private static JwtResponse createJwtResponse(Response response) {
        if ( response.code() == 200) {
            return createJwtResponseOK(response);

        } else if ( response.code() == 401 ) {
            return new JwtResponseUnauthorized();

        } else if ( response.code() == 403 ) {
            return new JwtResponseUnauthorized();

        } else if ( response.code() == 404 ) {

            return new JwtResponseBadRequest();
        }

        return new JwtResponseError();
    }

    private static RequestBody createRefreshTokenRequestBody(RefreshRequest refreshRequest) {
        FormBody.Builder builder = new FormBody.Builder();

        Map<String, String> formParameters = refreshRequest.getFormParameters();
        for ( String key : formParameters.keySet() ) {
            builder.add(key, formParameters.get(key));
        }
        return builder.build();
    }

    public JwtResponse refreshToken(RefreshRequest refreshRequest) {
        RequestBody formBody = createRefreshTokenRequestBody(refreshRequest);
        Request request = new Request.Builder()
                .header(HTTP_HEADER_ACCEPT, HTTP_HEADER_ACCEPT_VALUE)
                .header(HTTP_HEADER_CONTENT_TYPE, HTTP_HEADER_CONTENT_TYPE_FORM_URLENCODED)
                .url(refreshRequest.getRefreshUrl())
                .post(formBody)
                .build();
        try {
            Response response = client.newCall(request).execute();
            return createJwtResponse(response);

        } catch (IOException e) {
            e.printStackTrace();
            return new JwtResponseError();
        }
    }
}
