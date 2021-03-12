package com.cmpe202.carrentalsystem.model;

import javax.persistence.Column;
import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "VehicleType")
public class VehicleType {
	
	@Id
	@Column(name = "vehicle_type")
	@JoinColumn(name = "vehicle_type")
	private String vehicleType;
	
	@Column(name = "range_start")
	private int rangeStart;
	
	@Column(name = "range_end")
	private int rangeEnd;
	
	private int price;
	
	public String getVehicle_type() {
		return vehicleType;
	}
	public void setVehicle_type(String vehicleType) {
		this.vehicleType = vehicleType;
	}
	
	public int getRange_start() {
		return rangeStart;
	}
	public void setRange_start(int range_start) {
		this.rangeStart = range_start;
	}
	
	public int getRange_end() {
		return rangeEnd;
	}
	public void setRange_end(int range_end) {
		this.rangeEnd = range_end;
	}
	
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
}
