package br.com.pedidos.servicos;

import br.com.pedidos.excecoes.BadRequestException;
import br.com.pedidos.modelo.Pedido;

/**
 * Responsável por aplicar todas as regras de negócio para a classe Pedido,
 * assim, em caso de manutenção, irá permitir maior velocidade de mudança nestas regras
 */
public interface IService {
	Pedido aplicarTodasAsRegras(Pedido pedido) throws BadRequestException, Exception;
	void regrasParaData(Pedido pedido);
	void regrasParaCliente(Pedido pedido) throws BadRequestException;
	/**
	 * Insere todas as regras para produto e inserindo em pedido o valor total de produtos
	 * @param pedido
	 * @throws BadRequestException Em caso dados obrigatórios em falta ou regras fora de norma
	 */
	void regrasParaProduto(Pedido pedido) throws BadRequestException;
	
	static IService getInstance() {
		return Service.getInstance();
	}
}