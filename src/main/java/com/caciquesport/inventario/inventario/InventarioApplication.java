package com.caciquesport.inventario.inventario;

import java.io.FileNotFoundException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@SpringBootApplication
public class InventarioApplication {

	public static void main(String[] args) throws FileNotFoundException {
		SpringApplication.run(InventarioApplication.class, args);


	}
	
}
