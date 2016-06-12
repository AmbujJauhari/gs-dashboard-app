package com.ambuj.domain;

import com.typesafe.config.Config;

/**
 * Created by Aj on 24-05-2016.
 */
public class SpaceLookUpDetails {
    private String envName;
    private String url;
    private String userName;
    private String password;
    private boolean secured;

    public String getEnvName() {
        return envName;
    }

    public String getUrl() {
        return url;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public boolean isSecured() {
        return secured;
    }

    public static final class SpaceLookUpDetailsBuilder {
        private String envName;
        private String url;
        private String userName;
        private String password;
        private boolean secured;

        public SpaceLookUpDetailsBuilder withEnvName(String envName) {
            this.envName = envName;
            return this;
        }

        public SpaceLookUpDetailsBuilder withUrl(String url) {
            this.url = url;
            return this;
        }

        public SpaceLookUpDetailsBuilder withUserName(String userName) {
            this.userName = userName;
            return this;
        }

        public SpaceLookUpDetailsBuilder withPassword(String password) {
            this.password = password;
            return this;
        }

        public SpaceLookUpDetailsBuilder withSecured(boolean secured) {
            this.secured = secured;
            return this;
        }

        public SpaceLookUpDetails buildWithConfig(Config config) {
            SpaceLookUpDetails spaceLookUpDetails = new SpaceLookUpDetails();
            spaceLookUpDetails.envName = config.getString(ConfigurationProperties.ENV_NAME);
            spaceLookUpDetails.url = config.getString(ConfigurationProperties.ENV_SPACE_URL);
            spaceLookUpDetails.userName = config.getString(ConfigurationProperties.ENV_USER_NAME);
            spaceLookUpDetails.password = config.getString(ConfigurationProperties.ENV_USER_PASSWORD);
            spaceLookUpDetails.secured = config.getBoolean(ConfigurationProperties.ENV_IS_SECURED);

            return spaceLookUpDetails;
        }

        public SpaceLookUpDetails build() {
            SpaceLookUpDetails spaceLookUpDetails = new SpaceLookUpDetails();
            spaceLookUpDetails.envName = this.envName;
            spaceLookUpDetails.url = this.url;
            spaceLookUpDetails.userName = this.userName;
            spaceLookUpDetails.password = this.password;
            spaceLookUpDetails.secured = this.secured;

            return spaceLookUpDetails;
        }
    }

    @Override
    public String
    toString() {
        return "SpaceLookUpDetails{" +
                "envName='" + envName + '\'' +
                ", url='" + url + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", secured=" + secured +
                '}';
    }
}