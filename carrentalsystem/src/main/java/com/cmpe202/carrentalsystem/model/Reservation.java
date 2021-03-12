package com.cmpe202.carrentalsystem.model;

import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
@Table(name = "Reservation")
public class Reservation {
	
	@Id
	private int orderId;
	
	/*
	 * One order row matches to one vehicle number
	 */
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name= "vehicle_number")
	private Vehicles vehicle;
	
	/*
	 * Many orders can belong to one user
	 */
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name= "user_id")
	@JsonIgnoreProperties({"user"})
	private UserModel user;
	
	private String bookingStartTime;
	private String bookingEndTime;
	private String returnTime;
	
	private String feedback;
	private String car_condition;
	private String status;
	private String charges;
	

	public Reservation() {}
	public Reservation( Vehicles vehicleNumber, UserModel userId, String bookingStartTime, String bookingEndTime,
			String returnTime, String feedback, String condition, String status, String charges) {
		this.vehicle = vehicleNumber;
		this.user = userId;
		this.bookingStartTime = bookingStartTime;
		this.bookingEndTime = bookingEndTime;
		this.returnTime = returnTime;
		this.feedback = feedback;
		this.car_condition = condition;
		this.status = status;
		this.charges = charges;
	}
	
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	public Vehicles getVehicleNumber() {
		return vehicle;
	}
	public void setVehicleNumber(Vehicles vehicleNumber) {
		this.vehicle = vehicleNumber;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	public UserModel getUserId() {
		return user;
	}
	public void setUserId(UserModel userId) {
		this.user = userId;
	}
	
	public String getBookingStartTime() {
		return bookingStartTime;
	}
	public void setBookingStartTime(String bookingStartTime) {
		this.bookingStartTime = bookingStartTime;
	}
	
	public String getBookingEndTime() {
		return bookingEndTime;
	}
	public void setBookingEndTime(String bookingEndTime) {
		this.bookingEndTime = bookingEndTime;
	}
	
	public String getFeedback() {
		return feedback;
	}
	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}
	
	public String getCondition() {
		return car_condition;
	}
	public void setCondition(String condition) {
		this.car_condition = condition;
	}
	
	public Vehicles getVehicle() {
		return vehicle;
	}
	public void setVehicle(Vehicles vehicle) {
		this.vehicle = vehicle;
	}
	
	public UserModel getUser() {
		return user;
	}
	public void setUser(UserModel user) {
		this.user = user;
	}
	
	public String getCar_condition() {
		return car_condition;
	}
	public void setCar_condition(String car_condition) {
		this.car_condition = car_condition;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getReturnTime() {
		return returnTime;
	}
	public void setReturnTime(String returnTime) {
		this.returnTime = returnTime;
	}
	
	public String getCharges() {
		return charges;
	}
	public void setCharges(String charges) {
		this.charges = charges;
	}
	



}
