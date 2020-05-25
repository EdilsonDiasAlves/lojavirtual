package com.mz.lojavirtual.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mz.lojavirtual.domain.Cliente;
import com.mz.lojavirtual.repositories.ClienteRepository;
import com.mz.lojavirtual.security.UserDetailsImpl;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private ClienteRepository clienteRepo;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Cliente cli = clienteRepo.findByEmail(email);
		
		if (cli == null) {
			throw new UsernameNotFoundException(email);
		}
		
		return new UserDetailsImpl(cli.getId(), cli.getEmail(), cli.getSenha(), cli.getPerfis());
	}
	
}