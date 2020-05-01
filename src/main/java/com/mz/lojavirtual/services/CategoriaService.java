package com.mz.lojavirtual.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.mz.lojavirtual.domain.Categoria;
import com.mz.lojavirtual.repositories.CategoriaRepository;
import com.mz.lojavirtual.services.exceptions.DataIntegrityException;
import com.mz.lojavirtual.services.exceptions.ObjectNotFoudException;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository categoriaRepo;

	public Categoria find(Integer id) {
		Optional<Categoria> obj = categoriaRepo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoudException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	}

	public Categoria insert(Categoria categoria) {
		categoria.setId(null);
		return categoriaRepo.save(categoria);
	}

	public Categoria update(Categoria categoria) {
		find(categoria.getId());
		return categoriaRepo.save(categoria);
	}

	public void delete(Integer id) {
		find(id);
		try {
			categoriaRepo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos");
		}
	}
}
