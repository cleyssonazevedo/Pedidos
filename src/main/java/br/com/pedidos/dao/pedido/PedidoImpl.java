package br.com.pedidos.dao.pedido;

import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.pedidos.dao.GenericImpl;
import br.com.pedidos.excecoes.DuplicateObjectException;
import br.com.pedidos.modelo.Cliente;
import br.com.pedidos.modelo.Pedido;
import br.com.pedidos.modelo.Produto;
import br.com.pedidos.servicos.IService;

@Component
public class PedidoImpl extends GenericImpl<Pedido, Long> implements IPedido {
	private final IService service;
	
	public PedidoImpl() {
		super(Pedido.class);
		service = IService.getInstance();
	}
	
	@Override
	@Transactional(readOnly = true)
	public boolean exists(long id) {
		// TODO Auto-generated method stub
		try {
			CriteriaBuilder builder = super.getManager().getCriteriaBuilder();
			CriteriaQuery<Pedido> criteria = builder.createQuery(Pedido.class);
			Root<Pedido> root = criteria.from(Pedido.class);
			
			criteria.select(root)
				.where(builder.equal(root.get("id"), id));
			
			List<Pedido> pedidos = super.getManager().createQuery(criteria).getResultList();
			if(pedidos != null && pedidos.size() > 0)
				return true;
			else
				return false;
		}catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Pedido> buscarPorData(Date cadastro) throws NoResultException, Exception {
		// TODO Auto-generated method stub
		CriteriaBuilder builder = super.getManager().getCriteriaBuilder();
		CriteriaQuery<Pedido> criteria = builder.createQuery(Pedido.class);
		Root<Pedido> root = criteria.from(Pedido.class);
		
		criteria.select(root)
			.where(
				builder.equal(root.get("cadastro"), cadastro)
			);
		
		List<Pedido> pedidos = getManager().createQuery(criteria).getResultList();
		if(pedidos != null && !pedidos.isEmpty())
			return pedidos;
		else
			throw new NoResultException("Não existem pedidos com esta data!");
	}
	
	@Override
	@Transactional
	public void inserir(Pedido pedido) throws Exception {
		// TODO Auto-generated method stub
		service.aplicarTodasAsRegras(pedido);
		
		if(exists(pedido.getId()))
			throw new DuplicateObjectException("Pedido já foi cadastrado!");
		
		Cliente cliente = super.getManager().find(Cliente.class, pedido.getCliente().getId());
		if(cliente == null)
			throw new NoResultException("Não Existe cliente com este ID!!!");
		
		pedido.setCliente(cliente);
		super.inserir(pedido);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Pedido> listar() throws NoResultException, Exception {
		// TODO Auto-generated method stub
		List<Pedido> pedidos = super.listar();
		
		CriteriaBuilder builder = super.getManager().getCriteriaBuilder();
		CriteriaQuery<Produto> criteria = builder.createQuery(Produto.class);
		Root<Produto> root = criteria.from(Produto.class);
		
		pedidos.forEach(pedido -> {
			criteria.select(root).where(
				builder.equal(root.get("pedido").get("id"), pedido.getId())
			);
			
			pedido.setProdutos(super.getManager().createQuery(criteria).getResultList());
		});
		
		
		return pedidos;
	}
}