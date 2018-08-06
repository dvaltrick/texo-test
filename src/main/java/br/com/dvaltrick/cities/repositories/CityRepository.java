package br.com.dvaltrick.cities.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.dvaltrick.cities.models.City;

@Repository
public interface CityRepository extends JpaRepository<City, Integer>{
	@Query("SELECT DISTINCT new map(A.uf.name as uf, count(*) as quantidade) FROM City A " +
		   " GROUP BY A.uf.name " +
		   " ORDER BY 2 ")
	public List<?> getCitiesPerState();
	
	@Query("SELECT A FROM City A " +
	       "  JOIN FETCH A.uf " +
	       "  JOIN FETCH A.microregion " +
	       "  JOIN FETCH A.mesoregion " +
	       " WHERE (:ibge is null OR A.ibgeId = :ibge) " +
		   "   AND (:uf is null OR A.uf.name = :uf) " +
	       "   AND (:name is null OR A.name = :name) " +
		   "   AND (:lon is null OR A.longitude = :lon) " +
	       "   AND (:lat is null OR A.latitude = :lat) " +
	       "   AND (:noaccent is null OR A.noAccents = :noaccent) " +
	       "   AND (:alternative is null OR A.alternativeNames = :alternative) " +
	       "   AND (:microregion is null OR A.microregion.name = :microregion) " +
	       "   AND (:mesoregion is null OR A.mesoregion.name = :mesoregion) " )
	public List<City> filter(@Param("ibge") Integer ibge,
							 @Param("uf") String uf,
							 @Param("name") String name,
							 @Param("lon") String lon,
							 @Param("lat") String lat,
							 @Param("noaccent") String noAccent, 
							 @Param("alternative") String alternative, 
							 @Param("microregion") String microregion, 
							 @Param("mesoregion") String mesoregion);
	
	@Transactional
	public void deleteById(Integer id);
}
