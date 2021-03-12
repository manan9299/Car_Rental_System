package com.cmpe202.carrentalsystem.controllers;

import java.sql.SQLIntegrityConstraintViolationException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cmpe202.carrentalsystem.model.Location;
import com.cmpe202.carrentalsystem.model.Reservation;
import com.cmpe202.carrentalsystem.model.User;
import com.cmpe202.carrentalsystem.model.UserModel;
import com.cmpe202.carrentalsystem.model.VehicleType;
import com.cmpe202.carrentalsystem.model.Vehicles;
import com.cmpe202.carrentalsystem.service.LocationService;
import com.cmpe202.carrentalsystem.service.ReservationService;
import com.cmpe202.carrentalsystem.service.UserService;
import com.cmpe202.carrentalsystem.service.VehicleTypeService;
import com.cmpe202.carrentalsystem.service.VehiclesService;

import lombok.experimental.Helper;

@RestController
public class ReservationController {
	
	@Autowired
	private ReservationService reservationService;
	
	@Autowired
	private VehiclesService vehiclesService;
	
	@Autowired 
	private UserService userService;
	
	@Autowired 
	private LocationService locationService;
	
	@Autowired
	private VehicleTypeService vehicleTypeService;
	
	@GetMapping("/reservation")
	public void checkOrder(@RequestBody JSONObject v) {
		
	}
	
	@GetMapping("/reservation/order/{id}")
	public Reservation checkOrder(@PathVariable int id) {
		System.out.println("Finding by order id: " + id);
		Reservation order = reservationService.findByOrderId(id);
		return order;
	}
	
