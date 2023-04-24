package com.example.spring6restmvc.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring6restmvc.model.Customer;
import com.example.spring6restmvc.services.CustomerService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/customer")
public class CustomerController {
	
	private final CustomerService customerService;
	
	@RequestMapping(method = RequestMethod.GET)
	public List<Customer> listCustomers() {
		return customerService.listCustomers();
	}
	
	@RequestMapping(value = "/{customerId}", method = RequestMethod.GET)
	public Customer getCustomerById(@PathVariable("customerId") UUID customerId) {
		return customerService.getCustmerById(customerId);
	}
	
	@PostMapping
	public ResponseEntity handlePost(@RequestBody Customer customer) {
		Customer savedCustomer = customerService.saveNewCustomer(customer);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Location", "/api/v1/customer/" + savedCustomer.getId().toString());
		return new ResponseEntity(headers,HttpStatus.CREATED);
	}
}
