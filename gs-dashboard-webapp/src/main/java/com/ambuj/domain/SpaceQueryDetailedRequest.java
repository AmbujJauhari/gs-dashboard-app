package com.ambuj.domain;

/**
 * Created by Aj on 08-06-2016.
 */
public class SpaceQueryDetailedRequest {
    private String gridName;
    private String dataType;
    private String spaceId;

    public String getGridName() {
        return gridName;
    }

    public void setGridName(String gridName) {
        this.gridName = gridName;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(String spaceId) {
        this.spaceId = spaceId;
    }
}
