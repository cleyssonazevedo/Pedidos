package br.com.pedidos.dao.produto;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.pedidos.dao.GenericImpl;
import br.com.pedidos.modelo.Pedido;
import br.com.pedidos.modelo.Produto;
import br.com.pedidos.servicos.IService;

@Component
public class ProdutoImpl extends GenericImpl<Produto, Long> implements IProduto {
	
	private IService service;
	
	public ProdutoImpl() {
		super(Produto.class);
		service = IService.getInstance();
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void inserir(List<Produto> produtos, Pedido pedido) throws Exception {
		// TODO Auto-generated method stub
		 for(Produto produto : produtos) {
			 produto.setPedido(pedido);
			 super.inserir(produto);
		 }
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Produto> listar(Pedido pedido) throws NoResultException, Exception {
		// TODO Auto-generated method stub
		CriteriaBuilder builder = super.getManager().getCriteriaBuilder();
		CriteriaQuery<Produto> criteria = builder.createQuery(Produto.class);
		Root<Produto> root = criteria.from(Produto.class);
		
		criteria.select(root)
			.where(
				builder.equal(root.get("pedido").get("id"), pedido.getId())
			);
		
		List<Produto> produtos = super.getManager().createQuery(criteria).getResultList();
		if(produtos != null && !produtos.isEmpty()) {
			pedido.setProdutos(produtos);
			service.regrasParaProduto(pedido);
			return produtos;
		} else
			throw new NoResultException("Não existem produtos para o pedido com o ID " + pedido.getId());
	}
}
