package com.ambuj.domain;

import java.io.Serializable;

/**
 * Created by Aj on 02-06-2016.
 */
public class Country implements Serializable {

    private static final long serialVersionUID = 1L;
    private String countryName;
    private String continent;
    private Boolean isImmigrationAllowed;

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public boolean isImmigrationAllowed() {
        return isImmigrationAllowed;
    }

    public void setImmigrationAllowed(boolean immigrationAllowed) {
        isImmigrationAllowed = immigrationAllowed;
    }
}
