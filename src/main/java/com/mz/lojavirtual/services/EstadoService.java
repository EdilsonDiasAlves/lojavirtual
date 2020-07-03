package com.mz.lojavirtual.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mz.lojavirtual.domain.Estado;
import com.mz.lojavirtual.repositories.EstadoRepository;

@Service
public class EstadoService {

	@Autowired
	private EstadoRepository estadoRepo;
	
	public List<Estado> findAll() {
		return estadoRepo.findAllByOrderByNome();
	}
}
