package com.carros;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.carros.domain.Carro;
import com.carros.domain.dto.CarroDTO;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CarrosApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CarrosApiTest {

	@Autowired
	protected TestRestTemplate testRestTemplate;

	private void deleteCarro(String location) {
        testRestTemplate.withBasicAuth("ojpp", "albatroz").delete(location);
	}

	private ResponseEntity<CarroDTO> saveCarro(String url, Carro carro) {
		return testRestTemplate.withBasicAuth("ojpp", "albatroz").postForEntity(url, carro, null);
	}

	private ResponseEntity<CarroDTO> getCarro(String url) {
		return testRestTemplate.withBasicAuth("ojpp", "albatroz").getForEntity(url, CarroDTO.class);
	}

	private ResponseEntity<List<CarroDTO>> getCarros(String url) {
		return testRestTemplate.withBasicAuth("ojpp", "albatroz").exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<CarroDTO>>() {});
	}

	@Test
	public void testSave() {

        Carro carro = new Carro();
        carro.setNome("Porshe");
        carro.setTipo("esportivos");

        // Insert
        ResponseEntity<CarroDTO> response = this.saveCarro("/api/v1/carros", carro);
        System.out.println(response);

        // Verifica se criou
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        // Buscar o objeto
        String location = response.getHeaders().get("location").get(0);
        CarroDTO c = getCarro(location).getBody();

        assertNotNull(c);
        assertEquals("Porshe", c.getNome());
        assertEquals("esportivos", c.getTipo());

        // Deletar o objeto
        this.deleteCarro(location);

        // Verificar se deletou
        assertEquals(HttpStatus.NOT_FOUND, getCarro(location).getStatusCode());
	}

	@Test
	public void testLista() {

		List<CarroDTO> carros = this.getCarros("/api/v1/carros").getBody();
		assertNotNull(carros);
		assertEquals(30, carros.size());

	}

	@Test
	public void testListaPorTipo() {

		assertEquals(10, this.getCarros("/api/v1/carros/tipo/classicos").getBody().size());
		assertEquals(10, this.getCarros("/api/v1/carros/tipo/esportivos").getBody().size());
		assertEquals(10, this.getCarros("/api/v1/carros/tipo/luxo").getBody().size());

		assertEquals(HttpStatus.NO_CONTENT, this.getCarros("/api/v1/carros/tipo/xxx").getStatusCode());
	}

	@Test
	public void testGetOk() {

		ResponseEntity<CarroDTO> response = this.getCarro("/api/v1/carros/11");
		assertEquals(response.getStatusCode(), HttpStatus.OK);

		CarroDTO carro = response.getBody();
		assertEquals("Ferrari FF", carro.getNome());
	}

	@Test
	public void testGetNotFound() {

		ResponseEntity<CarroDTO> response = this.getCarro("/api/v1/carros/1100");
		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
	}
}
