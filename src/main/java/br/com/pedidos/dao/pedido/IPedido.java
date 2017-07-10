package br.com.pedidos.dao.pedido;

import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;

import br.com.pedidos.dao.IGeneric;
import br.com.pedidos.modelo.Pedido;

public interface IPedido extends IGeneric<Pedido, Long>{
	boolean exists(long id);
	List<Pedido> buscarPorData(Date cadastro) throws NoResultException, Exception;
}