package org.search.controllers;

import java.util.Optional;

import org.search.utils.FileLoaderBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchController {

	private static final Logger logger= LoggerFactory.getLogger(SearchController.class);

	@Autowired
	private FileLoaderBean fileLoaderBean;
	
	@RequestMapping("/connected")
	public String citiesConnected(@RequestParam(value="origin") Optional<String> origin, @RequestParam(value="destination") Optional<String> destination) {
		String result="No";
		
		String originCity=(origin.isPresent() ? origin.get().toLowerCase(): null);
		String destinationCity= (destination.isPresent() ? destination.get().toLowerCase(): null);
		
		result =fileLoaderBean.hasPathBetweenOriginAndDestination(originCity, destinationCity);
		logger.debug(" 1--> originCity  : "+originCity+" destinationCity  :"+destinationCity+"  result "+result);
		if(result.equalsIgnoreCase("No")) {
			result =fileLoaderBean.hasPathBetweenOriginAndDestination(destinationCity,originCity);
		}
		
		logger.debug(" 2--> originCity  : "+originCity+" destinationCity  :"+destinationCity+"  result "+result);
		return result;
	}
}
