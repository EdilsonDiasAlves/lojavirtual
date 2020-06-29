package com.mz.lojavirtual.services;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mz.lojavirtual.domain.Cidade;
import com.mz.lojavirtual.domain.Cliente;
import com.mz.lojavirtual.domain.Endereco;
import com.mz.lojavirtual.domain.enums.Perfil;
import com.mz.lojavirtual.domain.enums.TipoCliente;
import com.mz.lojavirtual.dto.ClienteCadastroDTO;
import com.mz.lojavirtual.dto.ClienteDTO;
import com.mz.lojavirtual.repositories.ClienteRepository;
import com.mz.lojavirtual.repositories.EnderecoRepository;
import com.mz.lojavirtual.security.UserDetailsImpl;
import com.mz.lojavirtual.services.exceptions.AuthorizationException;
import com.mz.lojavirtual.services.exceptions.DataIntegrityException;
import com.mz.lojavirtual.services.exceptions.ObjectNotFoudException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository clienteRepo;
	
	@Autowired
	private EnderecoRepository enderecoRepo;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private S3Service s3Service;

	public Cliente find(Integer id) {
		
		UserDetailsImpl user = UserService.getAuthenticated();
		if (user == null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso Negado");
		}
		
		Optional<Cliente> cliente = clienteRepo.findById(id);
		return cliente.orElseThrow(() -> new ObjectNotFoudException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}
	
	@Transactional
	public Cliente insert(Cliente cliente) {
		cliente.setId(null);
		cliente = clienteRepo.save(cliente);
		enderecoRepo.saveAll(cliente.getEnderecos());
		return cliente;
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
			throw new DataIntegrityException("Não é possível excluir um cliente com pedidos relacionados");
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
		return new Cliente(clienteDTO.getId(), clienteDTO.getNome(), clienteDTO.getEmail(), null, null, null);
	}
	
	public Cliente fromDTO(ClienteCadastroDTO cliCadDto) {
		Cliente cli = new Cliente(null, cliCadDto.getNome(), cliCadDto.getEmail(), 
				cliCadDto.getCpfOuCnpj(), TipoCliente.toEnum(cliCadDto.getTipo()), passwordEncoder.encode(cliCadDto.getSenha()));
		Cidade cid = new Cidade(cliCadDto.getCidadeId(), null, null);
		Endereco end = new Endereco(null, cliCadDto.getLogradouro(), cliCadDto.getNumero(), 
				cliCadDto.getComplemento(), cliCadDto.getBairro(), cliCadDto.getCep(), cli, cid);
		cli.getEnderecos().add(end);
		cli.getTelefones().add(cliCadDto.getTelefone1());
		
		if (cliCadDto.getTelefone2() != null) {
			cli.getTelefones().add(cliCadDto.getTelefone2());
		}
		
		if (cliCadDto.getTelefone3() != null) {
			cli.getTelefones().add(cliCadDto.getTelefone3());
		}
		
		return cli;
	}
	
	private void updateData(Cliente novoCliente, Cliente cliente) {
		novoCliente.setNome(cliente.getNome());
		novoCliente.setEmail(cliente.getEmail());
	}
	
	public URI uploadProfilePicture(MultipartFile multipartFile) {
		
		UserDetailsImpl user = UserService.getAuthenticated();
		
		if (user == null) {
			throw new AuthorizationException("Acesso Negado");
		}
		
		URI uri = s3Service.uploadFile(multipartFile);
		
		Cliente cli = clienteRepo.findById(user.getId()).get();
		cli.setImageUrl(uri.toString());
		clienteRepo.save(cli);
		
		return uri;		
	}
}
