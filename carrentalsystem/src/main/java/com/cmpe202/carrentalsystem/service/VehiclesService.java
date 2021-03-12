package com.cmpe202.carrentalsystem.service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.cmpe202.carrentalsystem.dao.VehiclesRepository;
import com.cmpe202.carrentalsystem.model.Vehicles;

@Service
public class VehiclesService {

	@Autowired
	private VehiclesRepository vehiclesRepository;
	
	public Vehicles save(Vehicles v) throws SQLIntegrityConstraintViolationException {
		return vehiclesRepository.save(v);
			
	}
	
	public List<Vehicles> findAllByVehicleType(String vehicleType){
		return vehiclesRepository.findAllByVehicleType(vehicleType);
	}
	
	public List<Vehicles> findAllVehicles(){
		return vehiclesRepository.findAll();
	}
	
	public List<Vehicles> findAvailability(@Param("vehicleType") String vehicleType, @Param("locationId") int locationId){
		return vehiclesRepository.findAvailability(vehicleType, locationId);
	}

	public List<String> recommendSimilar(@Param("vehicleType") String vehicleType, @Param("locationId") int locationId){
		return vehiclesRepository.recommendSimilar(vehicleType,locationId);
	}
	
	public void deleteByVehicleNumber(@Param("vehicleNumber") String vehicleNumber) {
		vehiclesRepository.deleteByVehicleNumber(vehicleNumber);
		
	}
	
	public void deleteByMileage(int number) {
		vehiclesRepository.deleteByMileage(number);
	}
	
	public List<Object[]> getVehicles()
	{
		return vehiclesRepository.getVehicles();
	}
	
	
	public Vehicles findByVehicleNumber(String vehicleNumber) {
		return vehiclesRepository.findByVehicleNumber(vehicleNumber);
	}
}
