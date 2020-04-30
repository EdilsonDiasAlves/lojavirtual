package com.mz.lojavirtual.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mz.lojavirtual.domain.Categoria;
import com.mz.lojavirtual.repositories.CategoriaRepository;
import com.mz.lojavirtual.services.exceptions.ObjectNotFoudException;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository categoriaRepo;

	public Categoria buscarPorId(Integer id) {
		Optional<Categoria> obj = categoriaRepo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoudException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	}

	public Categoria inserir(Categoria categoria) {
		categoria.setId(null);
		return categoriaRepo.save(categoria);
	}
}
