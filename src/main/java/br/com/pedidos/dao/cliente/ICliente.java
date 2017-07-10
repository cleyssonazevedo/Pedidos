package br.com.pedidos.dao.cliente;

import org.springframework.data.repository.CrudRepository;

import br.com.pedidos.modelo.Cliente;

public interface ICliente extends CrudRepository<Cliente, Long> {

}