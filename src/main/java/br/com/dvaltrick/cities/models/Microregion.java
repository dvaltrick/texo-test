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
public class Microregion extends Region{
	
	@OneToMany(mappedBy="microregion", fetch=FetchType.LAZY)
	@Cascade({CascadeType.ALL})
	@JsonManagedReference(value="microregion")
	private Set<City> cities = new HashSet<City>();

	public Microregion(){}
	
	public Microregion(String name, RegionType type) {
		super(name, type);
		// TODO Auto-generated constructor stub
	}

	public Set<City> getCities() {
		return cities;
	}

	public void setCities(Set<City> cities) {
		this.cities = cities;
	}
	
	
}
