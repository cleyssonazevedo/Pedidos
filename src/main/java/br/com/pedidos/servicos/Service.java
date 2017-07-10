package br.com.pedidos.servicos;

import java.math.BigDecimal;
import java.util.Date;

import br.com.pedidos.excecoes.BadRequestException;
import br.com.pedidos.modelo.Pedido;
import br.com.pedidos.modelo.Produto;

public class Service implements IService {
	private static Service singleton;
	
	private Service() {
	}
	
	public static synchronized Service getInstance() {
		if(singleton == null)
			singleton = new Service();
		
		return singleton;
	}
	
	@Override
	public void regrasParaData(Pedido pedido) {
		// TODO Auto-generated method stub
		if(pedido.getCadastroDate() == null)
			pedido.setCadastroDate(new Date());
	}
	
	@Override
	public void regrasParaCliente(Pedido pedido) throws BadRequestException {
		// TODO Auto-generated method stub
		if(pedido.getCliente().getId() == null)
			throw new BadRequestException("Requer ID do cliente!!");
	}
	
	@Override
	public void regrasParaProduto(Pedido pedido) throws BadRequestException {
		// TODO Auto-generated method stub
		BigDecimal total = new BigDecimal(0);
		/*
		 * Fórmula usada para o cáculo
		 * 
		 * Valor Total = (Valor Unitário * Quantidade) - [(Valor Unitário * Quantidade) * Porcentagem]
		 */
		
		if(pedido.getProdutos().size() > 0 && pedido.getProdutos().size() < 11) {
			for(Produto produto : pedido.getProdutos()) {
				if(produto.getNome() == null || produto.getValor() == null)
					throw new BadRequestException();
				else {
					if(produto.getQuantidade() == null)
						produto.setQuantidade(1);
					
					BigDecimal parcial = produto.getValor().multiply(new BigDecimal(produto.getQuantidade()));
					
					// Calcular desconto
					if(produto.getQuantidade() >= 10)
						parcial = parcial.subtract(parcial.multiply(new BigDecimal(0.10)));
					else
						if(produto.getQuantidade() > 5)
							parcial = parcial.subtract(parcial.multiply(new BigDecimal(0.05)));
					
					// Adicionando ao Total
					total = total.add(parcial);
				}
			}
		}else
			throw new BadRequestException("Número de Produtos incorretos!!!, existem " + pedido.getProdutos().size() + " produtos");
		
		pedido.setTotal(total);
	}

	@Override
	public Pedido aplicarTodasAsRegras(Pedido pedido) throws BadRequestException, Exception {
		// TODO Auto-generated method stub
		regrasParaCliente(pedido);
		regrasParaData(pedido);
		if(pedido.getProdutos() != null)
			regrasParaProduto(pedido);
		
		return pedido;
	}
}