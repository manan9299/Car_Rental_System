package com.cmpe202.carrentalsystem.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cmpe202.carrentalsystem.model.VehicleType;

public interface VehicleTypeRepository extends JpaRepository<VehicleType, Integer> {
	public VehicleType findByVehicleType(String name);
	public VehicleType findByVehicleTypeAndRangeStartAndRangeEndAndPrice(String vehicleType, int rangeStart, int rangeEnd, int price);
	
	public void deleteByVehicleTypeAndPrice(String vehicleType, int price);
	
	
	public List<VehicleType> findAllByVehicleType(String vehicleType);
  
	@Query(value= "SELECT DISTINCT vt.vehicle_type from VehicleType vt", nativeQuery = true)
	List<String> findDistinctVehicleType();
	

}
