package com.cmpe202.carrentalsystem.model;

import java.sql.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.transaction.annotation.EnableTransactionManagement;

@Entity
@Table(name = "Vehicles")
@EnableTransactionManagement
public class Vehicles {
	
	@Id
	@Column(name = "vehicle_number", length=45)
	private String vehicleNumber;
	
	private String make;
	private String model;
	private String year;
	private String registration_number;
	private int mileage;
	
	private Date last_serviced;
	private String car_condition;
	
	@Column(name="vehicle_type")
	private String vehicleType;
	
	
	@OneToMany(mappedBy = "vehicles", cascade = CascadeType.ALL)
	private Set<VehicleLocation> vehicleLocations;
	
	@OneToMany(mappedBy = "vehicle",  cascade = CascadeType.ALL)
	private Set<Reservation> order;
	
	public Vehicles() {}
	public Vehicles(String vehicleNumber, String make, String model, String year, String registration_number,
			int mileage, Date last_serviced, String condition, String vehicleType) {
		this.vehicleNumber = vehicleNumber;
		this.make = make;
		this.model = model;
		this.year = year;
		this.registration_number = registration_number;
		this.mileage = mileage;
		this.last_serviced = last_serviced;
		this.car_condition = condition;
		this.vehicleType = vehicleType;
	}

	public String getVehicleNumber() {
		return vehicleNumber;
	}

	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getRegistration_number() {
		return registration_number;
	}

	public void setRegistration_number(String registration_number) {
		this.registration_number = registration_number;
	}

	public int getMileage() {
		return mileage;
	}

	public void setMileage(int mileage) {
		this.mileage = mileage;
	}

	public Date getLast_serviced() {
		return last_serviced;
	}

	public void setLast_service(Date last_serviced) {
		this.last_serviced = last_serviced;
	}

	public String getCondition() {
		return car_condition;
	}

	public void setCondition(String condition) {
		this.car_condition = condition;
	}
	public String getVehicleType() {
		return vehicleType;
	}
	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}
	
	
	

}
