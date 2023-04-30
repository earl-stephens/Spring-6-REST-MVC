package com.example.spring6restmvc.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring6restmvc.model.Beer;
import com.example.spring6restmvc.services.BeerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
public class BeerController {
	
	private final BeerService beerService;
	public static final String BEER_PATH = "/api/v1/beer";
	public static final String BEER_PATH_ID = "/api/v1/beer/{beerId}";
	
	@GetMapping(value = BEER_PATH_ID)
	public Beer getBeerById(@PathVariable("beerId") UUID beerId) {
		
		return beerService.getBeerById(beerId);
	}
	
	@GetMapping(value = BEER_PATH)
	public List<Beer> listBeers() {
		log.debug("Get beers - in controller");
		return beerService.listBeers();
	}
	
	
	  @PostMapping(BEER_PATH) 
	  public ResponseEntity handlePost(@RequestBody Beer beer) { 
		  Beer savedBeer = beerService.saveNewBeer(beer); 
		  
		  HttpHeaders headers = new HttpHeaders();
		  headers.add("Location", "/api/v1/beer/" + savedBeer.getId().toString());
		  
		  return new ResponseEntity(headers, HttpStatus.CREATED);
	  }
	  
	  @PutMapping(BEER_PATH_ID)
	  public ResponseEntity updateById(@PathVariable("beerId") UUID id, @RequestBody Beer beer) {
		  beerService.updateBeerById(id, beer);
		  
		  return new ResponseEntity(HttpStatus.NO_CONTENT);
	  }
	 
	  @DeleteMapping(BEER_PATH_ID)
	  public ResponseEntity deleteById(@PathVariable("beerId") UUID id) {
		  beerService.deleteBeerById(id);
		  
		  return new ResponseEntity(HttpStatus.NO_CONTENT);
	  }
	  
	  @PatchMapping(BEER_PATH_ID)
	  public ResponseEntity patchById(@PathVariable("beerId") UUID id, @RequestBody Beer beer) {
		  beerService.patchById(id, beer);
		  
		  return new ResponseEntity(HttpStatus.NO_CONTENT);
	  }
}

