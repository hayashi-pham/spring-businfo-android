package com.lampnc.businfo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BusRoute implements java.io.Serializable {

    @JsonProperty("route_id")
    private int routeId;
    @JsonProperty("route_no")
    private String routeNo;
    @JsonProperty("route_name")
    private String routeName;
    @JsonProperty("in_bound_name")
    private String inBoundName;
    @JsonProperty("in_bound_description")
    private String inBoundDescription;
    @JsonProperty("out_bound_name")
    private String outBoundName;
    @JsonProperty("out_bound_description")
    private String outBoundDescription;
    @JsonProperty("bus_type")
    private short busType;
    @JsonProperty("distance")
    private double distance;
    @JsonProperty("num_of_seats")
    private String numOfSeats;
    @JsonProperty("operation_time")
    private String operationTime;
    @JsonProperty("tickets")
    private String tickets;
    @JsonProperty("total_trip")
    private String totalTrip;
    @JsonProperty("time_of_trip")
    private String timeOfTrip;
    private String headway;
    private String color;
    private short status;
    private BusOrg[] orgs;

    public BusRoute() {
    }

    public BusRoute(int routeId, String routeNo, String routeName, String inBoundName, String inBoundDescription,
                    String outBoundName, String outBoundDescription, short busType, double distance, String numOfSeats,
                    String operationTime, String tickets, String totalTrip, String timeOfTrip, String headway, String color,
                    short status) {
        this.routeId = routeId;
        this.routeNo = routeNo;
        this.routeName = routeName;
        this.inBoundName = inBoundName;
        this.inBoundDescription = inBoundDescription;
        this.outBoundName = outBoundName;
        this.outBoundDescription = outBoundDescription;
        this.busType = busType;
        this.distance = distance;
        this.numOfSeats = numOfSeats;
        this.operationTime = operationTime;
        this.tickets = tickets;
        this.totalTrip = totalTrip;
        this.timeOfTrip = timeOfTrip;
        this.headway = headway;
        this.color = color;
        this.status = status;
    }

    public BusRoute(int routeId, String routeNo, String routeName, String inBoundName, String inBoundDescription, String outBoundName, String outBoundDescription, short busType, double distance, String numOfSeats, String operationTime, String tickets, String totalTrip, String timeOfTrip, String headway, String color, short status, BusOrg[] orgs) {
        this.routeId = routeId;
        this.routeNo = routeNo;
        this.routeName = routeName;
        this.inBoundName = inBoundName;
        this.inBoundDescription = inBoundDescription;
        this.outBoundName = outBoundName;
        this.outBoundDescription = outBoundDescription;
        this.busType = busType;
        this.distance = distance;
        this.numOfSeats = numOfSeats;
        this.operationTime = operationTime;
        this.tickets = tickets;
        this.totalTrip = totalTrip;
        this.timeOfTrip = timeOfTrip;
        this.headway = headway;
        this.color = color;
        this.status = status;
        this.orgs = orgs;
    }

    public int getRouteId() {
        return this.routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public String getRouteNo() {
        return this.routeNo;
    }

    public void setRouteNo(String routeNo) {
        this.routeNo = routeNo;
    }

    public String getRouteName() {
        return this.routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public String getInBoundName() {
        return this.inBoundName;
    }

    public void setInBoundName(String inBoundName) {
        this.inBoundName = inBoundName;
    }

    public String getInBoundDescription() {
        return this.inBoundDescription;
    }

    public void setInBoundDescription(String inBoundDescription) {
        this.inBoundDescription = inBoundDescription;
    }

    public String getOutBoundName() {
        return this.outBoundName;
    }

    public void setOutBoundName(String outBoundName) {
        this.outBoundName = outBoundName;
    }

    public String getOutBoundDescription() {
        return this.outBoundDescription;
    }

    public void setOutBoundDescription(String outBoundDescription) {
        this.outBoundDescription = outBoundDescription;
    }

    public short getBusType() {
        return this.busType;
    }

    public void setBusType(short busType) {
        this.busType = busType;
    }

    public double getDistance() {
        return this.distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getNumOfSeats() {
        return this.numOfSeats;
    }

    public void setNumOfSeats(String numOfSeats) {
        this.numOfSeats = numOfSeats;
    }

    public String getOperationTime() {
        return this.operationTime;
    }

    public void setOperationTime(String operationTime) {
        this.operationTime = operationTime;
    }

    public String getTickets() {
        return this.tickets;
    }

    public void setTickets(String tickets) {
        this.tickets = tickets;
    }

    public String getTotalTrip() {
        return this.totalTrip;
    }

    public void setTotalTrip(String totalTrip) {
        this.totalTrip = totalTrip;
    }

    public String getTimeOfTrip() {
        return this.timeOfTrip;
    }

    public void setTimeOfTrip(String timeOfTrip) {
        this.timeOfTrip = timeOfTrip;
    }

    public String getHeadway() {
        return this.headway;
    }

    public void setHeadway(String headway) {
        this.headway = headway;
    }

    public String getColor() {
        return this.color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public short getStatus() {
        return this.status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

    public BusOrg[] getOrgs() {
        return orgs;
    }

    public void setOrgs(BusOrg[] orgs) {
        this.orgs = orgs;
    }
}
