package br.com.dvaltrick.cities.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;

import br.com.dvaltrick.cities.enums.RegionType;


@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public class Region {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	private String name;
	
	private RegionType type;
	
	public Region(){}
	
	public Region(String name, RegionType type){
		this.name = name;
		this.type = type;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public RegionType getType() {
		return type;
	}

	public void setType(RegionType type) {
		this.type = type;
	}

}
