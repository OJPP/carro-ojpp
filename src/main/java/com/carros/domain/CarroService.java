package com.carros.domain;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
				.map(CarroDTO::new)
				.collect(Collectors.toList());

//		List<Carro> carros = rep.findAll();

//		List<CarroDTO> carrosDTO = carros
//				.stream()
//				.map(CarroDTO::new)
//				.collect(Collectors.toList());

//		List<CarroDTO> carrosDTO = carros
//				.stream()
//				.map( carro -> new CarroDTO(carro))
//				.collect(Collectors.toList());

//		List<CarroDTO> carrosDTO = new ArrayList<CarroDTO>();
//		for(Carro carro: carros) {
//			carrosDTO.add(new CarroDTO(carro));
//		}

//		return carrosDTO;
	}

	public Optional<Carro> getCarroById(Long id) {
		return rep.findById(id);
	}

	public List<CarroDTO> getCarrosByTipo(String tipo) {

		return rep
				.findByTipo(tipo)
				.stream()
				.map(CarroDTO::new)
				.collect(Collectors.toList());

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

	public void delete(Long id) {
		rep.deleteById(id);
	}

//	public List<Carro> getCarrosFake() {
//		List<Carro> carros = new ArrayList<Carro>();
//		
//		carros.add(new Carro(1L, "Fusca"));
//		carros.add(new Carro(2L, "Brasilia"));
//		carros.add(new Carro(3L, "Chevette"));
//
//		return carros;
//	}
}
