package com.lampnc.businfo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BusRoutePlan implements java.io.Serializable {

    @JsonProperty("plan_id")
    private int planId;
    private String name;
    @JsonProperty("short_name")
    private String shortName;
    private double distance;
    private boolean outbound;
    @JsonProperty("running_time")
    private int runningTime;
    private short status;

    public BusRoutePlan() {
    }

    public BusRoutePlan(int planId, String name, String shortName, double distance, boolean outbound, int runningTime, short status) {
        this.planId = planId;
        this.name = name;
        this.shortName = shortName;
        this.distance = distance;
        this.outbound = outbound;
        this.runningTime = runningTime;
        this.status = status;
    }

    public int getPlanId() {
        return this.planId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return this.shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public double getDistance() {
        return this.distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public boolean isOutbound() {
        return this.outbound;
    }

    public void setOutbound(boolean outbound) {
        this.outbound = outbound;
    }

    public int getRunningTime() {
        return this.runningTime;
    }

    public void setRunningTime(int runningTime) {
        this.runningTime = runningTime;
    }

    public short getStatus() {
        return this.status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

}
