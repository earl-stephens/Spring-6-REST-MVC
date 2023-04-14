package com.example.spring6restmvc.controller;

import org.springframework.stereotype.Controller;

import com.example.spring6restmvc.services.BeerService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
public class BeerController {
	
	private final BeerService beerService;
	
}
