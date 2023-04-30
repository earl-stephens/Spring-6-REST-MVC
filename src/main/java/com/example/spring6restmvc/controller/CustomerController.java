package com.example.spring6restmvc.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring6restmvc.model.Customer;
import com.example.spring6restmvc.services.CustomerService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class CustomerController {
	
	private final CustomerService customerService;
	public static final String CUSTOMER_PATH = "/api/v1/customer";
	public static final String CUSTOMER_PATH_ID = "/api/v1/customer/{customerId}";
	
	@GetMapping(CUSTOMER_PATH)
	public List<Customer> listCustomers() {
		return customerService.listCustomers();
	}
	
	@GetMapping(CUSTOMER_PATH_ID)
	public Customer getCustomerById(@PathVariable("customerId") UUID customerId) {
		return customerService.getCustmerById(customerId);
	}
	
	@PostMapping(CUSTOMER_PATH)
	public ResponseEntity handlePost(@RequestBody Customer customer) {
		Customer savedCustomer = customerService.saveNewCustomer(customer);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Location", "/api/v1/customer/" + savedCustomer.getId().toString());
		return new ResponseEntity(headers,HttpStatus.CREATED);
	}
	
	@PutMapping(CUSTOMER_PATH_ID)
	public ResponseEntity updateCustomerById(@PathVariable("customerId") UUID id, @RequestBody Customer customer) {
		customerService.updateCustomerById(id, customer);
		
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}
	
	@DeleteMapping(CUSTOMER_PATH_ID)
	public ResponseEntity deleteCustomerById(@PathVariable("customerId") UUID id) {
		customerService.deleteCustomerById(id);
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PatchMapping(CUSTOMER_PATH_ID)
	public ResponseEntity patchCustomerById(@PathVariable("customerId") UUID id, @RequestBody Customer customer) {
		customerService.patchCustomerById(id, customer);
		
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}
}
