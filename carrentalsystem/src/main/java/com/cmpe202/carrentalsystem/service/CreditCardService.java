package com.cmpe202.carrentalsystem.service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmpe202.carrentalsystem.dao.CreditCardRepository;
import com.cmpe202.carrentalsystem.model.CreditCard;

@Service
public class CreditCardService {

	@Autowired
	CreditCardRepository creditCardRepository;
	
	public CreditCard findById(int id) {
		return creditCardRepository.findById(id);
	}
	
	public CreditCard save(CreditCard creditCard) throws SQLIntegrityConstraintViolationException {
		return creditCardRepository.save(creditCard);
	}
	
	public CreditCard findByCardNumber(String cardNumber) {
		return creditCardRepository.findByCardNumber(cardNumber);
	}
	
	public CreditCard deleteById(int id) {
		return creditCardRepository.deleteById(id);
	}
	
	public List<CreditCard> findByUser(int id) {
		List<CreditCard> creditCards = creditCardRepository.findByUser(id);
		System.out.println(creditCards);
		return creditCards;
	}
}
