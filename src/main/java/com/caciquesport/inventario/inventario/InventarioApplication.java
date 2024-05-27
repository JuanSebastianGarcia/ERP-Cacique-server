package com.caciquesport.inventario.inventario;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@SpringBootApplication
public class InventarioApplication {

	public static void main(String[] args) {
		//SpringApplication.run(InventarioApplication.class, args);
		
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String passwordEncrypt=encoder.encode("admin");

        System.out.println(passwordEncrypt);
	}

}
