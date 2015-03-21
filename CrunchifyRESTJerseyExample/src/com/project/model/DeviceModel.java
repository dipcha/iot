package com.project.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class DeviceModel {
	
	private float temperature;
	private int devID;
	private String devicetype;
	private String date;
	
	
	public DeviceModel() {
		super();
	}
	
	public DeviceModel(int id, float temperature, String date) {
		super();
		this.devID = id;
		this.temperature = temperature;
		this.date = date;
	}

	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getDevicetype() {
		return devicetype;
	}
	public void setDevicetype(String devicetype) {
		this.devicetype = devicetype;
	}

	public String toString() {
		return "temperature=" + temperature + ", devID=" + devID + ", devicetype=" + devicetype + ", date=" + date +" ";
	}
	public float getTemperature() {
		return temperature;
	}
	public void setTemperature(float temperature) {
		this.temperature = temperature;
	}
	public int getDevID() {
		return devID;
	}
	public void setDevID(int devID) {
		this.devID = devID;
	}
}
	
	
