package com.mz.lojavirtual.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mz.lojavirtual.domain.Pedido;
import com.mz.lojavirtual.services.PedidoService;

@RestController
@RequestMapping(value="/pedidos")
public class PedidoController {
	
	@Autowired
	private PedidoService pedidoService;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Pedido> findById(@PathVariable Integer id) {
		Pedido pedido = pedidoService.findById(id);
		return ResponseEntity.ok().body(pedido);
	}
}
