package com.mz.lojavirtual.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mz.lojavirtual.domain.Cliente;
import com.mz.lojavirtual.services.ClienteService;

@RestController
@RequestMapping(value="/clientes")
public class ClienteController {
	
	@Autowired
	private ClienteService clienteService;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
		
		Cliente cliente = clienteService.buscarPorId(id);
		return ResponseEntity.ok().body(cliente);
	}
}
