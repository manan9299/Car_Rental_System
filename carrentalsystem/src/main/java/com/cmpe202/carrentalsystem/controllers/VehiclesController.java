package com.cmpe202.carrentalsystem.controllers;

import java.sql.Date;
import java.sql.SQLIntegrityConstraintViolationException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
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

import com.cmpe202.carrentalsystem.model.Location;
import com.cmpe202.carrentalsystem.model.VehicleLocation;
import com.cmpe202.carrentalsystem.model.VehicleType;
import com.cmpe202.carrentalsystem.model.Vehicles;
import com.cmpe202.carrentalsystem.service.LocationService;
import com.cmpe202.carrentalsystem.service.VehicleLocationService;
import com.cmpe202.carrentalsystem.service.VehicleTypeService;
import com.cmpe202.carrentalsystem.service.VehiclesService;

@RestController
public class VehiclesController {

	@Autowired
	private VehiclesService vehiclesService;
	
	@Autowired
	private VehicleTypeService vehicleTypeService;
	
	@Autowired
	private VehicleLocationService vehicleLocationService;
	
	@Autowired
	private LocationService locationService;
	
	
	@GetMapping("/vehicle/test")
	public List<VehicleLocation> checkAvailability(@RequestBody JSONObject v) {
		System.out.println("INSIDE VEHICLE/TEST");
		System.out.println((String)v.get("vehicle_type"));
		VehicleType vt = vehicleTypeService.findByVehicleType((String)v.get("vehicle_type"));
		System.out.println("DOES IT GET PAST VT");
		if(vt == null) {
			System.out.println("Car not found");
		}
		//System.out.println("vt is: "+ vt.getVehicle_type_id());
		
		/*
		 * Search location by address field
		 * Return(s): Get all of the information for the searched address
		 */
		Location loc = locationService.findByAddress((String)v.get("address"));
		System.out.println("Location id is: " + loc.getLocation_id());
		
		
		/*
		 * Use returned location id to search in the VehicleLocation Table
		 * Return(s): Returns all location information and all vehicle type information and vehicle information
		 */
		
		/*
		 * Search by the vehicle type that users selects
		 * Return(s): Returns the vehicle type information 
		 */
		VehicleType vehicleType = vehicleTypeService.findByVehicleType((String)v.get("vehicle_type"));
		
		
		System.out.println("Location id is: " + loc.getLocation_id());
		//System.out.println("Vehicle Type id is: " + vehicleType.getVehicle_type_id());
		String id = vehicleType.getVehicle_type();
		
		/*
		 * Uses the vehicle type id and returns all locations that have the vehicle type
		 */
		List<VehicleLocation> allLocations = vehicleLocationService.findAllByIdVehicleNumber(id);
		
		
		/*
		 * Uses vehicle location id and vehicle number and returns all of the information that matches
		 */
		
		List<VehicleLocation> vehicleLoc = vehicleLocationService.findAllByIdLocationIdAndIdVehicleNumber(loc.getLocation_id(), id);

		
		System.out.println("vehicleLoc: " + vehicleLoc);

		return allLocations;
	}
	
	@GetMapping("/vehicle/{id}")
	public Vehicles getVehicle(@PathVariable String id) {
		System.out.println("What is parameter: " + id);
		Vehicles vehicle = vehiclesService.findByVehicleNumber(id);
		if(vehicle != null) {
			System.out.println("Found car: " + vehicle.getMake());
		}
		else {
			System.out.println("Vehicle not found: ");
		}	
		return vehicle;
	}

	@GetMapping("/getVehicles")
	public ResponseEntity<Object> getVehicles() {
		System.out.println("Reached");
		try {
			List<Object[]> records = vehiclesService.getVehicles();
			Vector vec = null;
			JSONArray arr = new JSONArray();
			for (Object[] obj : records)
			{
				JSONObject jsonObj = new JSONObject();		
				jsonObj.put("make",obj[0]);
				jsonObj.put("model",obj[1]);
				jsonObj.put("vehicle_number",obj[2]);
				jsonObj.put("type",obj[3]);
				jsonObj.put("address",obj[4]);
				jsonObj.put("price",obj[5]);
				arr.add(jsonObj);
			}
			
			return ResponseEntity.status(200).body(arr);
				
		}
		catch(Exception e)
		{
			System.out.println("Error in fetching vehicle detailes: " + e.getMessage());			
			return ResponseEntity.status(HttpStatus.OK).body(null);
		}
	}
	
