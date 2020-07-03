package com.mz.lojavirtual.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mz.lojavirtual.domain.Cidade;
import com.mz.lojavirtual.domain.Estado;
import com.mz.lojavirtual.dto.CidadeDTO;
import com.mz.lojavirtual.dto.EstadoDTO;
import com.mz.lojavirtual.services.CidadeService;
import com.mz.lojavirtual.services.EstadoService;

@RestController
@RequestMapping(value="/estados")
public class EstadoController {
	
	@Autowired
	private EstadoService estadoService;
	
	@Autowired
	private CidadeService cidadeService;
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<EstadoDTO>> findAll() {
		List<Estado> estados = estadoService.findAll();
		List<EstadoDTO> estadosDTO = estados.stream().map(e -> new EstadoDTO(e)).collect(Collectors.toList());
		return ResponseEntity.ok().body(estadosDTO);
	}
	
	@RequestMapping(value="/{id}/cidades", method=RequestMethod.GET)
	public ResponseEntity<List<CidadeDTO>> findAll(@PathVariable("id") Integer estadoId) {
		List<Cidade> cidades = cidadeService.findCidades(estadoId);
		List<CidadeDTO> cidadesDTO = cidades.stream().map(e -> new CidadeDTO(e)).collect(Collectors.toList());
		return ResponseEntity.ok().body(cidadesDTO);
	}
}
