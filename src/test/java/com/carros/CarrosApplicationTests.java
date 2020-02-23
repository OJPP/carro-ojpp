package com.carros;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.carros.domain.Carro;
import com.carros.domain.CarroService;

@SpringBootTest
class CarrosApplicationTests {

	@Autowired
	private CarroService carroService;

	@Test
	void teste1() {

		Carro carro = new Carro();
		carro.setNome("Ferrari");
		carro.setTipo("esportivo");
		
		carroService.insert(carro);
	}

	@Test
	void teste2() {
	}

}
