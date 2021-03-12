package com.cmpe202.carrentalsystem.controllers;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cmpe202.carrentalsystem.model.VehicleType;
import com.cmpe202.carrentalsystem.service.VehicleTypeService;

@RestController
public class VehicleTypeController {
	
	@Autowired
	private VehicleTypeService vehicleTypeService;

	@GetMapping("/admin/vehicleType/all")
	public ResponseEntity<List<String>> getAllVehicleTypes() {
		System.out.println("Inside getAllVehicleTypes");

		List<String> typeList = vehicleTypeService.getAllTypes();

		return ResponseEntity.status(HttpStatus.OK).body(typeList);

	}
	
	// Read Operation: Returns information from the database
//	@GetMapping("/vehicleType")
//	public void getVehicle(@RequestBody VehicleType vt) {
//		System.out.println("Inside getVehicle");
//		List<VehicleType> vehicleType = vehicleTypeService.findAllByVehicleType(vt.getVehicle_type().toString());
//		if(!vehicleType.isEmpty()) {
//			System.out.println("Found car");
//		}
//		else {
//			System.out.println("Vehicle not found: ");
//		}
//		
//	}
	
	// Create Operation: Stores Vehicle Type into database
	@PostMapping("/admin/vehicleType/add")
	public void addVehicle(@RequestBody VehicleType vt) {
		System.out.println("Inside post vehicle type:" + vt);
		try {
			vehicleTypeService.save(vt);
		} catch (SQLIntegrityConstraintViolationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// Update Operation: Update Vehicle information in database
	@PutMapping("/vehicleType")
	public void updateVehicle(@RequestBody VehicleType vt) {
		try {
			VehicleType vehicle = vehicleTypeService.findByVehicleTypeAndRangeStartAndRangeEndAndPrice(vt.getVehicle_type(), vt.getRange_start(), vt.getRange_end(), vt.getPrice());
			if(vehicle != null) {
			
				vehicle.setPrice(vt.getPrice());
				vehicle.setRange_start(vt.getRange_start());
				vehicle.setRange_end(vt.getRange_end());
				vehicle.setVehicle_type(vt.getVehicle_type());
				
				try {
					vehicleTypeService.save(vehicle);
				} catch (SQLIntegrityConstraintViolationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else {
				System.out.println("Record was not found for updating. Creating new record instead");
				try {
					vehicleTypeService.save(vehicle);
				} catch (SQLIntegrityConstraintViolationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		catch (DataIntegrityViolationException  ex){
				System.out.println(ex.getMessage());
				System.out.println("Database Constraint was violated");
		}
	}
	
	@DeleteMapping("/vehicleType/{id}")
	public void deleteVehicle(@PathVariable int id) {
		System.out.println("Id is: " + id);
		try {
			vehicleTypeService.delete(id);
			System.out.println("Vehicle has been deleted");
		}
		catch(IllegalArgumentException ex) {
			System.out.println("Error deleting vehicle: " + ex.getMessage());
			System.out.println("Vehicle id not valid");
		}
	}
}
