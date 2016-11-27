package com.lampnc.businfo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BusRouteMem {

	@JsonProperty("route_id")
	private int routeId;
	@JsonProperty("route_no")
	private String routeNo;
	@JsonProperty("route_name")
	private String routeName;

	public BusRouteMem() {
	}

	public BusRouteMem(int routeId, String routeNo, String routeName) {
		this.routeId = routeId;
		this.routeNo = routeNo;
		this.routeName = routeName;
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

}
