package com.carros;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.carros.domain.Carro;
import com.carros.domain.CarroService;
import com.carros.domain.dto.CarroDTO;

@SpringBootTest
class CarrosApplicationTests {

	@Autowired
	private CarroService carroService;

	@Test
	public void testSave() {

		Carro carro = new Carro();
		carro.setNome("Ferrari");
		carro.setTipo("esportivo");

		CarroDTO carroDTO = carroService.insert(carro);
		assertNotNull(carroDTO);
		
		Long id = carroDTO.getId();
		assertNotNull(id);

		// Ler o carro da base de dados
		Optional<CarroDTO> carroDTOBd = carroService.getCarroById(id);
		assertTrue(carroDTOBd.isPresent());
		
		carroDTO = carroDTOBd.get();
		assertEquals("Ferrari", carroDTO.getNome());
		assertEquals("esportivo", carroDTO.getTipo());
		
		// Eliminar o carro da base de dados
		carroService.delete(id);

		// Verificar se o carro foi eliminado da base de dados
		assertFalse(carroService.getCarroById(id).isPresent());
	}

	@Test
	public void testLista() {
		
		List<CarroDTO> carros = carroService.getCarros();
		assertEquals(30, carros.size());
	}

	@Test
	public void testListaPorTipo() {
		
		assertEquals(10, carroService.getCarrosByTipo("classicos").size());
		assertEquals(10, carroService.getCarrosByTipo("esportivos").size());
		assertEquals(10, carroService.getCarrosByTipo("luxo").size());

		assertEquals(0, carroService.getCarrosByTipo("x").size());

	}

	@Test
	public void testGet() {

		Optional<CarroDTO> carroDTOOp = carroService.getCarroById(11L);
		assertTrue(carroDTOOp.isPresent());
		
		CarroDTO carroDTOBd = carroDTOOp.get();
		assertEquals("Ferrari FF", carroDTOBd.getNome());
	}

}
