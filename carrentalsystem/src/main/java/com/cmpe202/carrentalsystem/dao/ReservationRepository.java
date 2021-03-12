package com.cmpe202.carrentalsystem.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.cmpe202.carrentalsystem.model.Reservation;
import com.cmpe202.carrentalsystem.model.UserModel;

public interface ReservationRepository extends JpaRepository<Reservation, Integer>{

	public Reservation findByOrderId(int id);
	
	@Modifying
	@Transactional 
	@Query(value = "Select * from Reservation where vehicle_number=:vehicleNumber and status=Active", nativeQuery = true)
	public List<Reservation> findAllByVehicle(@Param("vehicleNumber")String vehicleNumber);
	

	@Query(value = "Select * FROM Vehicles, Reservation \r\n" + 
			"where vehicle_type = :vehicleType AND Vehicles.vehicle_number = Reservation.vehicle_number \r\n" + 
			"AND status = \"Active\"", nativeQuery = true)
	public List<Reservation> findAvailability(@Param("vehicleType") String vehicleType);
	
	public Reservation findByFeedback(String feedback);
	public List<Reservation> findByUser(UserModel user);
	public Reservation save(Reservation order);
	
}
