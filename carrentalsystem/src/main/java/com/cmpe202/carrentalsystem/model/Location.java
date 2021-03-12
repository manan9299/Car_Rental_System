package com.cmpe202.carrentalsystem.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Location")
public class Location {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JoinColumn(name = "location_id")
	private int location_id;
	
	private String address;
	private int location_capacity;
	private int cur_cap_occupied;
	
	@OneToMany(mappedBy = "location", cascade = CascadeType.ALL)
	private Set<VehicleLocation> vehicleLocations;
	
	public int getLocation_id() {
		return location_id;
	}
	public void setLocation_id(int location_id) {
		this.location_id = location_id;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getLocation_capacity() {
		return location_capacity;
	}
	public void setLocation_capacity(int location_capacity) {
		this.location_capacity = location_capacity;
	}
	public int getCur_cap_occupied() {
		return cur_cap_occupied;
	}
	public void setCur_cap_occupied(int cur_cap_occupied) {
		this.cur_cap_occupied = cur_cap_occupied;
	}

	

}
