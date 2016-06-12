package com.ambuj.domain;

/**
 * Created by Aj on 09-06-2016.
 */
public class DetailedDataEntry {
    private String key;
    private Object value;
    private boolean disabled;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    @Override
    public String toString() {
        return "DetailedDataUpdateDto{" +
                "key='" + key + '\'' +
                ", value=" + value +
                ", disabled=" + disabled +
                '}';
    }
}
