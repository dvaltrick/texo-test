package br.com.dvaltrick.cities.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.dvaltrick.cities.models.City;
import br.com.dvaltrick.cities.models.State;


@Repository
public interface StateRepository extends JpaRepository<State, Integer> {
	@Query("SELECT A FROM State A " +
           " WHERE A.name = :name")
	public State findByName(@Param("name") String name);

	@Query("SELECT A.capital FROM State A " +
	       " ORDER BY A.capital.name")
	public List<City> getAllCapitals();
	
}
