package com.cmpe202.carrentalsystem.controllers;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cmpe202.carrentalsystem.model.CreditCard;
import com.cmpe202.carrentalsystem.model.UserModel;
import com.cmpe202.carrentalsystem.service.CreditCardService;
import com.cmpe202.carrentalsystem.service.UserService;

@RestController
public class CreditCardController {
	
	@Autowired
	CreditCardService creditCardService;
	
	@Autowired
	UserService userService;
	
	@GetMapping("user/{userId}/creditcards")
	public @ResponseBody ResponseEntity<List<CreditCard>> getCreditCards(@PathVariable int userId) {
		
		List<CreditCard> creditCards = creditCardService.findByUser(userId);
		
		return ResponseEntity.status(HttpStatus.OK).body(creditCards);
	}
	
	@GetMapping("/creditcard/{cardId}")
	public @ResponseBody ResponseEntity<CreditCard> getCreditCard(@PathVariable int cardId) {
		
		CreditCard creditCard = creditCardService.findById(cardId);
		
		return ResponseEntity.status(HttpStatus.OK).body(creditCard);
	}
	
	@PostMapping("/creditcard")
	public @ResponseBody ResponseEntity<CreditCard> addCreditCard(@RequestBody JSONObject creditCard) {
		
		String cardNumber = (String) creditCard.get("cardNumber");
		String name = (String) creditCard.get("name");
		int cvv = (int) creditCard.get("cvv");
		String validThrough = (String) creditCard.get("validThrough");
		int user_id = (int) creditCard.get("user_id");
		
		UserModel user = userService.findById(user_id);
		
		
		if (user == null) {
			System.out.println("User not available");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
		
		CreditCard creditCardObject = new CreditCard(cardNumber, name, cvv, validThrough, user);
		
		try {
			creditCardService.save(creditCardObject);
		} catch(SQLIntegrityConstraintViolationException e) {
			System.out.println("Error while inserting into database");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
		
		CreditCard newCreditCard = creditCardService.findByCardNumber(creditCardObject.getCardNumber());

		return ResponseEntity.status(HttpStatus.OK).body(newCreditCard);
	}
	
	@DeleteMapping("/creditcard/{card_id}")
	public ResponseEntity<CreditCard> deleteCreditCard(@PathVariable int card_id) {
		CreditCard creditCard = creditCardService.findById(card_id);
		
		if (creditCard == null) {
			System.out.println("Credit card does not exists");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		creditCardService.deleteById(card_id);
		return ResponseEntity.status(HttpStatus.OK).body(creditCard);
	}
	
	@PutMapping("/creditcard/{card_id}")
	public ResponseEntity<CreditCard> updateCreditCard(@PathVariable int card_id, @RequestBody JSONObject creditCardJSON) {
		CreditCard creditCard = creditCardService.findById(card_id);
		
		if (creditCard == null) {
			System.out.println("Credit card does not exists");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		
		Integer user_id = (Integer) creditCardJSON.get("user_id");
		
		if (user_id != null) {
			UserModel user = userService.findById(user_id);
			
			if (user == null) {
				System.out.println("User not available");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
			}
			
			creditCard.setUser_id(user);
		}
		
		String cardNumber = (String) creditCardJSON.get("cardNumber");
		if (cardNumber != null) {
			creditCard.setCardNumber(cardNumber);
		}
		
		String name = (String) creditCardJSON.get("name");
		if (name != null) {
			creditCard.setName(name);
		}
		
		Integer cvv = (Integer) creditCardJSON.get("cvv");
		if (cvv != null) {
			creditCard.setCvv(cvv);
		}
		
		String validThrough = (String) creditCardJSON.get("validThrough");
		if (validThrough != null) {
			creditCard.setValidThrough(validThrough);
		}
		
		CreditCard updatedCreditCard;
		try {
			updatedCreditCard = creditCardService.save(creditCard);
		} catch (SQLIntegrityConstraintViolationException e) {
			System.out.println("Error while updating the database");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(updatedCreditCard);
	}
}
