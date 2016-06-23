package com.ambuj.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Aj on 02-06-2016.
 */
public class Country implements Serializable {

    private static final long serialVersionUID = 1L;
    private String countryName;
    private String continent;
    private Boolean isImmigrationAllowed;
    private Map<String, String> countryContinentMap = new HashMap<>();

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

    public Boolean getImmigrationAllowed() {
        return isImmigrationAllowed;
    }

    public Map<String, String> getCountryContinentMap() {
        return countryContinentMap;
    }

    public void setCountryContinentMap(Map<String, String> countryContinentMap) {
        this.countryContinentMap = countryContinentMap;
    }
}
