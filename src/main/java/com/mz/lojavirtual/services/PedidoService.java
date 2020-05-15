package com.mz.lojavirtual.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mz.lojavirtual.domain.ItemPedido;
import com.mz.lojavirtual.domain.PagamentoComBoleto;
import com.mz.lojavirtual.domain.Pedido;
import com.mz.lojavirtual.domain.enums.EstadoPagamento;
import com.mz.lojavirtual.repositories.ItemPedidoRepository;
import com.mz.lojavirtual.repositories.PagamentoRepository;
import com.mz.lojavirtual.repositories.PedidoRepository;
import com.mz.lojavirtual.services.exceptions.ObjectNotFoudException;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository pedidoRepo;
	
	@Autowired
	private PagamentoRepository pagamentoRepo;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepo;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private EmailService emailService;

	public Pedido find(Integer id) {
		Optional<Pedido> obj = pedidoRepo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoudException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}

	@Transactional
	public Pedido insert(Pedido pedido) {
		pedido.setId(null);
		pedido.setInstante(new Date());
		pedido.setCliente(clienteService.find(pedido.getCliente().getId()));
		pedido.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		pedido.getPagamento().setPedido(pedido); 
		
		if(pedido.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) pedido.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, pedido.getInstante());
		}
		
		pedidoRepo.save(pedido);
		pagamentoRepo.save(pedido.getPagamento());
		
		for (ItemPedido item : pedido.getItens()) {
			item.setProduto(produtoService.find(item.getProduto().getId()));
			item.setDesconto(0.0);
			item.setPreco(item.getProduto().getPreco());
			item.setPedido(pedido);
		}
		
		itemPedidoRepo.saveAll(pedido.getItens());
		emailService.sendOrderConfirmationEmail(pedido);
		
		return pedido;
	}
}
