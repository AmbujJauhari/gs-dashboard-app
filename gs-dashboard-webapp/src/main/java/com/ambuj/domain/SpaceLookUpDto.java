package com.ambuj.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Aj on 18-06-2016.
 */
public class SpaceLookUpDto {
    private Set<String> gridNames = new HashSet<>(0);
    private String exceptions;

    public Set<String> getGridNames() {
        return gridNames;
    }

    public void setGridNames(Set<String> gridNames) {
        this.gridNames = gridNames;
    }

    public String getExceptions() {
        return exceptions;
    }

    public void setExceptions(String exceptions) {
        this.exceptions = exceptions;
    }
}
