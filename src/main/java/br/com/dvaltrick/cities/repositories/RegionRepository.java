package br.com.dvaltrick.cities.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.dvaltrick.cities.enums.RegionType;
import br.com.dvaltrick.cities.models.Region;

@Repository
public interface RegionRepository<T extends Region> extends CrudRepository<T, Integer> {
	@Query("SELECT A FROM #{#entityName} A " +
		       " WHERE A.name like :name " +
			   "   AND A.type = :type ")
	public T FindByName(@Param("name") String name,
						@Param("type") RegionType type);

}
