package com.cmpe202.carrentalsystem.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cmpe202.carrentalsystem.model.Location;

public interface LocationRepository extends JpaRepository<Location, Integer>{

	public Location findByAddress(String address);
	public List<Location> findAll();
}
