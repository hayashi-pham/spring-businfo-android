package com.lampnc.businfo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BusStopDto {

    @JsonProperty("stop_id")
    private int stopId;
    @JsonProperty("stop_type")
    private short stopType;
    private String code;
    private String name;
    @JsonProperty("short_name")
    private String shortName;
    @JsonProperty("address_no")
    private String addressNo;
    private String street;
    private String ward;
    private String zone;
    private double latitude;
    private double longitude;
    @JsonProperty("disability_support")
    private boolean disabilitySupport;
    private short status;

    public BusStopDto() {
    }

    public BusStopDto(int stopId, short stopType, String code, String name, String shortName, String addressNo,
                      String street, String ward, String zone, double latitude, double longitude, boolean disabilitySupport,
                      short status) {
        this.stopId = stopId;
        this.stopType = stopType;
        this.code = code;
        this.name = name;
        this.shortName = shortName;
        this.addressNo = addressNo;
        this.street = street;
        this.ward = ward;
        this.zone = zone;
        this.latitude = latitude;
        this.longitude = longitude;
        this.disabilitySupport = disabilitySupport;
        this.status = status;
    }

    public int getStopId() {
        return stopId;
    }

    public void setStopId(int stopId) {
        this.stopId = stopId;
    }

    public short getStopType() {
        return stopType;
    }

    public void setStopType(short stopType) {
        this.stopType = stopType;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getAddressNo() {
        return addressNo;
    }

    public void setAddressNo(String addressNo) {
        this.addressNo = addressNo;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public boolean isDisabilitySupport() {
        return disabilitySupport;
    }

    public void setDisabilitySupport(boolean disabilitySupport) {
        this.disabilitySupport = disabilitySupport;
    }

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

}
