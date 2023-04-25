package com.example.spring6restmvc.services;

import java.util.List;
import java.util.UUID;

import com.example.spring6restmvc.model.Beer;

public interface BeerService {

	Beer getBeerById(UUID id);
	
	List<Beer> listBeers();
	
	Beer saveNewBeer(Beer beer);
	
	Beer updateBeerById(UUID id, Beer beer);
	
	void deleteBeerById(UUID id);
}
