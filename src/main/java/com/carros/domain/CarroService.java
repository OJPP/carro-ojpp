package com.carros.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarroService {

	@Autowired
	private CarroRepository rep;

	public Iterable<Carro> getCarros() {
		return rep.findAll();
	}

	public Optional<Carro> getCarroById(Long id) {
		return rep.findById(id);
	}

	public Iterable<Carro> getCarrosByTipo(String tipo) {
		return rep.findByTipo(tipo);
	}

	public Carro save(Carro carro) {
		return rep.save(carro);
	}

	public List<Carro> getCarrosFake() {
		List<Carro> carros = new ArrayList<Carro>();
		
		carros.add(new Carro(1L, "Fusca"));
		carros.add(new Carro(2L, "Brasilia"));
		carros.add(new Carro(3L, "Chevette"));

		return carros;
	}
}
