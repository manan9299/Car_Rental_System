package com.cmpe202.carrentalsystem.controllers;

import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.cmpe202.carrentalsystem.model.UserModel;
import org.springframework.web.bind.annotation.PutMapping;
import com.cmpe202.carrentalsystem.model.APIError;
import com.cmpe202.carrentalsystem.service.UserService;


@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/admin")
	public String onlyAdmin() {
	
	return "Admin access";
	}

	@GetMapping("/customer")
	public String onlyCustomer() {
	
	return "Customer access";
	}
	
	@GetMapping("/both")
	public String allUsers() {
	
	return "All users";
	}

	@GetMapping("/registration")
    public String registration(Model model) {
		
		System.out.println("get registration");
        model.addAttribute("userForm", new UserModel());
        return "get registration";
    }
	
	@PutMapping("/updateUser")
	public @ResponseBody ResponseEntity<Object> updateUser(@RequestBody JSONObject newUser)
	{
		if (!newUser.containsKey("id")) {
			APIError apiError = new APIError();
			apiError.setMessage("Missing required paramters");
			return ResponseEntity.status(400).body(apiError);
		}
		
		int id = (int) newUser.get("id");
		UserModel userModel = userService.findById(id);
		
		if (userModel == null) {
			APIError apiError = new APIError();
			apiError.setMessage("Please specify valid user");
			return ResponseEntity.status(404).body(apiError);
		}
		
		UserModel updatedUser;

		try {
			updatedUser = userService.update(newUser, userModel);

		} catch(Exception e)
		{
			System.out.println(e);
        	APIError apiError = new APIError();
        	apiError.setMessage("Invalid parameters value");
        	return ResponseEntity.status(400).body(apiError);
		}

		return ResponseEntity.status(200).body(updatedUser);
	}

	@PostMapping("/registration")
    public @ResponseBody ResponseEntity<Object> registration(@RequestBody UserModel user) {
         user.setRole("customer");
        try {
        	userService.save(user);
        }
        catch (NullPointerException e) {
        	System.out.println(e);
        	APIError apiError = new APIError();
        	apiError.setMessage("Missing required parameters");
        	return ResponseEntity.status(400).body(apiError);
        }
        catch (DataIntegrityViolationException e) {
        	System.out.println(e);
        	APIError apiError = new APIError();
        	apiError.setMessage("User already exists or invalid entry.");
        	return ResponseEntity.status(400).body(apiError);
		} 
        UserModel newUser =  userService.findByEmail(user.getEmail());
		return ResponseEntity.status(201).body(newUser);
    }
	
	@GetMapping("/login")
    public @ResponseBody ResponseEntity<Object> login() {
		org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		System.out.println(principal);
		UserModel newUser =  userService.findByEmail(principal.getUsername());
		return ResponseEntity.status(200).body(newUser);

	}
	
	@GetMapping("/admin/allUsers")
    public @ResponseBody ResponseEntity<List<UserModel>> getAllUsers() {
		
		List<UserModel> usersList = userService.findAllUsers();
		return ResponseEntity.status(200).body(usersList);

	}
	
	@GetMapping("/getUser")
	public @ResponseBody ResponseEntity<Object> getUser(@RequestParam String email){

		/*try {
			UserModel user = 
			return ResponseEntity.status(200).body(user);
		}catch(Exception e)
		{
			APIError apiError = new APIError();
        	apiError.setMessage(e.getMessage());
        	return ResponseEntity.status(400).body(apiError);
		}*/
		
		try {
			UserModel user = userService.findByEmail(email);	
			return ResponseEntity.status(200).body(user);	
		}catch(Exception e)
		{
			APIError apiError = new APIError();
        	apiError.setMessage(e.getMessage());
        	return ResponseEntity.status(400).body(apiError);
		}
		
	}

	@GetMapping("/currentuser")
	public @ResponseBody ResponseEntity<UserModel> getCurrentUser() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		String userName;
		if (principal instanceof UserDetails) {
		  userName = ((UserDetails)principal).getUsername();
		} else {
		  System.out.println("Error while getting the user details");
		  return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
		
		UserModel user = userService.findByEmail(userName);
		return ResponseEntity.status(HttpStatus.OK).body(user);
	}
	
	
	@GetMapping("/welcome")
	public String welcome() {
		org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return principal.getUsername();
	}
}
