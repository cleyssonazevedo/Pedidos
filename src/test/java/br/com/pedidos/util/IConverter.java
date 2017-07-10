package br.com.pedidos.util;

import java.util.Date;

import br.com.pedidos.modelo.Pedido;

public interface IConverter {
	String toJSON(Pedido pedido) throws Exception;
	String toXML(Pedido pedido) throws Exception;
	
	String toJSONDate(Date data) throws Exception;
	String toXMLDate(Date data) throws Exception;
	
	static IConverter getInstance() {
		return new ConverterImpl();
	}
}
