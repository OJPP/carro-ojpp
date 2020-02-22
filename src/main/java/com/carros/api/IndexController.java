package com.carros.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class IndexController {

	@GetMapping
	public String get() {
		return "Get Spring Boot!";
	}

	@GetMapping("/login/{login}/senha/{senha}")
	public String get(@PathVariable("login") String login, @PathVariable("senha") String senha) {
		return "Login: " + login + ", senha: " + senha;
	}

	@GetMapping("/carro/{id}")
	public String getCarroById(@PathVariable("id") Long id) {
		return "Carro by id " + id;
	}

	@GetMapping("/carro/tipo/{tipo}")
	public String getCarroByTipo(@PathVariable("tipo") String tipo) {
		return "Lista de Carros " + tipo;
	}
}
