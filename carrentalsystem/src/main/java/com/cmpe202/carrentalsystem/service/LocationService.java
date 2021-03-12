package com.cmpe202.carrentalsystem.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmpe202.carrentalsystem.dao.LocationRepository;
import com.cmpe202.carrentalsystem.model.Location;

@Service
public class LocationService {
	
	@Autowired
	private LocationRepository locationRepository;
	
	public Location save(Location loc) {
		return locationRepository.save(loc);
			
	}

	public List<Location> getAll() {
		return locationRepository.findAll();
	}
	public Optional<Location> findById(int location_id) {
		
		return locationRepository.findById(location_id);
		
	}
	
	public void delete(int location_id) {
		locationRepository.deleteById(location_id);
	}
	
	public Location findByAddress(String address) {
		return locationRepository.findByAddress(address);
	}
	

}
