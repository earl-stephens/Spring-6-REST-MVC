package com.example.spring6restmvc.services;

import java.util.List;
import java.util.UUID;

import com.example.spring6restmvc.model.Customer;

public interface CustomerService {
	
	Customer getCustmerById(UUID id);
	
	List<Customer> listCustomers();
}
