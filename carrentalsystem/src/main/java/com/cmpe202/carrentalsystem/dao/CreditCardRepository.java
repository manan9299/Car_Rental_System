package com.cmpe202.carrentalsystem.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cmpe202.carrentalsystem.model.CreditCard;
import com.cmpe202.carrentalsystem.model.UserModel;

public interface CreditCardRepository extends JpaRepository<CreditCard, Integer>{
	
	public CreditCard findById(int id);
	public CreditCard save(CreditCard creditCard);
	public CreditCard deleteById(int id);
	public CreditCard findByCardNumber(String cardNumber);
	@Query(value = "SELECT * from CreditCards where user_id=?1", nativeQuery = true)
	public List<CreditCard> findByUser(int id);
	
}
