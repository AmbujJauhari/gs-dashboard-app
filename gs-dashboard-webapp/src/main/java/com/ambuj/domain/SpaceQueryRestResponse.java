package com.ambuj.domain;


import com.gigaspaces.document.SpaceDocument;

import java.util.List;

public class SpaceQueryRestResponse {
    private List<SpaceDocument> spaceDocuments;

    public List<SpaceDocument> getSpaceDocuments() {
        return spaceDocuments;
    }

    public void setSpaceDocuments(List<SpaceDocument> spaceDocuments) {
        this.spaceDocuments = spaceDocuments;
    }
}
