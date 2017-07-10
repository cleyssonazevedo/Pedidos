package br.com.pedidos.controle;

import javax.persistence.NoResultException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.pedidos.controle.lista.Pedidos;
import br.com.pedidos.dao.pedido.IPedido;
import br.com.pedidos.dao.produto.IProduto;
import br.com.pedidos.excecoes.BadRequestException;
import br.com.pedidos.excecoes.DuplicateObjectException;
import br.com.pedidos.modelo.Pedido;

@Controller
@RequestMapping(value = "/pedido", 
	consumes = { "application/json", "application/xml" },
	produces = { "application/json", "application/xml" }) 
	
public class PedidoControle extends Erros {
	@Autowired
	private IPedido pedidoDAO;
	
	@Autowired
	private IProduto produtoDAO;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public Pedido inserirPedido(@RequestBody(required = true) @Valid Pedido pedido) throws DuplicateObjectException, Exception {		
		pedidoDAO.inserir(pedido);
		produtoDAO.inserir(pedido.getProdutos(), pedido);
		return pedido;
	}
	
	@GetMapping("/{id}")
	@ResponseBody
	public Pedido buscarPedido(@PathVariable Long id) throws NoResultException, BadRequestException, Exception {
		Pedido pedido = pedidoDAO.buscar(id);
		produtoDAO.listar(pedido);
		
		return pedido;
	}
	
	@PostMapping("/data")
	@ResponseBody
	public Pedidos buscarPedidosPorData(@RequestBody(required = true) Pedido pedido) throws BadRequestException, NoResultException, Exception {
		if(pedido != null  && pedido.getCadastroDate() != null) {
			return new Pedidos(
				pedidoDAO.buscarPorData(pedido.getCadastroDate())
			);
		} else
			throw new BadRequestException();
	}
	
	@GetMapping
	@ResponseBody
	public Pedidos listarPedidos() throws NoResultException, Exception {
		return new Pedidos(pedidoDAO.listar());
	}
}