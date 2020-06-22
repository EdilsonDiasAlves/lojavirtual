package com.mz.lojavirtual.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.mz.lojavirtual.domain.Cliente;
import com.mz.lojavirtual.repositories.ClienteRepository;
import com.mz.lojavirtual.services.exceptions.ObjectNotFoudException;

@Service
public class AuthService {
	
	@Autowired
	private ClienteRepository clienteRepo;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Autowired
	private EmailService emailService;
	
	private Random rand = new Random();

	public void sendNewPassword(String email) {
		
		Cliente cliente = clienteRepo.findByEmail(email);
		if (cliente == null) {
			throw new ObjectNotFoudException("Email não encontrado");
		}
		
		String newPass = newPassword();
		cliente.setSenha(encoder.encode(newPass));
		
		clienteRepo.save(cliente);
		emailService.sendNewPasswordEmail(cliente, newPass);
	}

	private String newPassword() {
		char[] vet = new char[10];
		for (int i = 0; i < vet.length; i++) {
			vet[i] = randomChar();
		}
		return new String(vet);
	}

	// Logica baseada na tabela unicode
	private char randomChar() {
		int opt = rand.nextInt(3);
		
		// Gera um digito
		if (opt == 0) {
			return (char) (rand.nextInt(10) + 48);
		}
		// Gera letra maiuscula
		else if (opt == 1) {
			return (char) (rand.nextInt(26) + 65);
		}
		// Gera letra minuscula
		else {
			return (char) (rand.nextInt(26) + 97);
		}
	}
}
