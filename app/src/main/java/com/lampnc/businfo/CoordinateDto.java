package com.lampnc.businfo;

public class CoordinateDto {

	private double[] lat;
	private double[] lng;

	public CoordinateDto() {
	}

	public CoordinateDto(double[] lat, double[] lng) {
		this.lat = lat;
		this.lng = lng;
	}

	public double[] getLat() {
		return lat;
	}

	public void setLat(double[] lat) {
		this.lat = lat;
	}

	public double[] getLng() {
		return lng;
	}

	public void setLng(double[] lng) {
		this.lng = lng;
	}
}
