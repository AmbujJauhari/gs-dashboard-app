package com.ambuj.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Aj on 03-06-2016.
 */
public class EntriesForTypeName {
    private Set<String> fieldNames;
    private String spaceIdFieldName;
    private List<Map<String, String>> dataPerField = new ArrayList<>();

    public String getSpaceIdFieldName() {
        return spaceIdFieldName;
    }

    public void setSpaceIdFieldName(String spaceIdFieldName) {
        this.spaceIdFieldName = spaceIdFieldName;
    }

    public Set<String> getFieldNames() {
        return fieldNames;
    }

    public void setFieldNames(Set<String> fieldNames) {
        this.fieldNames = fieldNames;
    }

    public List<Map<String, String>> getDataPerField() {
        return dataPerField;
    }

    public void setDataPerField(List<Map<String, String>> dataPerField) {
        this.dataPerField = dataPerField;
    }

    @Override
    public String toString() {
        return "EntriesForTypeName{" +
                "fieldNames=" + fieldNames +
                ", spaceIdFieldName='" + spaceIdFieldName + '\'' +
                ", dataPerField=" + dataPerField +
                '}';
    }
}
