package br.com.dvaltrick.cities.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dvaltrick.cities.enums.RegionType;
import br.com.dvaltrick.cities.models.Mesoregion;
import br.com.dvaltrick.cities.repositories.RegionRepository;

@Service
public class MesoregionService {
	@Autowired
	private RegionRepository<Mesoregion> repository;
	
	public Mesoregion addIfNew(String toAdd, RegionType type) throws Exception{
		try{
			Mesoregion found = repository.FindByName(toAdd, type);
			
			if(found == null){
				return repository.save(new Mesoregion(toAdd, type));
			}
			
			return found;
		}catch(Exception e){
			throw new Exception("Não foi possível criar ou encontrar o estado");
		}
	}
}
