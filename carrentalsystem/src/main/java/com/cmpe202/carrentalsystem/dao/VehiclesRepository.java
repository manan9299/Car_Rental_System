package com.cmpe202.carrentalsystem.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import com.cmpe202.carrentalsystem.model.Vehicles;

public interface VehiclesRepository extends JpaRepository<Vehicles, String>{
	
	public Vehicles findByVehicleNumber(String vehicleNumber);
	public Vehicles save(Vehicles creditCard);
	
	public List<Vehicles> findAllByVehicleType(String vehicleType);

	public List<Vehicles> findAll();
	
	
	@Query(value = "Select Vehicles.vehicle_number, Vehicles.make, Vehicles.model, Vehicles.year, Vehicles.registration_number, Vehicles.mileage,\r\n" + 
			"Vehicles.last_serviced, Vehicles.car_condition, Vehicles.vehicle_type, VehicleLocation.location_id FROM Vehicles, VehicleLocation\r\n" + 
			"WHERE Vehicles.vehicle_type = :vehicleType AND Vehicles.vehicle_number = VehicleLocation.vehicle_number\r\n" + 
			"AND VehicleLocation.location_id = :locationId\r\n" + 
			"AND Vehicles.vehicle_number NOT IN (Select vehicle_number FROM Reservation WHERE status = \"Active\")", nativeQuery = true)
	public List<Vehicles> findAvailability(@Param("vehicleType") String vehicleType, @Param("locationId") int locationId);
	
	@Query(value = "SELECT address FROM \r\n" + 
			"(\r\n" + 
			"Select VehicleLocation.location_id FROM Vehicles, VehicleLocation\r\n" + 
			"WHERE Vehicles.vehicle_number = VehicleLocation.vehicle_number AND Vehicles.vehicle_type = :vehicleType\r\n" + 
			"AND VehicleLocation.location_id != :locationId\r\n" + 
			"AND Vehicles.vehicle_number NOT IN (Select vehicle_number FROM Reservation WHERE status = \"Active\")\r\n" + 
			") t\r\n" + 
			"INNER JOIN \r\n" + 
			"Location\r\n" + 
			"ON t.location_id = Location.location_id", nativeQuery = true)
	public List<String> recommendSimilar(@Param("vehicleType") String vehicleType, @Param("locationId") int locationId);
	
	
	
	@Modifying
	@Transactional 
	@Query(value = "DELETE from Vehicles where vehicle_number=:vehicleNumber", nativeQuery = true)
	public void deleteByVehicleNumber(@Param("vehicleNumber") String vehicleNumber);
	
	public void deleteByMileage(int number);
	
	@Query(value = "Select Vehicles.make as make,Vehicles.model as model,Vehicles.vehicle_number as veh1icle_number, Vehicles.vehicle_type as type,Location.address as address, VehicleType.price\r\n" + 
			"from Vehicles \r\n" + 
			"inner join VehicleType on VehicleType.vehicle_type=Vehicles.vehicle_type \r\n" + 
			"inner join VehicleLocation on VehicleLocation.vehicle_number = Vehicles.vehicle_number \r\n" + 
			"inner join Location on VehicleLocation.location_id = Location.location_id", nativeQuery = true)
	public List<Object[]> getVehicles();
	

}
