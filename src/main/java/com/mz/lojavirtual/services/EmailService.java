package com.mz.lojavirtual.services;

import org.springframework.mail.SimpleMailMessage;

import com.mz.lojavirtual.domain.Pedido;

public interface EmailService {

	void sendOrderConfirmationEmail(Pedido pedido);
	
	void sendEmail(SimpleMailMessage msg);
}
