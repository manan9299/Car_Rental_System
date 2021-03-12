package com.cmpe202.carrentalsystem.service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.cmpe202.carrentalsystem.dao.ReservationRepository;
import com.cmpe202.carrentalsystem.model.Reservation;
import com.cmpe202.carrentalsystem.model.UserModel;

@Service
public class ReservationService {

	@Autowired
	private ReservationRepository reservationRepository;
	
	public List<Reservation> findByUser(UserModel user) {
		return reservationRepository.findByUser(user);
	}
	
	public Reservation findByOrderId(int id) {
		return reservationRepository.findByOrderId(id);
	}

	public List<Reservation> findAllByVehicle(@Param("vehicleNumber") String vehicleNumber) {
		return reservationRepository.findAllByVehicle(vehicleNumber);
		
	}
	
	public List<Reservation> findAvailability(@Param("vehicleType") String vehicleType){
		return reservationRepository.findAvailability(vehicleType);
	}

	public Reservation findByFeedback(String feedback) {
		return reservationRepository.findByFeedback(feedback);
	}
	
	public Reservation save(Reservation order) throws SQLIntegrityConstraintViolationException{
		return reservationRepository.save(order);
	}
}
