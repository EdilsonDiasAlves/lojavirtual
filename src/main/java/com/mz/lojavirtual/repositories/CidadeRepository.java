package com.mz.lojavirtual.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mz.lojavirtual.domain.Cidade;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Integer> {

	@Query("SELECT c FROM Cidade c WHERE c.estado.id = :estadoId ORDER BY c.nome")
	public List<Cidade> findCidades(Integer estadoId);
}
