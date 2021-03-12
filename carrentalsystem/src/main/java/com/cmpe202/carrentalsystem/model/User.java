package com.cmpe202.carrentalsystem.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "User")
public class User {
	
	@Id
	private int user_id;
	private String name;
	private String email;
	
	@JsonIgnore
	private String password;
	private String phone;
	private Date dob;
	private String address;
	private String license_number;
	private Date license_date;
	private Date membership_expiry_date;
	private String role;
	
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public int getId() {
		return user_id;
	}
	
	public void setId(int user_id) {
		this.user_id = user_id;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	@JsonIgnore
	public String getPassword() {
		return password;
	}
	
	@JsonProperty
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public Date getDob() {
		return dob;
	}
	
	public void setDob(Date dob) {
		this.dob = dob;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getLicense_number() {
		return license_number;
	}
	
	public void setLicense_number(String license_number) {
		this.license_number = license_number;
	}
	
	public Date getLicense_date() {
		return license_date;
	}
	
	public void setLicense_date(Date license_date) {
		this.license_date = license_date;
	}
	
	public Date getMembership_expiry_date() {
		return membership_expiry_date;
	}
	
	public void setMembership_expiry_date(Date membership_expiry_date) {
		this.membership_expiry_date = membership_expiry_date;
	}	
}
