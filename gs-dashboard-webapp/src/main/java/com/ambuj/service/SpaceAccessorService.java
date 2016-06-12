package com.ambuj.service;


import com.ambuj.domain.DetailedDataEntry;
import com.ambuj.domain.SpaceLookUpDetails;
import com.gigaspaces.query.IdQuery;
import com.j_spaces.core.IJSpace;
import com.j_spaces.core.admin.IRemoteJSpaceAdmin;
import com.j_spaces.core.client.SQLQuery;
import org.openspaces.core.GigaSpace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MutableMessage;
import org.springframework.integration.transformer.MapToObjectTransformer;
import org.springframework.integration.transformer.ObjectToMapTransformer;
import org.springframework.stereotype.Component;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SpaceAccessorService {
    @Autowired
    private SpaceLookUpService spaceLookUpService;

    private ObjectToMapTransformer pojoToMapTransformer = new ObjectToMapTransformer();

    public List<String> getAllDataTypesForSpace(SpaceLookUpDetails spaceLookUpDetails) throws RemoteException {
        List<String> typesInSpace = new ArrayList<>();
        GigaSpace gigaSpace = spaceLookUpService.getSpace(spaceLookUpDetails.getEnvName());
        IJSpace ijSpace = gigaSpace.getSpace();
        IRemoteJSpaceAdmin spaceAdmin = (IRemoteJSpaceAdmin) ijSpace.getAdmin();
        typesInSpace = spaceAdmin.getRuntimeInfo().m_ClassNames;
        return typesInSpace;
    }

    public Object[] getAllObjectsFromSpaceForTypeName(String envName, String typeName, String criteria) {
        GigaSpace gigaSpace = spaceLookUpService.getSpace(envName);
        SQLQuery<Object> documentSQLQuery = new SQLQuery<>(typeName, criteria);
        return gigaSpace.readMultiple(documentSQLQuery);
    }

    public Map<String, Object> getDetailedDataFromSpaceForTypeNameWithSpaceId(String envName, String typeName, String spaceId) throws Exception {
        GigaSpace gigaSpace = spaceLookUpService.getSpace(envName);
        IdQuery<Object> objectIdQuery = new IdQuery<Object>(typeName, spaceId);
        Object queriedObject = gigaSpace.readById(objectIdQuery);
        return (Map<String, Object>) pojoToMapTransformer.doTransform(new MutableMessage<Object>(queriedObject));
    }

    public void updateDataForTypeNameWithSpaceId(String envName, String dataType, String spaceIdName, DetailedDataEntry[] detailedDataEntries) throws Exception {
        GigaSpace gigaSpace = spaceLookUpService.getSpace(envName);
        Object spaceId = null;
        Map<String, Object> fieldNameDataMap = new HashMap<>();
        for (DetailedDataEntry detailedDataEntry : detailedDataEntries) {
            if (detailedDataEntry.getKey().equals(spaceIdName)) {
                spaceId = detailedDataEntry.getValue();
            }
            fieldNameDataMap.put(detailedDataEntry.getKey(), detailedDataEntry.getValue());
        }
        IdQuery<Object> objectIdQuery = new IdQuery<Object>(dataType, spaceId);
        Object pojoTobeUpdated = gigaSpace.takeById(objectIdQuery);
        MapToObjectTransformer mapToObjectTransformer = new MapToObjectTransformer(pojoTobeUpdated.getClass());
        Object updatedPojo = mapToObjectTransformer.doTransform(new MutableMessage<Object>(fieldNameDataMap));
        gigaSpace.write(updatedPojo);
    }

    public String getSpaceIdFieldNameForType(String envName, String dataType) {
        GigaSpace gigaSpace = spaceLookUpService.getSpace(envName);
        return gigaSpace.getTypeManager().getTypeDescriptor(dataType).getIdPropertyName();
    }
}
