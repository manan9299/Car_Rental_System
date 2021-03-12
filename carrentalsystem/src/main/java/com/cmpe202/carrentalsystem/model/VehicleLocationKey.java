package com.cmpe202.carrentalsystem.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class VehicleLocationKey implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Column(name = "location_id")
	private int locationId;
	
	@Column(name = "vehicle_number")
	private String vehicleNumber;
	
	public VehicleLocationKey() {}
	public VehicleLocationKey(int locationId, String vehicleNumber) {
		super();
		this.locationId = locationId;
		this.vehicleNumber = vehicleNumber;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + locationId;
		result = prime * result + ((vehicleNumber == null) ? 0 : vehicleNumber.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VehicleLocationKey other = (VehicleLocationKey) obj;
		if (locationId != other.locationId)
			return false;
		if (vehicleNumber == null) {
			if (other.vehicleNumber != null)
				return false;
		} else if (!vehicleNumber.equals(other.vehicleNumber))
			return false;
		return true;
	}

	public int getLocationId() {
		return locationId;
	}

	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}

	public String getVehicleNumber() {
		return vehicleNumber;
	}

	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}

}
