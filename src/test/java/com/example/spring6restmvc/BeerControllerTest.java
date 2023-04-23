package com.example.spring6restmvc;

import static org.junit.jupiter.api.Assertions.fail;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.spring6restmvc.controller.BeerController;

@SpringBootTest
class BeerControllerTest {

	@Autowired
	BeerController beerController;
	
	@Test
	void testGetBeerById() {
		System.out.println(beerController.getBeerById(UUID.randomUUID()));
	}

}
