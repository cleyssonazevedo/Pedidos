package br.com.pedidos.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;

@Repository
public interface IGeneric<E, ID extends Serializable> {
	void inserir(E entity) throws Exception;
	
	E buscar(ID id) throws NoResultException, Exception;
	List<E> listar() throws NoResultException, Exception;
}
