package com.ambuj.domain;

import com.eclipsesource.json.Json;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.wnameless.json.flattener.JsonFlattener;
import com.google.common.collect.Iterables;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Test;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by Aj on 21-06-2016.
 */
public class MapTester {

    @Test
    public void testMapData() {
        Person p = TestDataCreator.getSampleData();

        ObjectMapper objectMapper = new ObjectMapper();
        Map m = objectMapper.convertValue(p, Map.class);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println(gson.toJson(p));

        Person p1 = gson.fromJson("{\n" +
                "  \"name\": \"p0\",\n" +
                "  \"age\": 22,\n" +
                "  \"address\": {\n" +
                "    \"addressName\": \"p0-home\",\n" +
                "    \"street\": \"p0-street\",\n" +
                "    \"city\": \"p0-city\",\n" +
                "    \"state\": \"p0-state\",\n" +
                "    \"county\": {\n" +
                "      \"countryName\": \"p0-country\",\n" +
                "      \"continent\": \"p0-continent\",\n" +
                "      \"isImmigrationAllowed\": false,\n" +
                "      \"countryContinentMap\": {}\n" +
                "    }\n" +
                "  },\n" +
                "  \"company\": {\n" +
                "    \"companyName\": \"p0-company\",\n" +
                "    \"department\": \"AMEX\",\n" +
                "    \"companyAddresses\": [\n" +
                "      {\n" +
                "        \"addressName\": \"p0-company0-home\",\n" +
                "        \"street\": \"p0-company0-street\",\n" +
                "        \"city\": \"p0-company0-city\",\n" +
                "        \"state\": \"p0-company0-state\",\n" +
                "        \"county\": {\n" +
                "          \"countryName\": \"p0-company0-country\",\n" +
                "          \"continent\": \"p0-company0-continent\",\n" +
                "          \"isImmigrationAllowed\": true,\n" +
                "          \"countryContinentMap\": {}\n" +
                "        }\n" +
                "      },\n" +
                "      {\n" +
                "        \"addressName\": \"p0-company2-home\",\n" +
                "        \"street\": \"p10company2-street\",\n" +
                "        \"city\": \"p0-company2-city\",\n" +
                "        \"state\": \"p0-company2-state\",\n" +
                "        \"county\": {\n" +
                "          \"countryName\": \"p0-company2-country\",\n" +
                "          \"continent\": \"p0-company2-continent\",\n" +
                "          \"isImmigrationAllowed\": true,\n" +
                "          \"countryContinentMap\": {}\n" +
                "        }\n" +
                "      }\n" +
                "    ],\n" +
                "    \"departmentEmployeecountMap\": {\n" +
                "      \"UBS\": 10,\n" +
                "      \"AMEX\": 200000\n" +
                "    }\n" +
                "  },\n" +
                "  \"mothersName\": \"p0-mother\",\n" +
                "  \"fathersName\": \"p0-father\"\n" +
                "}", Person.class);

        System.out.println(p1);

        //gson.toJson()
        //System.out.println(JsonFlattener.flattenAsMap(gson.toJson(p)));

        System.out.println(p1);
    }
}


