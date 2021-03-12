package com.cmpe202.carrentalsystem.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Table(name = "CreditCards")
public class CreditCard {

	@Id
	@Column(name = "card_id")
	private int id;
	
	@Column(name = "card_number")
	private String cardNumber;
	private String name;
	
	private int cvv;
	@Column(name = "valid_through")
	private String validThrough;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@Basic(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
	@JsonIgnoreProperties({"phone", "dob", "address", "license_number", "license_date", "membership_expiry_date", "hibernateLazyInitializer"})
    private UserModel user_id; 
	
	public CreditCard() {}
	
	public CreditCard(String cardNumber, String name, int cvv, String validThrough, UserModel user_id) {
		this.cardNumber = cardNumber;
		this.name = name;
		this.cvv = cvv;
		this.validThrough = validThrough;
		this.user_id = user_id;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public String getCardNumber() {
		return cardNumber;
	}
	
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCvv() {
		return cvv;
	}

	public void setCvv(int cvv) {
		this.cvv = cvv;
	}
	
	public String getValidThrough() {
		return validThrough;
	}
	
	public void setValidThrough(String validThrough) {
		this.validThrough = validThrough;
	} 
	
	@ManyToOne(fetch = FetchType.LAZY)
	public UserModel getUser_id() {
		return user_id;
	}
	
	public void setUser_id(UserModel user_id) {
		this.user_id = user_id;
	}
}
