package org.grails.springsecurityrest.client;

import java.util.HashMap;
import java.util.Map;

public class RefreshRequest {

    final private String path;
    final private String serverUrl;
    final private String refreshToken;
    final private String grantType;
    final private String refreshTokenPropertyName;
    final private String grantTypePropertyName;

    private RefreshRequest(Builder builder) {
        this.path = builder.path;
        this.serverUrl = builder.serverUrl;
        this.refreshToken = builder.refreshToken;
        this.grantType = builder.grantType;
        this.refreshTokenPropertyName = builder.refreshTokenPropertyName;
        this.grantTypePropertyName = builder.grantTypePropertyName;
    }

    public Map<String, String> getFormParameters() {
        Map<String, String> m = new HashMap<>();
        m.put(grantTypePropertyName, grantType);
        m.put(refreshTokenPropertyName, this.refreshToken);
        return m;
    }

    public String getRefreshUrl() {
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
        private String path = "/oauth/access_token";
        private String serverUrl;
        private String refreshToken;
        private String grantType = "refresh_token";
        private String refreshTokenPropertyName = "refresh_token";
        private String grantTypePropertyName = "grant_type";

        public Builder() {}

        public void path(String str) {
            this.path = str;
        }

        public Builder serverUrl(String str) {
            this.serverUrl = str;
            return this;
        }

        public Builder refreshToken(String str) {
            this.refreshToken = str;
            return this;
        }

        public Builder grantType(String str) {
            this.grantType = str;
            return this;
        }

        public Builder refreshTokenPropertyName(String str) {
            refreshTokenPropertyName = str;
            return this;
        }

        public Builder grantTypePropertyName(String str) {
            grantTypePropertyName = str;
            return this;
        }

        public RefreshRequest build() {
            return new RefreshRequest(this);
        }
    }
}
