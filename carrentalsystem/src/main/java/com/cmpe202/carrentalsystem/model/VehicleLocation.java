package com.cmpe202.carrentalsystem.model;

import javax.persistence.EmbeddedId;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import javax.persistence.Entity;

@Entity
@Table(name = "VehicleLocation")
public class VehicleLocation {
	
	@EmbeddedId
	private VehicleLocationKey id;
	
	@ManyToOne
	@MapsId("location_id")
	@JoinColumn(name = "location_id")
	private Location location;
	


	@ManyToOne
	@MapsId("vehicle_number")
	@JoinColumn(name = "vehicle_number")
	private Vehicles vehicles;
	
	public VehicleLocation() {}
	
	
	public VehicleLocation(VehicleLocationKey id, Location location, Vehicles vehicles) {
		this.id = id;
		this.location = location;
		this.vehicles = vehicles;
	}

	public VehicleLocationKey getId() {
		return id;
	}

	public void setId(VehicleLocationKey id) {
		this.id = id;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Vehicles getVehicles() {
		return vehicles;
	}

	public void setVehicles(Vehicles vehicles) {
		this.vehicles = vehicles;
	}

}
