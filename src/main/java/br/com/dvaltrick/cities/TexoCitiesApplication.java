package br.com.dvaltrick.cities;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.dvaltrick.cities.services.DataService;

@SpringBootApplication
public class TexoCitiesApplication {

	public static void main(String[] args) {
		SpringApplication.run(TexoCitiesApplication.class, args);
		
		DataService data = new DataService();
		try {
			//data.load();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
