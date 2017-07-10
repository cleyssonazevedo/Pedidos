package br.com.pedidos.controle.lista;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.pedidos.modelo.Pedido;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Pedidos {
	@XmlElementWrapper(name = "pedidos")
	@XmlElements(@XmlElement(name = "pedido"))
	private List<Pedido> pedidos;

	public Pedidos() {
		// TODO Auto-generated constructor stub
	}
	
	public Pedidos(List<Pedido> pedidos) {
		// TODO Auto-generated constructor stub
		this.pedidos = pedidos;
	}
	
	public List<Pedido> getPedidos() {
		return pedidos;
	}
}