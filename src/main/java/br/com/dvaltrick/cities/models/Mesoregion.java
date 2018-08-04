package br.com.dvaltrick.cities.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import br.com.dvaltrick.cities.enums.RegionType;


@Entity
public class Mesoregion extends Region {

	@OneToMany(mappedBy="mesoregion", fetch=FetchType.LAZY)
	@Cascade({CascadeType.ALL})
	@JsonManagedReference(value="mesoregion")
	private Set<City> cities = new HashSet<City>();
	
	public Mesoregion(){}
	
	public Mesoregion(String name, RegionType type) {
		super(name, type);
	}

	public Set<City> getCities() {
		return cities;
	}

	public void setCities(Set<City> cities) {
		this.cities = cities;
	}
	
	

}
