package br.com.dvaltrick.cities.services;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataService {
	@Autowired
	private CitiesService citiesService;
	
	public void load() throws Exception{
		if(citiesService.isDatabaseEmpty()){
			ClassLoader classLoader = ClassLoader.getSystemClassLoader();
			File file = new File(classLoader.getResource("cidades.csv").getFile());

			try (Scanner scanner = new Scanner(file)) {
				scanner.nextLine();
				while (scanner.hasNextLine()) {
					String line = scanner.nextLine();
					String[] data = line.split(",");
					citiesService.cityBuilder(data);
				}

				scanner.close();

			} catch (IOException e) {
				e.printStackTrace();
			}			
		}

	}
}
