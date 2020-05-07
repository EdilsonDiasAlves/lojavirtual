package com.mz.lojavirtual.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.mz.lojavirtual.controllers.exception.FieldMessage;
import com.mz.lojavirtual.domain.Cliente;
import com.mz.lojavirtual.domain.enums.TipoCliente;
import com.mz.lojavirtual.dto.ClienteCadastroDTO;
import com.mz.lojavirtual.repositories.ClienteRepository;
import com.mz.lojavirtual.services.validation.util.DocumentUtil;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteCadastroDTO> {
	
	@Autowired
	private ClienteRepository clienteRepo;
	
	@Override
	public void initialize(ClienteInsert ci) {
	}

	@Override
	public boolean isValid(ClienteCadastroDTO objDto, ConstraintValidatorContext context) {
		
		List<FieldMessage> list = new ArrayList<>();

		if(objDto.getTipo().equals(TipoCliente.PESSOA_FISICA.getCod()) && !DocumentUtil.isValidCPF(objDto.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));
		}
		
		if(objDto.getTipo().equals(TipoCliente.PESSOA_JURIDICA.getCod()) && !DocumentUtil.isValidCNPJ(objDto.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido"));
		}
		
		Cliente aux = clienteRepo.findByEmail(objDto.getEmail());
		if(aux != null) {
			list.add(new FieldMessage("email", "Email já existente"));
		}
		
		for (FieldMessage field : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(field.getMessage()).addPropertyNode(field.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}