	@GetMapping("/reservation/user/{userId}")
	public ResponseEntity<JSONObject> getUserReservations(@PathVariable int userId) {
		
		UserModel user = userService.findById(userId);
		if (user == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
		List<Reservation> reservations = reservationService.findByUser(user);
		JSONObject responseObject = new JSONObject();
		List<Reservation> previous = new ArrayList<Reservation>();
		List<Reservation> upcoming = new ArrayList<Reservation>();

		for(Reservation reservation:reservations) {
			if (reservation.getStatus().equals("Finished") || reservation.getStatus().equals("Cancelled")) {
				previous.add(reservation);
			} else {
				upcoming.add(reservation);
			}
		}
		responseObject.put("previous", previous);
		responseObject.put("upcoming", upcoming);
		return ResponseEntity.status(HttpStatus.OK).body(responseObject);
	}
	
	@GetMapping("/reservation/vehicle")
	public ResponseEntity<Object> checkAvailability(@RequestBody JSONObject v) {
		/*
		 * Find by the vehicle number and the status (checking for active status) in reservation
		 * If it returns empty, that means there is no active reservation on that particular car, so it is available
		 */
		
		// Example: User enter
		String location = v.get("location").toString();
		String vehicleType = v.get("vehicle_type").toString();
		String pickupTime = v.get("pickup_time").toString();
		String returnTime = v.get("return_time").toString();
		List<String> recommendedLocations = null;

		Location loc = locationService.findByAddress(location);
		List<Vehicles> availableVehicles = vehiclesService.findAvailability(vehicleType, loc.getLocation_id());
	//	List<Reservation> availableVehicles  = reservationService.checkAvailability(vehicleType, loc.getLocation_id());
		
		/* First check: Are there any cars available
		 * Reservation being empty means that no user is using any vehicles of that type.
		 * numberOfVehicles equaling reservation size means all vehicles are full, if they are not equal then that means vehicles are available.
		 */
	
		// All vehicles at current location are unavailable
		if(availableVehicles.isEmpty()) {
			// Recommend similar vehicle at different location
			recommendedLocations = vehiclesService.recommendSimilar(vehicleType, loc.getLocation_id());
			return ResponseEntity.status(HttpStatus.OK).body(recommendedLocations);
		}

		return ResponseEntity.status(HttpStatus.OK).body(availableVehicles);
	}
	
//	@GetMapping("/reservation/vehicle")
//	public List<Reservation> checkAvailability(@RequestBody JSONObject v) {
//		/*
//		 * Find by the vehicle number and the status (checking for active status) in reserveation
//		 * If it returns empty, that means there is no active reservation on that particular car, so it is available
//		 */
//		String vehicleNumber = "";
//		List<Reservation> order = orderService.findAllByVehicle(vehicleNumber);
//		if(order.isEmpty()) {
//			System.out.println("Car is available");
//			System.out.println("You make book the car!");
//		}
//	
//		System.out.println(order.get(0).getBookingEndTime());
//		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//		/*
//		 * If the try clause is true then that means someone is trying to book a car that is still active for a certain date range
//		 */
//		try {
//			System.out.println(formatter.parse("2020-03-11 15:00:00").before(formatter.parse(order.get(0).getBookingEndTime())));
//			/*
//			 * If a booking cannot be made, a similar vehicle needs to be suggested at another location
//			 */
//			
//			List<VehicleLocation> vehicleLocation = vehicleLocationService.findAllByIdVehicleNumber(vehicleNumber);
//			
//			
//			if(formatter.parse("2020-03-11 15:00:00").before(formatter.parse(order.get(0).getBookingEndTime()))) {
//				
//			}
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//		System.out.println(order.get(0).getBookingStartTime().getClass());
//		return order;
//		
//	}
	
	
	@PutMapping("/reservation/{orderId}/cancel")
	public @ResponseBody ResponseEntity<Reservation> cancelReservation(@PathVariable int orderId) {
		Reservation order = reservationService.findByOrderId(orderId);
		
		if (order == null || order.getStatus().equals("Finished")) {
			System.out.println("Order does not exists");
			return ResponseEntity.status(HttpStatus.OK).body(null);
		}
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		java.util.Date returnDate = new java.util.Date();
		String returnTime = formatter.format(returnDate);
		System.out.println(returnTime);
		
		String feedback = null;
		
		Vehicles vehicle = vehiclesService.findByVehicleNumber(order.getVehicleNumber().getVehicleNumber());
		List<VehicleType> vehicleTypes = vehicleTypeService.findAllByVehicleType(vehicle.getVehicleType());
		
		String start = order.getBookingStartTime();
		String end = order.getBookingEndTime();
		java.util.Date startDate;
		java.util.Date endDate;

		try {
			startDate = formatter.parse(start);
			endDate = formatter.parse(end);
		} catch (ParseException e) {
			System.out.println("Internal server error");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}

		int charges = 0;
		
		long diff = (returnDate.getTime() - startDate.getTime())/1000;

		System.out.println(diff);
		
		if(diff > -3600) {
			System.out.println("Can not cancel after one hours");
			java.util.Date returnTimeForCalculation = endDate;

			// Late fee
			if(returnDate.after(endDate)) {
				System.out.println("Applying late fee");
				charges += 100;
				returnTimeForCalculation = returnDate;
				
			} else if(returnDate.after(startDate) && returnDate.before(endDate)) {
				System.out.println("Using booking end date");
				returnTimeForCalculation = endDate;
			} else {
				System.out.println("Using return time");
				Calendar cal = Calendar.getInstance();
				cal.setTime(startDate);
				cal.add(Calendar.HOUR_OF_DAY, 1);
				returnTimeForCalculation = cal.getTime();
			}
			
			long diffInMillies = returnTimeForCalculation.getTime() - startDate.getTime();
			System.out.println("Different in millies " + diffInMillies);
//			TimeUnit timeUnit = TimeUnit.MINUTES;
//			long minutes = timeUnit.convert(diffInMillies, TimeUnit.MINUTES);
//			System.out.println("Different in minutes " + minutes);
			int hours = (int)Math.ceil(diffInMillies / (1000 * 60 * 60));
					
			System.out.println("Hours " + hours);
			boolean flag = false;
			
			for(VehicleType vehicleType : vehicleTypes) {
				if (vehicleType.getRange_start() <= hours && vehicleType.getRange_end() >= hours) {
					charges += hours * vehicleType.getPrice();
					flag = true;
				}
			}

			// If no valid range found charge with 20$ per hours
			if(!flag) {
				charges += hours * 20;
			}
	
		}
		order.setStatus("Cancelled");
		order.setCharges(Integer.toString(charges));
		
		try {
			reservationService.save(order);
		} catch (SQLIntegrityConstraintViolationException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
			
		return ResponseEntity.status(HttpStatus.OK).body(order);
		
		
	}
	
	@PostMapping("/reservation")
	public Reservation createOrder(@RequestBody JSONObject v) throws ParseException {

		String vNum = v.get("vehicle").toString();
		System.out.println("Vnum is: " + vNum);
		
		Vehicles vehicleNumber = vehiclesService.findByVehicleNumber(vNum);
		UserModel userId = userService.findById((int)v.get("user"));
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		System.out.println("Start Time is: " + v.get("bookingStartTime").toString());
		String i = v.get("bookingStartTime").toString();

		
		java.util.Date startTime = (java.util.Date) formatter.parseObject(v.get("bookingStartTime").toString());
		java.util.Date endTime = (java.util.Date) formatter.parseObject(v.get("bookingEndTime").toString());
	
		java.sql.Timestamp bookingStartTime = new java.sql.Timestamp(startTime.getTime());
		java.sql.Timestamp bookingEndTime = new java.sql.Timestamp(endTime.getTime());
		
		String start = v.get("bookingStartTime").toString();
		String end = v.get("bookingEndTime").toString();
		//Timestamp timestamp = new java.sql.Timestamp(startTime.getTime());
		System.out.println("What is timestamp: " + bookingStartTime);
		System.out.println("What is timestamp: " + bookingEndTime);
		System.out.println("What is timezone: " + bookingStartTime.getTimezoneOffset());
		String status = "Active";
		System.out.println(bookingStartTime);
		System.out.println(bookingEndTime);

		
		try {
			reservationService.save(new Reservation(vehicleNumber, userId, start, end, null, null, null, status, null));
		} catch (SQLIntegrityConstraintViolationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@PostMapping("/reservation/return") 
	public @ResponseBody ResponseEntity<Reservation> returnVehicle(@RequestBody JSONObject returnObject) {
		
		if (!returnObject.containsKey("orderId")) {
			System.out.println("Invalid or missing parameters");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
		
		int orderId;
		try {
			orderId = (Integer) returnObject.get("orderId");
		} catch(Exception e) {
			System.out.println("Invalid or missing parameters");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
		
		Reservation order = reservationService.findByOrderId(orderId);
		
		if (order == null || order.getStatus().equals("Finished")) {
			System.out.println("Order is invalid or not active");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		java.util.Date returnDate = new java.util.Date();
		String returnTime = formatter.format(returnDate);
		System.out.println(returnTime);
		
		String feedback = null;
		if (returnObject.containsKey("feedback")) {
			feedback = (String) returnObject.get("feedback");
		}
		
		Vehicles vehicle = vehiclesService.findByVehicleNumber(order.getVehicleNumber().getVehicleNumber());
		List<VehicleType> vehicleTypes = vehicleTypeService.findAllByVehicleType(vehicle.getVehicleType());
		
		String start = order.getBookingStartTime();
		String end = order.getBookingEndTime();
		int charges = 0;
		
		java.util.Date startDate;
		java.util.Date endDate;

		try {
			startDate = formatter.parse(start);
			endDate = formatter.parse(end);
		} catch (ParseException e) {
			System.out.println("Internal server error");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
		
		java.util.Date returnTimeForCalculation = endDate;
		// Late fee
		if(returnDate.after(endDate)) {
			charges += 100;
			returnTimeForCalculation = returnDate;
			
		}
		
		long diffInMillies = returnTimeForCalculation.getTime() - startDate.getTime();
//		TimeUnit timeUnit = TimeUnit.MINUTES;
//		long minutes = timeUnit.convert(diffInMillies, TimeUnit.MINUTES);
//		int hours = (int)Math.ceil(minutes/60);
		int hours = (int)Math.ceil(diffInMillies / (1000 * 60 * 60));
		boolean flag = false;
		
		for(VehicleType vehicleType : vehicleTypes) {
			if (vehicleType.getRange_start() <= hours && vehicleType.getRange_end() >= hours) {
				charges += hours * vehicleType.getPrice();
				flag = true;
			}
		}

		// If no valid range found charge with 20$ per hours
		if(!flag) {
			charges += hours * 20;
		}
		
		order.setReturnTime(returnTime);
		order.setFeedback(feedback);
		order.setCharges(Integer.toString(charges));
		order.setStatus("Finished");
		
		try {
			reservationService.save(order);
		} catch (SQLIntegrityConstraintViolationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(order);
	}

}
