package org.grails.springsecurityrest.client;

import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

public class AuthenticationRequest {
    private final String path;
    private final String usernamePropertyName;
    private final String passwordPropertyName;
    private final String serverUrl;
    private final String username;
    private final String password;
    private final boolean useJsonCredentials;

    private AuthenticationRequest(Builder builder) {
        this.path = builder.path;
        this.usernamePropertyName = builder.usernamePropertyName;
        this.passwordPropertyName = builder.passwordPropertyName;
        this.serverUrl = builder.serverUrl;
        this.username = builder.username;
        this.password = builder.password;
        this.useJsonCredentials = builder.useJsonCredentials;
    }

    public Map<String, String> credentials() {
        Map<String, String> m = new HashMap<>();
        m.put(usernamePropertyName, username);
        m.put(passwordPropertyName, password);
        return m;
    }

    public boolean getUseJsonCredentials() {
        return this.useJsonCredentials;
    }

    public String getCredentialsJsonString() {
        JsonObject jsonObject = new JsonObject();
        for ( String key : credentials().keySet()) {
            jsonObject.addProperty(key, credentials().get(key));
        }
        return jsonObject.toString();
    }

    public String getApiLoginUrl() {
        StringBuilder sb = new StringBuilder();
        if ( this.serverUrl != null ) {
            sb.append(this.serverUrl);
        }
        if (!(
                (this.serverUrl != null && this.serverUrl.endsWith("/")) ||
                (this.path != null)  && this.path.startsWith("/")
            )) {
            sb.append("/");
        }

        if ( this.path != null ) {
            sb.append(this.path);
        }
        return sb.toString();
    }

    public static class Builder {
        private static final String DEFAULT_PATH = "/api/login";
        private static final String DEFAULT_USERNAME_PROPERTY_NAME = "username";
        private static final String DEFAULT_PASSWORD_PROPERTY_NAME = "password";
        private static final boolean DEFAULT_USER_JSON_CREDENTIALS = true;

        private String path = DEFAULT_PATH;
        private String usernamePropertyName = DEFAULT_USERNAME_PROPERTY_NAME;
        private String passwordPropertyName = DEFAULT_PASSWORD_PROPERTY_NAME;
        private String serverUrl;
        private String username;
        private String password;
        private boolean useJsonCredentials = DEFAULT_USER_JSON_CREDENTIALS;

        public Builder() {

        }

        public Builder useJsonCredentials(boolean useJsonCredentials) {
            this.useJsonCredentials = useJsonCredentials;
            return this;
        }

        public Builder serverUrl(String str) {
            this.serverUrl = str;
            return this;
        }

        public Builder username(String str) {
            this.username = str;
            return this;
        }

        public Builder password(String str) {
            this.password = str;
            return this;
        }

        public Builder path(String str) {
            this.path = str;
            return this;
        }

        public Builder usernamePropertyName(String str) {
            usernamePropertyName = str;
            return this;
        }

        public Builder passwordPropertyName(String str) {
            passwordPropertyName = str;
            return this;
        }

        public AuthenticationRequest build() {
            return new AuthenticationRequest(this);
        }

    }
}
