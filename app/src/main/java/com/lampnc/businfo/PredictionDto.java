package com.lampnc.businfo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PredictionDto {

    // EBMS mappings:
    // - r = RouteId
    // - rN = RouteName
    // - rNo = RouteNo
    // - s = StopId
    // - sN = StopName
    // - v = RouteVarId
    // - vN = RouteVarShortName

    @JsonProperty("route_id")
    private int routeId;
    @JsonProperty("route_no")
    private String routeNo;
    @JsonProperty("route_name")
    private String routeName;
    @JsonProperty("stop_id")
    private int stopId;
    @JsonProperty("stop_name")
    private String stopName;
    @JsonProperty("plan_id")
    private int planId;
    @JsonProperty("plan_short_name")
    private String planShortName;
    private List<VehicleDto> vehicles;

    public PredictionDto() {
    }

    public PredictionDto(int routeId, String routeNo, String routeName, int stopId, String stopName, int planId,
                         String planShortName) {
        this.routeId = routeId;
        this.routeNo = routeNo;
        this.routeName = routeName;
        this.stopId = stopId;
        this.stopName = stopName;
        this.planId = planId;
        this.planShortName = planShortName;
    }

    public PredictionDto(int routeId, String routeNo, String routeName, int stopId, String stopName, int planId,
                         String planShortName, List<VehicleDto> vehicles) {
        this.routeId = routeId;
        this.routeNo = routeNo;
        this.routeName = routeName;
        this.stopId = stopId;
        this.stopName = stopName;
        this.planId = planId;
        this.planShortName = planShortName;
        this.vehicles = vehicles;
    }

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public String getRouteNo() {
        return routeNo;
    }

    public void setRouteNo(String routeNo) {
        this.routeNo = routeNo;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public int getStopId() {
        return stopId;
    }

    public void setStopId(int stopId) {
        this.stopId = stopId;
    }

    public String getStopName() {
        return stopName;
    }

    public void setStopName(String stopName) {
        this.stopName = stopName;
    }

    public int getPlanId() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
    }

    public String getPlanShortName() {
        return planShortName;
    }

    public void setPlanShortName(String planShortName) {
        this.planShortName = planShortName;
    }

    // @JsonProperty("PredictionResult")
    public List<VehicleDto> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<VehicleDto> vehicles) {
        this.vehicles = vehicles;
    }

}
