package com.example.spring6restmvc.controller;

import java.util.List;

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
}
