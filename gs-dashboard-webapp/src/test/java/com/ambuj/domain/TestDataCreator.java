package com.ambuj.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestDataCreator {
    public static Person getSampleData() {
        return getSampleData(1).get(0);
    }

    public  static List<Person> getSampleData(int noOfEntries) {
        List<Person> sampleData = new ArrayList<>();
        for (int i = 0; i < noOfEntries; i++) {
            Country p1CompanyCountry1 = new Country();
            p1CompanyCountry1.setContinent("p" + i + "-company" + i + "-continent");
            p1CompanyCountry1.setCountryName("p" + i + "-company" + i + "-country");
            p1CompanyCountry1.setImmigrationAllowed(true);

            Address p1CompanyAddress1 = new Address();
            p1CompanyAddress1.setAddressName("p" + i + "-company" + i + "-home");
            p1CompanyAddress1.setCity("p" + i + "-company" + i + "-city");
            p1CompanyAddress1.setCounty(p1CompanyCountry1);
            p1CompanyAddress1.setState("p" + i + "-company" + i + "-state");
            p1CompanyAddress1.setStreet("p" + i + "-company" + i + "-street");

            Country p1CompanyCountry2 = new Country();
            p1CompanyCountry2.setContinent("p" + i + "-company2-continent");
            p1CompanyCountry2.setCountryName("p" + i + "-company2-country");
            p1CompanyCountry2.setImmigrationAllowed(true);

            Address p1CompanyAddress2 = new Address();
            p1CompanyAddress2.setAddressName("p" + i + "-company2-home");
            p1CompanyAddress2.setCity("p" + i + "-company2-city");
            p1CompanyAddress2.setCounty(p1CompanyCountry2);
            p1CompanyAddress2.setState("p" + i + "-company2-state");
            p1CompanyAddress2.setStreet("p1" + i + "company2-street");


            ArrayList<Address> p1CompanyAddresses = new ArrayList<>();
            p1CompanyAddresses.add(p1CompanyAddress1);
            p1CompanyAddresses.add(p1CompanyAddress2);

            Map<Department, Integer> departmentEmployeecountMap = new HashMap<>();
            departmentEmployeecountMap.put(Department.UBS, 10);
            departmentEmployeecountMap.put(Department.AMEX, 20);

            Company p1Company = new Company();
            p1Company.setCompanyName("p" + i + "-company");
            p1Company.setDepartment(Department.AMEX);
            p1Company.setCompanyAddresses(p1CompanyAddresses);
            p1Company.setDepartmentEmployeecountMap(departmentEmployeecountMap);


            Country p1Country = new Country();
            p1Country.setContinent("p" + i + "-continent");
            p1Country.setCountryName("p" + i + "-country");
            p1Country.setImmigrationAllowed(false);

            Address p1Address = new Address();
            p1Address.setAddressName("p" + i + "-home");
            p1Address.setCity("p" + i + "-city");
            p1Address.setCounty(p1Country);
            p1Address.setState("p" + i + "-state");
            p1Address.setStreet("p" + i + "-street");

            Person p1 = new Person();
            p1.setName("p" + i);
            p1.setAge(22);
            p1.setFathersName("p" + i + "-father");
            p1.setMothersName("p" + i + "-mother");
            p1.setAddress(p1Address);
            p1.setCompany(p1Company);

            sampleData.add(p1);
        }

        return sampleData;
    }
}
