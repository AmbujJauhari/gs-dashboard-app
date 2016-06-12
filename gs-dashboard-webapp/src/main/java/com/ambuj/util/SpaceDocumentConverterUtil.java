package com.ambuj.util;

import com.gigaspaces.document.SpaceDocument;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpaceDocumentConverterUtil {
    public static Map<String, Object> convertSpaceDocumentToMap(SpaceDocument spaceDocument, Map<String, Object> keyValuePair) {
        Map<String, Object> properties = spaceDocument.getProperties();
        for (String propertyName : properties.keySet()) {
            if (properties.get(propertyName) instanceof SpaceDocument) {
                SpaceDocument nestedSpaceDocument = (SpaceDocument) properties.get(propertyName);
                keyValuePair.putAll(convertSpaceDocumentToMap(nestedSpaceDocument, keyValuePair));
            } else {
                keyValuePair.put(propertyName, properties.get(propertyName));
            }
        }

        return keyValuePair;
    }

    public static List<Map<String, Object>> convertSpaceDocumentToMap(SpaceDocument... spaceDocuments) {
        List<Map<String, Object>> keyValuePairs = new ArrayList<>();
        for (SpaceDocument spaceDocument : spaceDocuments) {
            Map<String, Object> keyValuePair = new HashMap<>();
            keyValuePairs.add(convertSpaceDocumentToMap(spaceDocument, keyValuePair));
        }

        return keyValuePairs;
    }
}
