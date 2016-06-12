package com.ambuj.domain;

import java.util.Arrays;

/**
 * Created by Aj on 09-06-2016.
 */
public class DetailedDataUpdateDto {
    private String gridName;
    private String dataTypeName;
    private String spaceIdName;
    private DetailedDataEntry[] detailedDataEntry;

    public String getGridName() {
        return gridName;
    }

    public void setGridName(String gridName) {
        this.gridName = gridName;
    }

    public String getDataTypeName() {
        return dataTypeName;
    }

    public void setDataTypeName(String dataTypeName) {
        this.dataTypeName = dataTypeName;
    }

    public DetailedDataEntry[] getDetailedDataEntry() {
        return detailedDataEntry;
    }

    public void setDetailedDataEntry(DetailedDataEntry[] detailedDataEntry) {
        this.detailedDataEntry = detailedDataEntry;
    }

    public String getSpaceIdName() {
        return spaceIdName;
    }

    public void setSpaceIdName(String spaceIdName) {
        this.spaceIdName = spaceIdName;
    }

    @Override
    public String toString() {
        return "DetailedDataUpdateDto{" +
                "dataTypeName='" + dataTypeName + '\'' +
                ", spaceIdName='" + spaceIdName + '\'' +
                ", detailedDataEntry=" + Arrays.toString(detailedDataEntry) +
                '}';
    }
}
