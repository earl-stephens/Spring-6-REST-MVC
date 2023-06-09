package com.example.spring6restmvc.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.example.spring6restmvc.model.Customer;

public interface CustomerService {
	
	Optional<Customer> getCustmerById(UUID id);
	
	List<Customer> listCustomers();
	
	Customer saveNewCustomer(Customer customer);
	
	void updateCustomerById(UUID id, Customer customer);
	
	void deleteCustomerById(UUID id);
	
	void patchCustomerById(UUID id, Customer customer);
}
