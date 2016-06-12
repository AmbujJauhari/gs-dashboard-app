package com.ambuj.domain;

import com.gigaspaces.annotation.pojo.SpaceClass;
import com.gigaspaces.annotation.pojo.SpaceId;
import com.gigaspaces.annotation.pojo.SpaceRouting;

import java.io.Serializable;

/**
 * Created by Aj on 02-06-2016.
 */
@SpaceClass
    public class Person implements Serializable {
        private static final long serialVersionUID = 1L;
        private String name;
        private Integer age;
        private Address address;
        private Company company;
        private String mothersName;
        private String fathersName;

        @SpaceRouting
        @SpaceId
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public Address getAddress() {
            return address;
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Company getCompany() {
            return company;
        }

        public void setCompany(Company company) {
            this.company = company;
        }
    //
        public String getMothersName() {
            return mothersName;
        }

        public void setMothersName(String mothersName) {
            this.mothersName = mothersName;
        }

        public String getFathersName() {
            return fathersName;
        }

        public void setFathersName(String fathersName) {
            this.fathersName = fathersName;
        }

        public String getNickName()  {
            return "No nick name";
        }

        public void setNickName() {


        }

    }
