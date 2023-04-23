package com.example.spring6restmvc.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring6restmvc.model.Beer;
import com.example.spring6restmvc.services.BeerService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/vi/beer")
public class BeerController {
	
	private final BeerService beerService;
	
	@RequestMapping(value = "/{beerId}", method = RequestMethod.GET)
	public Beer getBeerById(@PathVariable("beerId") UUID beerId) {
		log.debug("Get beer by Id - in controller");
		
		return beerService.getBeerById(beerId);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public List<Beer> listBeers() {
		return beerService.listBeers();
	}
	
	@PostMapping
	public ResponseEntity handlePost(Beer beer) {
		Beer savedBeer = beerService.saveNewBeer(beer);
		return new ResponseEntity(HttpStatus.CREATED);
	}
}

