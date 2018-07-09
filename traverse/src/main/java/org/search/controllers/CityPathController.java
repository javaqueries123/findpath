package org.search.controllers;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Optional;

import org.search.models.Node;
import org.search.utils.FileLoaderBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CityPathController {
	
	
	@Autowired
	private FileLoaderBean fileLoaderBean;
	
	@RequestMapping("/searchPath")
	public String citiesConnected(@RequestParam(value="origin") Optional<String> origin, @RequestParam(value="destination") Optional<String> destination) {
		String result="No";
		
		result =fileLoaderBean.hasPathBetweenOriginAndDestination(origin.toString().toLowerCase(), destination.toString().toLowerCase());
		
		return result;
	}
	
	 
}
