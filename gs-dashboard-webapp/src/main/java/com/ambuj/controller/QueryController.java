package com.ambuj.controller;

import com.ambuj.domain.DataRequestForTypeName;
import com.ambuj.domain.DetailedDataUpdateDto;
import com.ambuj.domain.EntriesForTypeName;
import com.ambuj.domain.SpaceLookUpDetails;
import com.ambuj.service.SpaceAccessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import static org.apache.commons.beanutils.BeanUtils.describe;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("query/")
public class QueryController {
    @Autowired
    private SpaceAccessorService spaceAccessorService;

    @RequestMapping(value = "queryboard", method = GET)
    public String queryBoard() {
        return "query/queryboard";
    }

    @RequestMapping(value = "getAllDocumentTypesForSpace")
    public
    @ResponseBody
    List<String> getAllDocumentTypesForSpace(@RequestParam String gridName, @RequestParam String spaceName) throws RemoteException {
        SpaceLookUpDetails spaceLookUpDetails = new SpaceLookUpDetails.SpaceLookUpDetailsBuilder().withEnvName(gridName).withSpaceName(spaceName).build();
        return spaceAccessorService.getAllDataTypesForSpace(spaceLookUpDetails);
    }

    @RequestMapping(value = "getDataFromSpaceForType", headers = "Accept=*/*", method = POST, produces = "application/json")
    public
    @ResponseBody
    EntriesForTypeName getDataFromSpaceForType(@RequestBody DataRequestForTypeName dataRequestForTypeName) throws Exception {
        String documentName = dataRequestForTypeName.getDataType();
        String criteria = dataRequestForTypeName.getCriteria();
        String envName = dataRequestForTypeName.getGridName();
        String spaceName = dataRequestForTypeName.getSpaceName();
        Object[] allEntriesOfTypeName = spaceAccessorService.getAllObjectsFromSpaceForTypeName(envName, spaceName, documentName, criteria);
        EntriesForTypeName entriesForTypeName = new EntriesForTypeName();
        for (int i = 0; i < allEntriesOfTypeName.length; i++) {
            Map<String, String> valuesMap = describe(allEntriesOfTypeName[i]);
            if (i == 0) {
                entriesForTypeName.setFieldNames(valuesMap.keySet());
                entriesForTypeName.setSpaceIdFieldName(spaceAccessorService.getSpaceIdFieldNameForType(envName, spaceName, documentName));
            }
            entriesForTypeName.getDataPerField().add(valuesMap);
        }
        return entriesForTypeName;
    }

    @RequestMapping(value = "updateDataInSpaceForTypeForSpaceId", headers = "Accept=*/*", method = POST)
    public
    @ResponseBody
    void updateDataInSpaceForTypeForSpaceId(@RequestBody DetailedDataUpdateDto detailedDataUpdateDto) throws Exception {
        spaceAccessorService.updateDataForTypeNameWithSpaceId(detailedDataUpdateDto.getGridName(),
                detailedDataUpdateDto.getSpaceName(),
                detailedDataUpdateDto.getDataTypeName(),
                detailedDataUpdateDto.getSpaceIdName(),
                detailedDataUpdateDto.getDetailedDataEntry());
    }

    @RequestMapping(value = "getDataFromSpaceForTypeForSpaceId", method = GET)
    public
    @ResponseBody
    Map<String, Object> getDataFromSpaceForTypeForSpaceId(@RequestParam String spaceId,
                                                          @RequestParam String dataType,
                                                          @RequestParam String gridName,
                                                          @RequestParam String spaceName) throws Exception {
        return spaceAccessorService.getDetailedDataFromSpaceForTypeNameWithSpaceId(gridName, spaceName, dataType, spaceId);
    }

    public SpaceAccessorService getSpaceAccessorService() {
        return spaceAccessorService;
    }

    public void setSpaceAccessorService(SpaceAccessorService spaceAccessorService) {
        this.spaceAccessorService = spaceAccessorService;
    }
}
