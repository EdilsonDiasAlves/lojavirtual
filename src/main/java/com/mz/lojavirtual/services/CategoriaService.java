package com.mz.lojavirtual.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mz.lojavirtual.domain.Categoria;
import com.mz.lojavirtual.repositories.CategoriaRepository;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository categoriaRepo;

	public Categoria buscarPorId(Integer id) {
		Optional<Categoria> obj = categoriaRepo.findById(id);
		return obj.orElse(null);
	}
}
