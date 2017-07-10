package br.com.pedidos.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public abstract class GenericImpl<E, ID extends Serializable> implements IGeneric<E, ID> {
	private final Class<E> persistClass;

	@PersistenceContext
	private EntityManager manager;

	public GenericImpl(Class<E> persistClass) {
		// TODO Auto-generated constructor stub
		this.persistClass = persistClass;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void inserir(E entity) throws Exception {
		// TODO Auto-generated method stub
		manager.persist(entity);
	}

	@Override
	@Transactional(readOnly = true)
	public E buscar(ID id) throws NoResultException, Exception {
		// TODO Auto-generated method stub
		E entity = manager.find(persistClass, id);
		if (entity != null)
			return entity;
		else
			throw new NoResultException();
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<E> listar() throws NoResultException, Exception {
		// TODO Auto-generated method stub
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<E> criteria = builder.createQuery(persistClass);
		Root<E> root = criteria.from(persistClass);
		
		criteria.select(root);
		List<E> lista = manager.createQuery(criteria).getResultList();
		if(lista != null && !lista.isEmpty())
			return lista;
		else
			throw new NoResultException();
	}

	public EntityManager getManager() {
		return manager;
	}
}