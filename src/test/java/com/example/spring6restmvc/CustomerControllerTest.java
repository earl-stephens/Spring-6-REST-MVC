package com.example.spring6restmvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.spring6restmvc.controller.CustomerController;
import com.example.spring6restmvc.controller.NotFoundException;
import com.example.spring6restmvc.model.Customer;
import com.example.spring6restmvc.services.CustomerService;
import com.example.spring6restmvc.services.CustomerServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@MockBean
	CustomerService customerService;
	
	CustomerServiceImpl customerServiceImpl;
	
	@BeforeEach
	void setUp() {
		customerServiceImpl = new CustomerServiceImpl();		
	}
	
	@Captor
	ArgumentCaptor<UUID> uuidArgumentCaptor;
	
	@Captor
	ArgumentCaptor<Customer> customerArgumentCaptor;
	
	@Test
	void testGetCustomerByIdNotFound() throws Exception {
		given(customerService.getCustmerById(any(UUID.class))).willThrow(NotFoundException.class);
		
		mockMvc.perform(get(CustomerController.CUSTOMER_PATH_ID, UUID.randomUUID()))
										.andExpect(status().isNotFound());
	}
	
	@Test
	void testPatchCustomer() throws Exception {
		Customer customer = customerServiceImpl.listCustomers().get(0);
		
		Map<String, Object> customerMap = new HashMap<>();
		customerMap.put("name", "New Name");
		
		mockMvc.perform(patch(CustomerController.CUSTOMER_PATH_ID, customer.getId()).accept(MediaType.APPLICATION_JSON)
																	.contentType(MediaType.APPLICATION_JSON)
																	.content(objectMapper.writeValueAsString(customerMap)))
																	.andExpect(status().isNoContent());
		
		verify(customerService).patchCustomerById(uuidArgumentCaptor.capture(), customerArgumentCaptor.capture());
		assertThat(uuidArgumentCaptor.getValue()).isEqualTo(customer.getId());
		assertThat(customerArgumentCaptor.getValue().getName()).isEqualTo(customerMap.get("name"));
	}
	
	@Test
	void testDeleteCustomer() throws Exception {
		Customer customer = customerServiceImpl.listCustomers().get(0);
		
		mockMvc.perform(delete(CustomerController.CUSTOMER_PATH_ID, customer.getId()).accept(MediaType.APPLICATION_JSON))
																		.andExpect(status().isNoContent());
		
		verify(customerService).deleteCustomerById(uuidArgumentCaptor.capture());
		
		assertThat(customer.getId()).isEqualTo(uuidArgumentCaptor.getValue());
	}
	
	@Test
	void testUpdateCustomer() throws Exception {
		Customer customer = customerServiceImpl.listCustomers().get(0);
		
		mockMvc.perform(put(CustomerController.CUSTOMER_PATH_ID, customer.getId()).accept(MediaType.APPLICATION_JSON)
																	.contentType(MediaType.APPLICATION_JSON)
																	.content(objectMapper.writeValueAsBytes(customer)))
																	.andExpect(status().isNoContent());
		
		verify(customerService).updateCustomerById(uuidArgumentCaptor.capture(), any(Customer.class));
		
		assertThat(customer.getId()).isEqualTo(uuidArgumentCaptor.getValue());
	}
	
	@Test
	void testCreateNewCustomer() throws JsonProcessingException, Exception {
		Customer customer = customerServiceImpl.listCustomers().get(0);
		customer.setId(null);
		customer.setVersion(0);
		
		given(customerService.saveNewCustomer(any(Customer.class))).willReturn(customerServiceImpl.listCustomers().get(1));
		
		mockMvc.perform(post(CustomerController.CUSTOMER_PATH).accept(MediaType.APPLICATION_JSON)
												.contentType(MediaType.APPLICATION_JSON)
												.content(objectMapper.writeValueAsString(customer)))
												.andExpect(status().isCreated())
												.andExpect(header().exists("Location"));
		}
	
	@Test
	void testGetCustomerById() throws Exception {
		Customer testCustomer = customerServiceImpl.listCustomers().get(0);
		
		given(customerService.getCustmerById(testCustomer.getId())).willReturn(testCustomer);
		
		mockMvc.perform(get(CustomerController.CUSTOMER_PATH_ID, testCustomer.getId()).accept(MediaType.APPLICATION_JSON))
																		.andExpect(status().isOk())
																		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
																		.andExpect(jsonPath("$.id", is(testCustomer.getId().toString())))
																		.andExpect(jsonPath("$.name", is(testCustomer.getName())));
	}
	
	@Test
	void testGetCustomerList() throws Exception {
		given(customerService.listCustomers()).willReturn(customerServiceImpl.listCustomers());
		
		mockMvc.perform(get(CustomerController.CUSTOMER_PATH).accept(MediaType.APPLICATION_JSON))
												.andExpect(status().isOk())
												.andExpect(content().contentType(MediaType.APPLICATION_JSON))
												.andExpect(jsonPath("$.length()", is(3)));
	}
}
