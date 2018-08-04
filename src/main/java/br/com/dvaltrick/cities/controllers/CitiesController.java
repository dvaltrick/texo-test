package br.com.dvaltrick.cities.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.dvaltrick.cities.models.City;
import br.com.dvaltrick.cities.services.CitiesService;

@CrossOrigin(origins="*")
@RestController
public class CitiesController {
	@Autowired
	private CitiesService service;
	
	@RequestMapping(value="/api/moreandless",
			method=RequestMethod.GET,
			produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> getMoreAndLess(){
		try{
			return new ResponseEntity<Map<String, Object>>(service.getMoreAndLess(), HttpStatus.OK);
		}catch(Exception e){
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);	
		}
	}
	
	@RequestMapping(value="/api/cities/{page}",
			method=RequestMethod.GET,
			produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> getCitiesPage(@PathVariable("page") Integer page){
		try{
			return new ResponseEntity<List<City>>(service.getPage(page-1).getContent(),HttpStatus.OK);
		}catch(Exception e){
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);	
		}
	}
	
	@RequestMapping(value="/api/cities",
			method=RequestMethod.GET,
			produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> getCities(@RequestParam(value = "ibge_id", required = false) Integer ibge,
									   @RequestParam(value = "uf", required = false) String uf,
									   @RequestParam(value = "name", required = false) String name,
									   @RequestParam(value = "lon", required = false) String lon,
									   @RequestParam(value = "lat", required = false) String lat,
									   @RequestParam(value = "no_accent", required = false) String noAccent,
									   @RequestParam(value = "alternative_names", required = false) String alternatives,
									   @RequestParam(value = "microregion", required = false) String microregion,
									   @RequestParam(value = "mesoregion", required = false) String mesoregion){
		try{
			return new ResponseEntity<List<City>>(service.filter(ibge, uf, name, lon, lat, noAccent, 
																 alternatives, microregion, mesoregion),HttpStatus.OK);
		}catch(Exception e){
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);	
		}
	}
	
	@RequestMapping(value="/api/deletecity/{id}",
	        method=RequestMethod.DELETE,
	        produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<String> deleteCity(@PathVariable("id") Integer id){
		try{
			service.delete(id);
			return new ResponseEntity<>("OK!",HttpStatus.OK);
		}catch(Exception e){
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
}
