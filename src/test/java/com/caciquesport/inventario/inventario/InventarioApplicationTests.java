package com.caciquesport.inventario.inventario;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.caciquesport.model.configTypes.Talla;

@SpringBootTest
class InventarioApplicationTests {

	@Test
	void contextLoads() {

		Talla talla=new Talla(1,"xl");
	}

}
