package com.cmpe202.carrentalsystem.service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.cmpe202.carrentalsystem.dao.VehicleLocationRepository;
import com.cmpe202.carrentalsystem.model.VehicleLocation;
import com.cmpe202.carrentalsystem.model.VehicleLocationKey;

@Service
public class VehicleLocationService {
	
	@Autowired
	private VehicleLocationRepository vehicleLocationRepository;

	public Optional<VehicleLocation> findById(VehicleLocationKey id){
		System.out.println("Key is: " + id.getLocationId());
		System.out.println("Key is: " + id.getVehicleNumber());
		return vehicleLocationRepository.findById(id);
	}
	
//	public VehicleLocation findById(int id) {
//		return vehicleLocationRepository.findById(id);
//	
//	}
	
	public VehicleLocation findByIdVehicleNumber(String id) {
		return vehicleLocationRepository.findByIdVehicleNumber(id);
	}
	public VehicleLocation findByIdLocationId(int id) {
		return vehicleLocationRepository.findByIdLocationId(id);
	}
	
	public VehicleLocation findByIdLocationIdAndIdVehicleNumber(int locationId, String vehicleNumber) {
		return vehicleLocationRepository.findByIdLocationIdAndIdVehicleNumber(locationId, vehicleNumber);
	}
	
	public List<VehicleLocation> findByAddressAndVehicleType(@Param("vehicleType") String vehicleType, @Param("locationId") int locationId){
		System.out.println("Inside query");
		System.out.println("Inside query: vehicleType is " + vehicleType);
		System.out.println("Inside query: locationId is " + locationId);
		
		return vehicleLocationRepository.findByAddressAndVehicleType(vehicleType, locationId);
	}

	
	public List<VehicleLocation> findAllByIdLocationId(int id) {
		return vehicleLocationRepository.findAllByIdLocationId(id);
	}
	
	public List<VehicleLocation> findAllByIdVehicleNumber(String id){
		return vehicleLocationRepository.findAllByIdVehicleNumber(id);
	}
	
	public List<VehicleLocation> findAllByIdLocationIdAndIdVehicleNumber(int locationId, String vehicleNumber){
		return vehicleLocationRepository.findAllByIdLocationIdAndIdVehicleNumber(locationId, vehicleNumber);
	}

	public VehicleLocation save(VehicleLocation vehicleLocation) throws SQLIntegrityConstraintViolationException{
		return vehicleLocationRepository.save(vehicleLocation);
		
	}
	
	public void update(@Param("vehicle_number")String vehicleNumber, @Param("locationId") int locationId) {
		 vehicleLocationRepository.update(vehicleNumber, locationId);
	}
	
	public void deleteById(@Param("locationId") int locationId) {
		vehicleLocationRepository.deleteById(locationId);
	}

}


