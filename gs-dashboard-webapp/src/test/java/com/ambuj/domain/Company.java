package com.ambuj.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by Aj on 02-06-2016.
 */
public class Company implements Serializable {
    private static final long serialVersionUID = 1L;
    private String companyName;
    private Department department;
    private List<Address> companyAddresses;
    private Map<Department, Integer>
            departmentEmployeecountMap;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public List<Address> getCompanyAddresses() {
        return companyAddresses;
    }

    public void setCompanyAddresses(List<Address> companyAddresses) {
        this.companyAddresses = companyAddresses;
    }

    public Map<Department, Integer> getDepartmentEmployeecountMap() {
        return departmentEmployeecountMap;
    }

    public void setDepartmentEmployeecountMap(Map<Department, Integer> departmentEmployeecountMap) {
        this.departmentEmployeecountMap = departmentEmployeecountMap;
    }
}
