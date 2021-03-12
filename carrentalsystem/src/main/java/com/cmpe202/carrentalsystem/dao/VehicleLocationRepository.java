package com.cmpe202.carrentalsystem.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.cmpe202.carrentalsystem.model.VehicleLocation;
import com.cmpe202.carrentalsystem.model.VehicleLocationKey;

public interface VehicleLocationRepository extends JpaRepository<VehicleLocation, VehicleLocationKey> {
	public Optional<VehicleLocation> findById(VehicleLocationKey id);
	public VehicleLocation findByIdVehicleNumber(String id);
	public VehicleLocation findByIdLocationId(int id);
	public VehicleLocation save(VehicleLocation vehicleLocation);
	public VehicleLocation findByIdLocationIdAndIdVehicleNumber(int locationId, String vehicleNumber);
	
	public List<VehicleLocation> findAllByIdLocationId(int id);
	public List<VehicleLocation> findAllByIdLocationIdAndIdVehicleNumber(int locationId, String vehicleNumber);
	public List<VehicleLocation> findAllByIdVehicleNumber(String id);
	
	

	@Query(value = "Select * FROM VehicleLocation, Location, Vehicles\r\n" + 
			"WHERE VehicleLocation.location_id = Location.location_id \r\n" + 
			"AND VehicleLocation.vehicle_number = Vehicles.vehicle_number\r\n" + 
			"AND Vehicles.vehicle_type = :vehicleType AND Location.location_id = :locationId", nativeQuery = true)
	public List<VehicleLocation> findByAddressAndVehicleType(@Param("vehicleType") String vehicleType, @Param("locationId") int locationId);

	
	@Modifying
	@Transactional 
	@Query(value = "UPDATE VehicleLocation SET location_id = :locationId WHERE vehicle_number = :vehicle_number", nativeQuery = true)
	public void update(@Param("vehicle_number")String vehicleNumber, @Param("locationId") int locationId);
	
	@Modifying
	@Transactional 
	@Query(value = "DELETE FROM VehicleLocation where location_id = :locationId", nativeQuery = true)
	public void deleteById(@Param("locationId") int locationId);
	
}
