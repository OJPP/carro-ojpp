package com.carros.domain;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import org.springframework.util.Assert;
import org.springframework.util.Assert;

import com.carros.domain.dto.CarroDTO;

@Service
public class CarroService {

	@Autowired
	private CarroRepository rep;

	public List<CarroDTO> getCarros() {
		return rep
				.findAll()
				.stream()
				.map(CarroDTO::create)
				.collect(Collectors.toList());
	}

	public Optional<CarroDTO> getCarroById(Long id) {
		return rep
				.findById(id)
				.map(CarroDTO::create);
	}

	public List<CarroDTO> getCarrosByTipo(String tipo) {

		return rep
				.findByTipo(tipo)
				.stream()
				.map(CarroDTO::create)
				.collect(Collectors.toList());

	}

	public CarroDTO insert(Carro carro) {
		Assert.isTrue(carro.getId() == 0, "Não foi possível inserir o registo");

		return CarroDTO.create(rep.save(carro));
	}

	public CarroDTO update(Carro carro, Long id) {
		Assert.notNull(id, "Não foi possivel actualizar o registo");

		// Busca o carro na base de dados
		Optional<Carro> carroOptional = rep.findById(id);

		if (carroOptional.isPresent()) {
			Carro carroDB = carroOptional.get();

			// Copiar as propriedades
			carroDB.setNome(carro.getNome());
			carroDB.setTipo(carro.getTipo());
			System.out.println("Carro id " + carroDB.getId());

			// Actualiza o carro
			rep.save(carroDB);

			return CarroDTO.create(carroDB);
		} else {
			return null;
			// throw new RuntimeException("Não foi possivel actualizar o registo");
		}

	}

	public boolean delete(Long id) {
		if (this.getCarroById(id).isPresent()) {
			rep.deleteById(id);
			return true;
		}
		return false;
	}

}
