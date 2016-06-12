package com.ambuj.domain;

import java.io.Serializable;

/**
 * Created by Aj on 02-06-2016.
 */
public class Address implements Serializable{
    private static final long serialVersionUID = 1L;
    private String addressName;
    private String street;
    private String city;
    private String state;
    private Country county;

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Country getCounty() {
        return county;
    }

    public void setCounty(Country county) {
        this.county = county;
    }
}
