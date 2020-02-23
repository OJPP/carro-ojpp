package com.carros.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

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

	public Carro insert(Carro carro) {
		return rep.save(carro);
	}

	public Carro update(Carro carro, Long id) {

		Assert.notNull(id, "Não foi possivel actualizar o registo");

//		// Busca o carro na base de dados
//        Optional<Carro> carroOptional = rep.findById(id);
//
//		if(carroOptional.isPresent()) {
//			Carro carroDB = carroOptional.get();
//
//			// Copiar as propriedades
//			carroDB.setNome(carro.getNome());
//			carroDB.setTipo(carro.getTipo());
//			System.out.println("Carro id " + carroDB.getId());
//			
//			//Actualiza o carro
//			rep.save(carroDB);
//
//			return carroDB;
//		} else {
//			throw new RuntimeException("Não foi possivel actualizar o registo");
//		}

		this.getCarroById(id).map( carroDB -> {
			// Copiar as propriedades
			carroDB.setNome(carro.getNome());
			carroDB.setTipo(carro.getTipo());
			System.out.println("Carro id " + carroDB.getId());
			
			//Actualiza o carro
			rep.save(carroDB);

			return carroDB;
		}).orElseThrow( () -> new RuntimeException("Não foi possivel actualizar o registo"));

		return null;
	}

	public List<Carro> getCarrosFake() {
		List<Carro> carros = new ArrayList<Carro>();
		
		carros.add(new Carro(1L, "Fusca"));
		carros.add(new Carro(2L, "Brasilia"));
		carros.add(new Carro(3L, "Chevette"));

		return carros;
	}
}
