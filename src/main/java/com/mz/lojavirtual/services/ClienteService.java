package com.mz.lojavirtual.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.mz.lojavirtual.domain.Cliente;
import com.mz.lojavirtual.dto.ClienteDTO;
import com.mz.lojavirtual.repositories.ClienteRepository;
import com.mz.lojavirtual.services.exceptions.DataIntegrityException;
import com.mz.lojavirtual.services.exceptions.ObjectNotFoudException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository clienteRepo;

	public Cliente find(Integer id) {
		Optional<Cliente> obj = clienteRepo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoudException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}
	
	public Cliente insert(Cliente cliente) {
		cliente.setId(null);
		return clienteRepo.save(cliente);
	}

	public Cliente update(Cliente cliente) {
		Cliente novoCliente = find(cliente.getId());
		updateData(novoCliente, cliente);
		return clienteRepo.save(novoCliente);
	}

	public void delete(Integer id) {
		find(id);
		try {
			clienteRepo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir um cliente com entidades relacionadas");
		}
	}

	public List<Cliente> findAll() {
		return clienteRepo.findAll();
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return clienteRepo.findAll(pageRequest);
	}
	
	public Cliente fromDTO(ClienteDTO clienteDTO) {
		return new Cliente(clienteDTO.getId(), clienteDTO.getNome(), clienteDTO.getEmail(), null, null);
	}
	
	private void updateData(Cliente novoCliente, Cliente cliente) {
		novoCliente.setNome(cliente.getNome());
		novoCliente.setEmail(cliente.getEmail());
	}
}
