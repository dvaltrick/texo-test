package br.com.dvaltrick.cities.models;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class City {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	private Integer ibgeId; 
	
	@ManyToOne(fetch=FetchType.EAGER, targetEntity=State.class)
	@JoinColumn(name="uf_id", nullable=true, referencedColumnName="id")
	private State uf;
	
	private String name;
	
	private String longitude;
	
	private String latitude;
	
	private String noAccents;
	
	private String alternativeNames;
	
	@ManyToOne(fetch=FetchType.EAGER, targetEntity=Microregion.class)
	@JoinColumn(name="microregion_id", nullable=true, referencedColumnName="id")
	private Microregion microregion;

	@ManyToOne(fetch=FetchType.EAGER, targetEntity=Mesoregion.class)
	@JoinColumn(name="mesoregion_id", nullable=true, referencedColumnName="id")
	private Mesoregion mesoregion;
	
	
	public City(){}
	

	public static class Builder{
		private Integer ibgeId; 
		private State uf;
		private String name;
		private String longitude;
		private String latitude;
		private String noAccents;
		private String alternativeNames;
		private Microregion microregion;
		private Mesoregion mesoregion;
		
		public Builder(){}
		
		public Builder withIBGE(Integer value){
			ibgeId = value;
			return this;
		}
		
		public Builder atUF(State value){
			uf = value;
			return this;
		}
		
		public Builder withName(String value){
			name = value;
			return this;
		}
		
		public Builder atLongitude(String value){
			longitude = value;
			return this;
		}
		
		public Builder atLatitude(String value){
			latitude = value;
			return this;
		}
		
		public Builder withNoAccentsName(String value){
			noAccents = value;
			return this;
		}
		
		public Builder withAlternativeNames(String value){
			alternativeNames = value;
			return this;
		}
		
		public Builder atMicroregion(Microregion value){
			microregion = value;
			return this;
		}
		
		public Builder atMesoregion(Mesoregion value){
			mesoregion = value;
			return this;
		}
		
		public City build(){
			return new City(this);
		}
			
	}
	
	public City(Builder builder){
		ibgeId = builder.ibgeId;
		uf = builder.uf;
		name = builder.name;
		longitude = builder.longitude;
		latitude = builder.latitude;
		noAccents = builder.noAccents;
		alternativeNames = builder.alternativeNames;
		microregion = builder.microregion;
		mesoregion = builder.mesoregion;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIbgeId() {
		return ibgeId;
	}

	public void setIbgeId(Integer ibgeId) {
		this.ibgeId = ibgeId;
	}

	public State getUf() {
		return uf;
	}

	public void setUf(State uf) {
		this.uf = uf;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getNoAccents() {
		return noAccents;
	}

	public void setNoAccents(String noAccents) {
		this.noAccents = noAccents;
	}

	public String getAlternativeNames() {
		return alternativeNames;
	}

	public void setAlternativeNames(String alternativeNames) {
		this.alternativeNames = alternativeNames;
	}

	public Microregion getMicroregion() {
		return microregion;
	}

	public void setMicroregion(Microregion microregion) {
		this.microregion = microregion;
	}

	public Mesoregion getMesoregion() {
		return mesoregion;
	}

	public void setMesoregion(Mesoregion mesoregion) {
		this.mesoregion = mesoregion;
	}
	
	
	
}
