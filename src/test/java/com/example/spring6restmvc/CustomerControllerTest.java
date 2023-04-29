package com.example.spring6restmvc;

import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.spring6restmvc.controller.CustomerController;
import com.example.spring6restmvc.model.Customer;
import com.example.spring6restmvc.services.CustomerService;
import com.example.spring6restmvc.services.CustomerServiceImpl;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {
	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	CustomerService customerService;
	
	CustomerServiceImpl customerServiceImpl = new CustomerServiceImpl();
	
	@Test
	void testGetCustomerById() throws Exception {
		Customer testCustomer = customerServiceImpl.listCustomers().get(0);
		
		given(customerService.getCustmerById(testCustomer.getId())).willReturn(testCustomer);
		
		mockMvc.perform(get("/api/v1/customer/" + testCustomer.getId()).accept(MediaType.APPLICATION_JSON))
																		.andExpect(status().isOk())
																		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
																		.andExpect(jsonPath("$.id", is(testCustomer.getId().toString())))
																		.andExpect(jsonPath("$.name", is(testCustomer.getName())));
	}
	
	@Test
	void testGetCustomerList() throws Exception {
		given(customerService.listCustomers()).willReturn(customerServiceImpl.listCustomers());
		
		mockMvc.perform(get("/api/v1/customer").accept(MediaType.APPLICATION_JSON))
												.andExpect(status().isOk())
												.andExpect(content().contentType(MediaType.APPLICATION_JSON))
												.andExpect(jsonPath("$.length()", is(3)));
	}
}
