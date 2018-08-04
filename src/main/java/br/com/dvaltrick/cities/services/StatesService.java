package br.com.dvaltrick.cities.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dvaltrick.cities.models.City;
import br.com.dvaltrick.cities.models.State;
import br.com.dvaltrick.cities.repositories.StateRepository;


@Service
public class StatesService {
	@Autowired
	private StateRepository repository;
	
	public State addIfNew(String toAdd) throws Exception{
		try{
			State found = repository.findByName(toAdd);
			
			if(found == null){
				return repository.save(new State(toAdd));
			}
			
			return found;
		}catch(Exception e){
			throw new Exception("Não foi possível criar ou encontrar o estado");
		}
	}
	
	public State save(State toSave) throws Exception{
		try{
			return repository.save(toSave);
		}catch(Exception e){
			throw new Exception("Não foi possível salvar o estado " + e.getMessage());
		}
	}
	
	public List<City> getAllCapitals(){
		return repository.getAllCapitals();
	}
}
