package com.mz.lojavirtual.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mz.lojavirtual.domain.Pedido;
import com.mz.lojavirtual.repositories.PedidoRepository;
import com.mz.lojavirtual.services.exceptions.ObjectNotFoudException;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository pedidoRepo;

	public Pedido find(Integer id) {
		Optional<Pedido> obj = pedidoRepo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoudException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}
}
