package com.cmpe202.carrentalsystem.controllers;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cmpe202.carrentalsystem.model.Location;
import com.cmpe202.carrentalsystem.model.VehicleLocation;
import com.cmpe202.carrentalsystem.model.VehicleLocationKey;
import com.cmpe202.carrentalsystem.model.Vehicles;
import com.cmpe202.carrentalsystem.service.LocationService;
import com.cmpe202.carrentalsystem.service.VehicleLocationService;
import com.cmpe202.carrentalsystem.service.VehiclesService;

@RestController
public class VehicleLocationController {

	@Autowired
	private VehicleLocationService vehicleLocationService;
	
	@Autowired
	private LocationService locationService;
	
	@Autowired
	private VehiclesService vehiclesService;
	
	
	/*
	 * Finds all vehicles by address
	 */
	@GetMapping("/vehicleLocation/{address}")
	public List<VehicleLocation> getByAddress(@PathVariable String address) {
		System.out.println("What is parameter: " + address);
		Location loc = locationService.findByAddress(address);
		List<VehicleLocation> vehicleLocation = vehicleLocationService.findAllByIdLocationId(loc.getLocation_id());
		
		return vehicleLocation;
		
	}
	
	/*
	 * Finds by location and vehicle type
	 */
	@GetMapping("/vehicleLocation/{address}/{vehicleType}")
	public List<VehicleLocation> getVehicle(@PathVariable String address, @PathVariable String vehicleType ) {
		System.out.println("What is parameter: " + address);
		Location loc = locationService.findByAddress(address);
		System.out.println("What is vehicle type:" + vehicleType);
		System.out.println("What is location:" + loc.getLocation_id());
		List<VehicleLocation> vehiceLocation =  vehicleLocationService.findByAddressAndVehicleType(vehicleType, loc.getLocation_id());
		
		return vehiceLocation;
	}
	
	@PostMapping("/admin/vehicleLocation")
	public ResponseEntity<Object> addVehicleLocation(@RequestBody JSONObject v) {
//		System.out.println(v.get("id").getClass());
//		LinkedHashMap linkedHashMap = (LinkedHashMap)v.get("id");
//		
//		System.out.println("Linked hash map is: " + linkedHashMap.get("location_id"));
//		System.out.println("Integer is: " + (int) linkedHashMap.get("location_id"));
//		
//		int locationId = (int) linkedHashMap.get("location_id");
//		String vehicleNumber = (String) linkedHashMap.get("vehicle_number");
		
		int locationId = (int) v.get("location_id");
		String vehicleNumber = (String) v.get("vehicle_number");

		VehicleLocationKey vehicleLocationKey = new VehicleLocationKey(locationId, vehicleNumber);
		
		Optional<Location> location = locationService.findById(locationId);
		Vehicles vehicle = vehiclesService.findByVehicleNumber(vehicleNumber);
		
		VehicleLocation vehicleLocation = new VehicleLocation(vehicleLocationKey, location.get(), vehicle);
		if(!location.isPresent() && vehicle == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
		
		try {
			vehicleLocationService.save(vehicleLocation);
			System.out.println("Found car: " + vehicleLocation.getVehicles().getMake() +  "Address at: " + 	vehicleLocation.getLocation().getAddress());
			return ResponseEntity.status(HttpStatus.OK).body(vehicleLocation);
		}
		catch(SQLIntegrityConstraintViolationException e) {
			System.out.println("Vehicle not found: ");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}
	
	@PutMapping("/vehicleLocation/update/{id}/{vehicleNumber}")
	public ResponseEntity<Object> updateVehicleLocation(@PathVariable int id, @PathVariable String vehicleNumber) {
		try {
			vehicleLocationService.update(vehicleNumber, id);
			
			return ResponseEntity.status(HttpStatus.OK).body(vehicleLocationService.findByIdLocationId(id));
		}
		catch (Exception e){
			System.out.println("Violated foreign key child");
			System.out.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.OK).body("Error");
			
		}
	}
		
	@DeleteMapping("vehicleLocation/delete/{id}") 
	public ResponseEntity<Object> deleteVehicleLocation(@PathVariable int id)
	{
		try {
			System.out.println("Id is: " + id);
			vehicleLocationService.deleteById(id);
			return ResponseEntity.status(HttpStatus.OK).body("Delete success!");
		}
		catch(IllegalArgumentException ex){
			System.out.println("Error deleting vehicle location: " + ex.getMessage());
			return ResponseEntity.status(HttpStatus.OK).body(null);
		}
	}

}
