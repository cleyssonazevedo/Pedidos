package br.com.pedidos.util;

import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.com.pedidos.modelo.Pedido;

public class ConverterImpl implements IConverter {
	@Override
	public String toJSON(Pedido pedido) throws Exception {
		// TODO Auto-generated method stub
		JSONObject json = new JSONObject();
		json.put("id", pedido.getId());
		
		if(pedido.getCadastroDate() != null)
			json.put("cadastro", pedido.getCadastro());
		
		JSONObject cliente = new JSONObject();
		cliente.put("id", pedido.getCliente().getId());
		
		JSONArray produtos = new JSONArray();
		
		pedido.getProdutos().forEach(p -> {
			JSONObject produto = new JSONObject();
			try {
				produto.put("nome", p.getNome());
				produto.put("valor", p.getValor());
				
				if(p.getQuantidade() != null)
					produto.put("quantidade", p.getQuantidade());
				
				produtos.put(produto);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
		
		json.put("cliente", cliente);
		json.put("produtos", produtos);
		
		return json.toString();
	}

	@Override
	public String toXML(Pedido pedido) throws Exception {
		// TODO Auto-generated method stub
		StringWriter xml = new StringWriter();
		xml.write("<pedido>");
		xml.write(String.format("<id>%s</id>", pedido.getId().toString()));
		
		if(pedido.getCadastroDate() != null) 
			xml.write(String.format("<cadastro>%s</cadastro>", pedido.getCadastro()));
		
		xml.write(String.format("<cliente><id>%s</id></cliente>", pedido.getCliente().getId().toString()));
		
		xml.write("<produtos>");
		pedido.getProdutos().forEach(produto -> {
			xml.write("<produto>");
				xml.write(String.format("<nome>%s</nome>", produto.getNome()));
				xml.write(String.format("<valor>%s</valor>", produto.getValor().toString()));
				
				if(produto.getQuantidade() != null)
					xml.write(String.format("<quantidade>%s</quantidade>", produto.getQuantidade().toString()));
					
			xml.write("</produto>");
		});
		xml.write("</produtos>");
		xml.write("</pedido>");
		return xml.toString();
	}
	
	@Override
	public String toJSONDate(Date data) throws Exception {
		// TODO Auto-generated method stub
		JSONObject pedido = new JSONObject();
		pedido.put("cadastro", new SimpleDateFormat("dd/MM/yyyy").format(data));
		
		return pedido.toString();
	}
	
	@Override
	public String toXMLDate(Date data) throws Exception {
		// TODO Auto-generated method stub
		StringWriter pedido = new StringWriter();
		pedido.write(String.format("<pedido><cadastro>%s</cadastro></pedido>", new SimpleDateFormat("dd/MM/yyyy").format(data)));
		return pedido.toString();
	}
}