package br.com.pedidos.dao.produto;

import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;

import br.com.pedidos.dao.IGeneric;
import br.com.pedidos.modelo.Pedido;
import br.com.pedidos.modelo.Produto;

@Repository
public interface IProduto extends IGeneric<Produto, Long> {
	void inserir(List<Produto> produtos, Pedido pedido) throws Exception;
	
	List<Produto> listar(Pedido pedido) throws NoResultException, Exception;
}
