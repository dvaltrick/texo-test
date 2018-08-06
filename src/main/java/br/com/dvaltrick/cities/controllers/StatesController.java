package br.com.dvaltrick.cities.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.dvaltrick.cities.models.City;
import br.com.dvaltrick.cities.services.StatesService;

@CrossOrigin(origins="*")
@RestController
public class StatesController {
	@Autowired
	private StatesService service;
	
	@RequestMapping(value="/api/states/capitals",
					method=RequestMethod.GET,
					produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> capitals(){
		try{
			return new ResponseEntity<List<City>>(service.getAllCapitals(), HttpStatus.OK);
		}catch(Exception e){
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);	
		}
		
	}
}
