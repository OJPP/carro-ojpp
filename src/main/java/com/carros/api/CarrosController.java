package com.carros.api;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carros.domain.Carro;
import com.carros.domain.CarroService;
import com.carros.domain.dto.CarroDTO;
import com.carros.utils.HttpUtils;

@RestController
@RequestMapping("/api/v1/carros")
public class CarrosController {

	@Autowired
	private CarroService service;

	@GetMapping
	public ResponseEntity<List<CarroDTO>> get() {
		return ResponseEntity.ok(service.getCarros());
	}

	@GetMapping("/{id}")
	public ResponseEntity<CarroDTO> get(@PathVariable Long id) {
		return ResponseEntity.ok(service.getCarroById(id));
	}

	@GetMapping("/tipo/{tipo}")
	public ResponseEntity<List<CarroDTO>> getCarrosByTipo(@PathVariable String tipo) {

		List<CarroDTO> carros = service.getCarrosByTipo(tipo);

		return carros.isEmpty() ?
				ResponseEntity.noContent().build() :
				ResponseEntity.ok(carros);
	}

	@PostMapping
	public ResponseEntity<String> post(@RequestBody Carro carro) {
		CarroDTO carroDTO = service.insert(carro);
		URI location = (new HttpUtils()).getUri(carroDTO.getId());
		return ResponseEntity.created(location).build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<CarroDTO> put(@PathVariable Long id, @RequestBody Carro carro) {
		carro.setId(id);

		CarroDTO carroDTO = service.update(carro, id);

		return carroDTO != null ?
				ResponseEntity.ok(carroDTO) :
				ResponseEntity.notFound().build();

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.ok().build();
	}
}
