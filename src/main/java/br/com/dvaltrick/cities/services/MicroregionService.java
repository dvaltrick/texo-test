package br.com.dvaltrick.cities.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dvaltrick.cities.enums.RegionType;
import br.com.dvaltrick.cities.models.Microregion;
import br.com.dvaltrick.cities.repositories.RegionRepository;

@Service
public class MicroregionService {
	@Autowired
	private RegionRepository<Microregion> repository;
	
	public Microregion addIfNew(String toAdd, RegionType type) throws Exception{
		try{
			Microregion found = repository.FindByName(toAdd, type);
			
			if(found == null){
				return repository.save(new Microregion(toAdd, type));
			}
			
			return found;
		}catch(Exception e){
			throw new Exception("Não foi possível criar ou encontrar o estado " + e.getMessage());
		}
	}
}
