package com.cmpe202.carrentalsystem.service;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.transaction.Transactional;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cmpe202.carrentalsystem.dao.UserRepository;
import com.cmpe202.carrentalsystem.model.UserModel;

@Service
public class UserService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Transactional
	public UserModel save(UserModel user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		UserModel newUser = userRepository.save(user);
		return newUser;
	}

	public UserModel update(JSONObject newUser, UserModel userToBeUpdated) throws Exception
	{		
		if (newUser.containsKey("name")) {
			userToBeUpdated.setName((String)newUser.get("name"));
		}
		
		if (newUser.containsKey("email")) {
			userToBeUpdated.setEmail((String)newUser.get("email"));
		}
		
		if (newUser.containsKey("address")) {
			userToBeUpdated.setAddress((String)newUser.get("address"));
		}
		
		if (newUser.containsKey("phone")) {
			userToBeUpdated.setPhone((String)newUser.get("phone"));
		}
		
		if (newUser.containsKey("license_number")) {
			userToBeUpdated.setLicense_number((String)newUser.get("license_number"));
		}
		
		System.out.println(newUser);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
		if (newUser.containsKey("dob")) {
			
			java.util.Date date = dateFormat.parse((String) newUser.get("dob"));
			Date dob = new Date(date.getTime());
			userToBeUpdated.setDob(dob);	
		}
		
		if (newUser.containsKey("license_date")) {
			java.util.Date date = dateFormat.parse((String) newUser.get("license_date"));
			Date licenseDate = new Date(date.getTime());
			userToBeUpdated.setLicense_date(licenseDate);
		}
		
		if (newUser.containsKey("membership_expiry_date")) {
			java.util.Date date = dateFormat.parse((String) newUser.get("membership_expiry_date"));
			Date membershipExpiryDate = new Date(date.getTime());
			userToBeUpdated.setMembership_expiry_date(membershipExpiryDate);
		}

		return userRepository.save(userToBeUpdated);
	}
	
	public UserModel findByEmail(String email) {
		UserModel user = userRepository.findByEmail(email);
		return user;
	}
	
	public UserModel findById(int id) {
		return userRepository.findById(id);
	}

	public UserModel verifyCredentials(String email, String password) throws Exception {
		
		UserModel user = this.findByEmail(email);
		if(bCryptPasswordEncoder.matches(password, user.getPassword()))
		{
			return user;
		}
		throw new Exception();
	}

	public List<UserModel> findAllUsers() {
		return userRepository.findAll();
	}
}
