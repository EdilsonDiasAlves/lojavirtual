package com.mz.lojavirtual.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mz.lojavirtual.domain.Cidade;
import com.mz.lojavirtual.repositories.CidadeRepository;

@Service
public class CidadeService {

	@Autowired
	private CidadeRepository cidadeRepo;
	
	public List<Cidade> findCidades(Integer estadoId) {
		return cidadeRepo.findCidades(estadoId);
	}
}
