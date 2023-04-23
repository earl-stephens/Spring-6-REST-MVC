package com.example.spring6restmvc.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring6restmvc.model.Beer;
import com.example.spring6restmvc.services.BeerService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@RestController
public class BeerController {
	
	private final BeerService beerService;
	
	public Beer getBeerById(UUID id) {
		log.debug("Get beer by Id - in controller");
		
		return beerService.getBeerById(id);
	}
	
	@RequestMapping("/api/v1/beer")
	public List<Beer> listBeers() {
		return beerService.listBeers();
	}
}

