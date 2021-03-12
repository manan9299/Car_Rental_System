package com.cmpe202.carrentalsystem.service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmpe202.carrentalsystem.dao.VehicleTypeRepository;
import com.cmpe202.carrentalsystem.model.VehicleType;

@Service
public class VehicleTypeService {

	@Autowired
	private VehicleTypeRepository vehicleTypeRepository;
	
	public VehicleType save(VehicleType vt) throws SQLIntegrityConstraintViolationException {
		return vehicleTypeRepository.save(vt);
			
	}

	public Optional<VehicleType> findById(int vehicle_type_id) {
		
		return vehicleTypeRepository.findById(vehicle_type_id);
		
	}
	
	public List<String> getAllTypes(){
		return vehicleTypeRepository.findDistinctVehicleType();
	}
	
	public void delete(int vehicle_type) {
		vehicleTypeRepository.deleteById(vehicle_type);
	}

	public VehicleType findByVehicleType(String name) {
		return vehicleTypeRepository.findByVehicleType(name);
	}
	public VehicleType findByVehicleTypeAndRangeStartAndRangeEndAndPrice(String vehicleType, int rangeStart, int rangeEnd, int price) {
		return vehicleTypeRepository.findByVehicleTypeAndRangeStartAndRangeEndAndPrice(vehicleType, rangeStart, rangeEnd, price);
	}
	
	public List<VehicleType> findAllByVehicleType(String vehicleType){
		return vehicleTypeRepository.findAllByVehicleType(vehicleType);
	}

	public void deleteByVehicleTypeAndPrice(String vehicleType, int price) {
		vehicleTypeRepository.deleteByVehicleTypeAndPrice(vehicleType, price);
	}

}
