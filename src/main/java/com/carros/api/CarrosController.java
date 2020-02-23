package com.carros.api;

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

@RestController
@RequestMapping("/api/v1/carros")
public class CarrosController {

	@Autowired
	private CarroService service;

	@GetMapping
	public ResponseEntity<Iterable<Carro>> get() {
		// return new ResponseEntity<Iterable<Carro>>(service.getCarros(), HttpStatus.OK);
		// Atalho (é identico ao comando return definido acima):
		return ResponseEntity.ok(service.getCarros());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Carro> get(@PathVariable Long id) {

		return service.getCarroById(id)
				.map( ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());

//		return service.getCarroById(id)
//				.map( carro -> ResponseEntity.ok(carro))
//				.orElse(ResponseEntity.notFound().build());

//		Optional<Carro> carroOpcional = service.getCarroById(id);

//		return carroOpcional.isPresent() ?
//				ResponseEntity.ok(carroOpcional.get()) :
//				ResponseEntity.notFound().build();

//		if(carroOpcional.isPresent()) {
//			return ResponseEntity.ok(carroOpcional.get());
//		} else {
//			return ResponseEntity.notFound().build();
//		}

	}

	@GetMapping("/tipo/{tipo}")
	public Iterable<Carro> getCarrosByTipo(@PathVariable String tipo) {
		return service.getCarrosByTipo(tipo);
	}

	@PostMapping
	public String post(@RequestBody Carro carro) {
		Carro c = service.insert(carro);
		return "Carro salvo com sucesso: " + c.getId();
	}

	@PutMapping("/{id}")
	public String put(@PathVariable Long id, @RequestBody Carro carro) {
		Carro c = service.update(carro, id);
		return "Carro actualizado com sucesso: " + c.getId();
	}

	@DeleteMapping("/{id}")
	public String delete(@PathVariable Long id) {
		service.delete(id);
		return "Carro eliminado com sucesso: " + id;
	}
}
