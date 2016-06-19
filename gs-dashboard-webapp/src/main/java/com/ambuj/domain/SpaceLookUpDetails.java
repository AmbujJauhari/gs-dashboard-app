package com.ambuj.domain;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigList;
import com.typesafe.config.ConfigObject;
import com.typesafe.config.ConfigValue;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aj on 24-05-2016.
 */
public class SpaceLookUpDetails {
    private String envName;
    private String spaceName;
    private String spaceUrl;
    private String userName;
    private String password;
    private boolean secured;

    public String getSpaceName() {
        return spaceName;
    }

    public String getEnvName() {
        return envName;
    }

    public String getSpaceUrl() {
        return spaceUrl;
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

    public void setEnvName(String envName) {
        this.envName = envName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SpaceLookUpDetails that = (SpaceLookUpDetails) o;

        if (secured != that.secured) return false;
        if (envName != null ? !envName.equals(that.envName) : that.envName != null) return false;
        if (spaceName != null ? !spaceName.equals(that.spaceName) : that.spaceName != null) return false;
        if (spaceUrl != null ? !spaceUrl.equals(that.spaceUrl) : that.spaceUrl != null) return false;
        if (userName != null ? !userName.equals(that.userName) : that.userName != null) return false;
        return password != null ? password.equals(that.password) : that.password == null;

    }

    @Override
    public int hashCode() {
        int result = envName != null ? envName.hashCode() : 0;
        result = 31 * result + (spaceName != null ? spaceName.hashCode() : 0);
        result = 31 * result + (spaceUrl != null ? spaceUrl.hashCode() : 0);
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (secured ? 1 : 0);
        return result;
    }

    public static final class SpaceLookUpDetailsBuilder {
        private String envName;
        private String url;
        private String userName;
        private String password;
        private boolean secured;
        private String spaceName;

        public SpaceLookUpDetailsBuilder withEnvName(String envName) {
            this.envName = envName;
            return this;
        }

        public SpaceLookUpDetailsBuilder withSpaceName(String spaceName) {
            this.spaceName = spaceName;
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

        public List<SpaceLookUpDetails> buildWithConfig(Config config) {
            List<SpaceLookUpDetails> lookUpDetails = new ArrayList<>();

            ConfigList spaces = config.getList(ConfigurationProperties.GRID_SPACES);

            for (ConfigValue spaceDetails : spaces) {
                SpaceLookUpDetails spaceLookUpDetails = new SpaceLookUpDetails();
                spaceLookUpDetails.envName = config.getString(ConfigurationProperties.GRID_NAME);
                Config spaceConfig = ((ConfigObject) spaceDetails).toConfig();
                spaceLookUpDetails.spaceName = spaceConfig.getString(ConfigurationProperties.GRID_SPACE_NAME);
                spaceLookUpDetails.spaceUrl = spaceConfig.getString(ConfigurationProperties.GRID_SPACE_URL);
                spaceLookUpDetails.userName = config.getString(ConfigurationProperties.GRID_USER_NAME);
                spaceLookUpDetails.password = config.getString(ConfigurationProperties.GRID_USER_PASSWORD);
                spaceLookUpDetails.secured = spaceConfig.getBoolean(ConfigurationProperties.GRID_SPACE_IS_SECURED);

                lookUpDetails.add(spaceLookUpDetails);

            }

            return lookUpDetails;
        }

        public SpaceLookUpDetails build() {
            SpaceLookUpDetails spaceLookUpDetails = new SpaceLookUpDetails();
            spaceLookUpDetails.envName = this.envName;
            spaceLookUpDetails.spaceUrl = this.url;
            spaceLookUpDetails.userName = this.userName;
            spaceLookUpDetails.password = this.password;
            spaceLookUpDetails.secured = this.secured;
            spaceLookUpDetails.spaceName = this.spaceName;

            return spaceLookUpDetails;
        }
    }

    @Override
    public String
    toString() {
        return "SpaceLookUpDetails{" +
                "envName='" + envName + '\'' +
                ", spaceUrl='" + spaceUrl + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", secured=" + secured +
                '}';
    }
}