	@PostMapping("/admin/vehicle")
	public ResponseEntity<Object> addVehicle(@RequestBody JSONObject v) throws ParseException {
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		java.util.Date date = (java.util.Date) formatter.parseObject(v.get("last_serviced").toString());
		
		String vehicleNumber = (String) v.get("vehicle_number");
		String make = (String) v.get("make");
		String model = (String) v.get("model");
		String year = (String) v.get("year");
		String registration_number = (String) v.get("registration_number");
		int mileage = Integer.parseInt((String) v.get("mileage"));
		Date last_serviced = new java.sql.Date(date.getTime());
		System.out.println(last_serviced);
		String condition = (String) v.get("condition");
		String vehicleType = v.get("vehicle_type").toString();
		Vehicles vehiclesObj = new Vehicles(vehicleNumber, make, model, year, registration_number,
				mileage,last_serviced, condition, vehicleType);
		
		try {
			System.out.println("Saved successfully");
			System.out.println(vehiclesObj.getCondition());
			vehiclesService.save(vehiclesObj);
			System.out.println("Saved successfully after!");
		} catch (SQLIntegrityConstraintViolationException e) {
			System.out.println("Inside date error");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
		
		Vehicles vehiclesSavedObj = vehiclesService.findByVehicleNumber(vehiclesObj.getVehicleNumber());
		return ResponseEntity.status(HttpStatus.OK).body(vehiclesSavedObj);
	}

	@GetMapping("/admin/vehicle/all")
	public ResponseEntity<List<Vehicles>> getAllVehicles() {
		System.out.println("Inside getAllVehicleTypes");

		List<Vehicles> vehList = vehiclesService.findAllVehicles();

		return ResponseEntity.status(HttpStatus.OK).body(vehList);
	}
	
	@PutMapping("/admin/vehicle/update")
	public void updateVehicle(@RequestBody JSONObject v) throws ParseException {
		try {
			
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
			java.util.Date date = (java.util.Date) formatter.parseObject(v.get("last_serviced").toString());
			
			String vehicleNumber = (String) v.get("vehicle_number");
			String make = (String) v.get("make");
			String model = (String) v.get("model");
			String year = (String) v.get("year");
			String registration_number = (String) v.get("registration_number");
			int mileage = (int) v.get("mileage");
			Date last_serviced = new java.sql.Date(date.getTime());
			System.out.println(last_serviced);
			String condition = (String) v.get("car_condition");
			//int vehicle_type_id = (int) v.get("vehicle_type_id");
			String vehicleType = v.get("vehicleType").toString();
			
			Vehicles vehicle = vehiclesService.findByVehicleNumber(vehicleNumber);
			//Optional<VehicleType> user = vehicleTypeService.findById(vehicle_type_id);
			
			if(vehicle != null) {
			
				vehicle.setVehicleNumber(vehicle.getVehicleNumber());
				vehicle.setMake(make);
				vehicle.setModel(model);
				vehicle.setYear(year);
				vehicle.setRegistration_number(registration_number);
				vehicle.setMileage(mileage);
				vehicle.setLast_service(new java.sql.Date(date.getTime()));
				vehicle.setCondition(condition);
				vehicle.setVehicleType(vehicleType);
				//vehicle.setVehicle_type_id(user.get());
				
				try {
					System.out.println("Update save:");
					System.out.println(vehicle.getVehicleNumber());
					System.out.println(vehicle.getVehicleNumber());
					System.out.println(vehicle.getVehicleNumber());
					vehiclesService.save(vehicle);
				} catch (SQLIntegrityConstraintViolationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else {
				System.out.println("Record was not found for updating. Creating new record instead");
				try {
					vehiclesService.save(vehicle);
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
	
	@DeleteMapping("/admin/vehicle/{id}")
	public ResponseEntity<Object> deleteVehicle(@PathVariable String id) {
		//System.out.println("Id is: " + id.getClass());
		try {
			Vehicles vehicle = vehiclesService.findByVehicleNumber(id);
			vehiclesService.deleteByVehicleNumber(vehicle.getVehicleNumber());
			System.out.println("Vehicle has been deleted");
			return ResponseEntity.status(HttpStatus.OK).body("Delete success!");
		}
		catch(IllegalArgumentException ex) {
			System.out.println("Error deleting vehicle: " + ex.getMessage());
			System.out.println("Vehicle id not valid");
			return ResponseEntity.status(HttpStatus.OK).body(null);
		}

	}
}
