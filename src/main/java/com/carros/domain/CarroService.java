package com.carros.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class CarroService {

	public List<Carro> getCarros() {
		List<Carro> carros = new ArrayList<Carro>();
		
		carros.add(new Carro(1L, "Fusca"));
		carros.add(new Carro(2L, "Brasilia"));
		carros.add(new Carro(3L, "Chevette"));

		return carros;
	}
}
