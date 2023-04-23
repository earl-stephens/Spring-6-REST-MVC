package com.example.spring6restmvc.services;

import java.util.UUID;

import com.example.spring6restmvc.model.Beer;

public interface BeerService {

	Beer getBeerById(UUID id);
}
