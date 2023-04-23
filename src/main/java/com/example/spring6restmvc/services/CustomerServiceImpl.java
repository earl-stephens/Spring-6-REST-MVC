package com.example.spring6restmvc.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.spring6restmvc.model.Customer;

@Service
public class CustomerServiceImpl implements CustomerService {

	private Map<UUID, Customer> customerMap;
	
	public CustomerServiceImpl() {
		this.customerMap = new HashMap<>();
		
		Customer customer1 = Customer.builder()
				.id(UUID.randomUUID())
				.customerName("John")
				.version(1)
				.createdDate(LocalDateTime.now())
				.lastModifiedDate(LocalDateTime.now())
				.build();
		
		Customer customer2 = Customer.builder()
				.id(UUID.randomUUID())
				.customerName("Jack")
				.version(1)
				.createdDate(LocalDateTime.now())
				.lastModifiedDate(LocalDateTime.now())
				.build();
				
		Customer customer3 = Customer.builder()
				.id(UUID.randomUUID())
				.customerName("Jason")
				.version(1)
				.createdDate(LocalDateTime.now())
				.lastModifiedDate(LocalDateTime.now())
				.build();
		
		customerMap.put(customer1.getId(), customer1);
		customerMap.put(customer2.getId(), customer2);
		customerMap.put(customer3.getId(), customer3);
	}
	
	@Override
	public Customer getCustmerById(UUID id) {
		return customerMap.get(id);
	}

	@Override
	public List<Customer> listCustomers() {
		return new ArrayList<>(customerMap.values());
	}

}