package br.com.dvaltrick.cities.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
			
			City city = new City.Builder().withIBGE(Integer.parseInt(data[0]))
										  .atUF(state)
										  .withName(data[2])
										  .atLongitude(data[4])
										  .atLatitude(data[5])
										  .withNoAccentsName(data[6])
										  .withAlternativeNames(data[7])
										  .atMicroregion(micro)
										  .atMesoregion(meso)
										  .build();
			
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
			City found = repository.findById(id).get();
			if(found != null){
				State ufFound = found.getUf();
				if(ufFound.getCapital().getId() == found.getId()){
					ufFound.setCapital(null);
					statesServices.save(ufFound);
				}
				
				found.getUf().getCities().remove(found);
				found.getMesoregion().getCities().remove(found);
				found.getMicroregion().getCities().remove(found);
				
				repository.delete(found);					
			}
		}catch(Exception e){
			throw new Exception("Falha na deleção");
		}
	}
	
	public Map<String, Object> filter(Integer ibge, String uf, String name, String lon, String lat, String noAccent, 
			                 String alternative, String microregion, String mesoregion){
		List<City> cities = repository.filter(ibge, uf, name, lon, lat, noAccent, alternative, microregion, mesoregion);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("lista", cities);
		result.put("quantidade", cities.size());
		
		return result;
		
		
	}
	
}
