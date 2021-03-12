package com.cmpe202.carrentalsystem.controllers;

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
import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cmpe202.carrentalsystem.model.Location;
import com.cmpe202.carrentalsystem.service.LocationService;

@RestController
public class LocationController {
	
	@Autowired
	private LocationService locationService;
	
	@GetMapping("/admin/locations")
	public @ResponseBody ResponseEntity<List<Location>> getAllLocations() {
		List<Location> locations = locationService.getAll();
		return ResponseEntity.status(HttpStatus.OK).body(locations);
	}
	
	@PostMapping("/admin/location/add")
	public void addLocation(@RequestBody Location loc) {
//		System.out.println("Inside post vehicle type:" + loc.getLocation_id());
		System.out.println("=====saved=======");
		System.out.println(locationService.save(loc));
	}
	
	@PutMapping("/admin/location/update")
	public void updateLocation(@RequestBody Location loc) {
		try {
			java.util.Optional<Location> location = locationService.findById(loc.getLocation_id());
			if(location.isPresent()) {
			
				location.get().setLocation_id(loc.getLocation_id());
				location.get().setAddress(loc.getAddress());
				location.get().setCur_cap_occupied(loc.getCur_cap_occupied());
				location.get().setLocation_capacity(loc.getLocation_capacity());
				
				locationService.save(location.get());
			}
			else {
				System.out.println("Record was not found for updating. Creating new record instead");
				locationService.save(location.get());
			}
		}
		catch (DataIntegrityViolationException  ex){
				System.out.println(ex.getMessage());
				System.out.println("Database Constraint was violated");
		}
	}
	
	@DeleteMapping("/admin/location/{id}")
	public void deleteLocation(@PathVariable int id) {
		System.out.println("Id is: " + id);
		try {
			locationService.delete(id);
			System.out.println("Vehicle has been deleted");
		}
		catch(IllegalArgumentException ex) {
			System.out.println("Error deleting vehicle: " + ex.getMessage());
			System.out.println("Vehicle id not valid");
		}
	}

}
