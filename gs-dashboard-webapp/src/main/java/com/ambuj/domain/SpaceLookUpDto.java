package com.ambuj.domain;

import java.util.List;
import java.util.Set;

/**
 * Created by Aj on 18-06-2016.
 */
public class SpaceLookUpDto {
    private Set<SpaceLookUpDetails> spaceLookUpDetailsList;
    private String exceptions;

    public Set<SpaceLookUpDetails> getSpaceLookUpDetailsList() {
        return spaceLookUpDetailsList;
    }

    public void setSpaceLookUpDetailsList(Set<SpaceLookUpDetails> spaceLookUpDetailsList) {
        this.spaceLookUpDetailsList = spaceLookUpDetailsList;
    }

    public String getExceptions() {
        return exceptions;
    }

    public void setExceptions(String exceptions) {
        this.exceptions = exceptions;
    }
}
