package com.example.spring6restmvc.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.spring6restmvc.model.Beer;
import com.example.spring6restmvc.model.BeerStyle;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BeerServiceImpl implements BeerService {

	private Map<UUID, Beer> beerMap;
	
	public BeerServiceImpl()  {
		this.beerMap = new HashMap<>();
		
		Beer beer1 = Beer.builder()
				.id(UUID.randomUUID())
				.version(1)
				.beerName("Galaxy Cat")
				.beerStyle(BeerStyle.PALE_ALE)
				.upc("123456")
				.price(new BigDecimal("12.99"))
				.quantityOnHand(122)
				.createdDate(LocalDateTime.now())
				.updateDate(LocalDateTime.now())
				.build();
		
		Beer beer2 = Beer.builder()
				.id(UUID.randomUUID())
				.version(1)
				.beerName("Crank")
				.beerStyle(BeerStyle.PALE_ALE)
				.upc("12356222")
				.price(new BigDecimal("11.99"))
				.quantityOnHand(392)
				.createdDate(LocalDateTime.now())
				.updateDate(LocalDateTime.now())
				.build();
		
		Beer beer3 = Beer.builder()
				.id(UUID.randomUUID())
				.version(1)
				.beerName("Sunshine City")
				.beerStyle(BeerStyle.IPA)
				.upc("12356")
				.price(new BigDecimal("13.99"))
				.quantityOnHand(144)
				.createdDate(LocalDateTime.now())
				.updateDate(LocalDateTime.now())
				.build();
		
		beerMap.put(beer1.getId(), beer1);
		beerMap.put(beer2.getId(), beer2);
		beerMap.put(beer3.getId(), beer3);
		}

		@Override
		public Optional<Beer> getBeerById(UUID id) {
			log.debug("Get Beer by Id - in service. Id: " + id.toString());
			
			return Optional.of(beerMap.get(id));
		}
		
		public List<Beer> listBeers() {
			return new ArrayList<>(beerMap.values());
		}
		
		public Beer saveNewBeer(Beer beer) {
			Beer savedBeer = Beer.builder()
					.id(UUID.randomUUID())
					.createdDate(LocalDateTime.now())
					.updateDate(LocalDateTime.now())
					.beerName(beer.getBeerName())
					.beerStyle(beer.getBeerStyle())
					.quantityOnHand(beer.getQuantityOnHand())
					.version(beer.getVersion())
					.upc(beer.getUpc())
					.price(beer.getPrice())
					.build();
			
			beerMap.put(savedBeer.getId(), savedBeer);
			
			return savedBeer;
		}

		@Override
		public Beer updateBeerById(UUID id, Beer beer) {
			Beer existing = beerMap.get(id);
			
			existing.setBeerName(beer.getBeerName());
			existing.setBeerStyle(beer.getBeerStyle());
			existing.setPrice(beer.getPrice());
			existing.setQuantityOnHand(beer.getQuantityOnHand());
			existing.setUpc(beer.getUpc());
			existing.setUpdateDate(LocalDateTime.now());

			return beer;
		}

		@Override
		public void deleteBeerById(UUID id) {
			beerMap.remove(id);
		}

		@Override
		public void patchById(UUID id, Beer beer) {
			Beer existing = beerMap.get(id);
			
			if(StringUtils.hasText(beer.getBeerName())) {
				existing.setBeerName(beer.getBeerName());
			}
			
			if(beer.getBeerStyle() != null) {
				existing.setBeerStyle(beer.getBeerStyle());
			}
			
			if(beer.getPrice() != null) {
				existing.setPrice(beer.getPrice());
			}
			
			if(beer.getQuantityOnHand() != null) {
				existing.setQuantityOnHand(beer.getQuantityOnHand());
			}
			
			if(StringUtils.hasText(beer.getUpc())) {
				existing.setUpc(beer.getUpc());
			}
		}
}
