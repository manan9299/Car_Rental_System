package com.cmpe202.carrentalsystem.dao;
import org.springframework.data.jpa.repository.JpaRepository;	

import com.cmpe202.carrentalsystem.model.UserModel;

public interface UserRepository extends JpaRepository<UserModel, Integer>{

	public UserModel findByEmail(String email);
	public UserModel save(UserModel user);
	public UserModel findById(int id);
	public UserModel getOne(int id);
}
