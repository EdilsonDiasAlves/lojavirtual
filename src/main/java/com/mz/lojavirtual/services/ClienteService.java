package com.mz.lojavirtual.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mz.lojavirtual.domain.Cliente;
import com.mz.lojavirtual.repositories.ClienteRepository;
import com.mz.lojavirtual.services.exceptions.ObjectNotFoudException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository clienteRepo;

	public Cliente buscarPorId(Integer id) {
		Optional<Cliente> obj = clienteRepo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoudException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}
}
