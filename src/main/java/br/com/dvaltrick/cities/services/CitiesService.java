package br.com.dvaltrick.cities.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import br.com.dvaltrick.cities.enums.RegionType;
import br.com.dvaltrick.cities.models.City;
import br.com.dvaltrick.cities.models.Mesoregion;
import br.com.dvaltrick.cities.models.Microregion;
import br.com.dvaltrick.cities.models.State;
import br.com.dvaltrick.cities.repositories.CityRepository;

@Service
public class CitiesService {
	@Autowired
	private CityRepository repository;
	
	@Autowired
	private StatesService statesServices;
	
	@Autowired
	private MesoregionService mesoService;
	
	@Autowired
	private MicroregionService microService;
	
	public void cityBuilder(String[] data) throws Exception{
		try{
			State state = statesServices.addIfNew(data[1]);
			Microregion micro = microService.addIfNew(data[8], RegionType.Microregion);
			Mesoregion meso = mesoService.addIfNew(data[9], RegionType.Mesoregion);
			
			City city = new City();
			city.setIbgeId(Integer.parseInt(data[0]));
			city.setUf(state);
			city.setName(data[2]);
			city.setLongitude(data[4]);
			city.setLatitude(data[5]);
			city.setNoAccents(data[6]);
			city.setAlternativeNames(data[7]);
			city.setMicroregion(micro);
			city.setMesoregion(meso);
			
			add(city);
			
			if(!data[3].trim().isEmpty()){
				Boolean isCapital = Boolean.valueOf(data[3].trim());
				if(isCapital){
					state.setCapital(city);
					statesServices.save(state);
				}				
			}
		}catch(Exception e){
			throw new Exception("Não foi possível criar a cidade " + e.getMessage());
		}
	}
	
	public City add(City toAdd) throws Exception{
		try{
			return repository.save(toAdd);
		}catch(Exception e){
			throw new Exception(e.getMessage());
		}
	}
	
	public Map<String, Object> getMoreAndLess(){
		Map<String, Object> result = new HashMap<String, Object>();
		
		List<?> states = repository.getCitiesPerState();
		result.put("more", states.get(states.size()-1));
		result.put("less", states.get(0));
		
		return result;
	}
	
	public Boolean isDatabaseEmpty(){
		return repository.findAll().isEmpty();
	}
	
	public Page<City> getPage(Integer page) {
		return repository.findAll(PageRequest.of(page, 50));
	}
	
	public void delete(Integer id) throws Exception{
		try{
			City found = repository.getOne(id);
			if(found != null){
				repository.delete(found);	
			}
		}catch(Exception e){
			throw new Exception("Falha na deleção");
		}
	}
	
	public List<City> filter(Integer ibge, String uf, String name, String lon, String lat, String noAccent, 
			                 String alternative, String microregion, String mesoregion){
		return repository.filter(ibge, uf, name, lon, lat, noAccent, alternative, microregion, mesoregion);
	}
	
}
