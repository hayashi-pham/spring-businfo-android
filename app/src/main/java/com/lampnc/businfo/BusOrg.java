package com.lampnc.businfo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BusOrg implements java.io.Serializable {

    @JsonProperty("name")
    private String orgName;
    @JsonProperty("phone")
    private String orgPhone;

    public BusOrg() {
    }

    public BusOrg(String orgName, String orgPhone) {
        this.orgName = orgName;
        this.orgPhone = orgPhone;
    }

    public String getOrgName() {
        return this.orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgPhone() {
        return this.orgPhone;
    }

    public void setOrgPhone(String orgPhone) {
        this.orgPhone = orgPhone;
    }

}
