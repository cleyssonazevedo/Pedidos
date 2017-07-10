package br.com.pedidos.test;

import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Repeat;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import br.com.pedidos.Application;
import br.com.pedidos.dao.cliente.ICliente;
import br.com.pedidos.modelo.Cliente;
import br.com.pedidos.modelo.Pedido;
import br.com.pedidos.modelo.Produto;
import br.com.pedidos.util.IConverter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.http.MediaType.APPLICATION_XML;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class PedidosTest {
	private final MediaType JSON = new MediaType(APPLICATION_JSON_UTF8.getType(),
            APPLICATION_JSON_UTF8.getSubtype(),
            Charset.forName("utf8"));

	private final MediaType XML = new MediaType(APPLICATION_XML.getType(),
            APPLICATION_XML.getSubtype(),
            Charset.forName("utf8"));
	
	private IConverter converter;
	
	@Autowired
	private WebApplicationContext webApplicationContext;
	private MockMvc mockMvc;
	
	private static Pedido pedido;
	private Random random;
	private static boolean jaInserido = false;
	
	@Autowired
	private ICliente clienteDAO;
	
	@Before
	public void before() {
		mockMvc = webAppContextSetup(webApplicationContext).build();
		converter = IConverter.getInstance();
		random = new Random();
		
		// Inserindo clientes
		if(!jaInserido) {
			for(int i = 1; i != 10; i++) {
				Cliente cliente = new Cliente.BuilderCliente()
						.setId((long) i)
						.setNome(RandomStringUtils.randomAlphabetic(10).toString())
						.build();
	
				clienteDAO.save(cliente);
			}
			
			jaInserido = true;
		}
		
		
		// Valores que serão salvos em pedido
		if(pedido == null) {
			List<Produto> produtos = new ArrayList<>(3);

			Produto produto = new Produto.BuilderProduto()
								.setNome("Lápis")
								.setValor(new BigDecimal(1))
								.build();
			produtos.add(produto);

			produto = new Produto.BuilderProduto()
							.setNome("Caneta")
							.setValor(new BigDecimal(1.5))
							.setQuantidade(15)
							.build();
			produtos.add(produto);

			produto = new Produto.BuilderProduto()
							.setNome("Giz")
							.setValor(new BigDecimal(0.5))
							.setQuantidade(9)
							.build();
			produtos.add(produto);
			
			
			// Número aleatório com o ID de 1 a 10 do cliente
			int id = 0;
			do {
				id = (int) random.nextInt(10);
			} while(id == 0);
			
			pedido = new Pedido.BuilderPedido()
						.setCadastro(null)
						.setCliente(
								new Cliente.BuilderCliente()
									.setId((long) id)
									.build())
						.setProdutos(produtos)
						.build();
		}
	}
	
	@Test
	public void SemConteudo() throws Exception {
		mockMvc.perform(
				get("/pedido/" + Math.abs(random.nextLong()))
				.contentType(JSON))
		.andExpect(status().isNoContent());
		
		mockMvc.perform(
				get("/pedido/" + Math.abs(random.nextLong()))
				.contentType(XML))
		.andExpect(status().isNoContent());
	}
	
	@Test
	public void BuscarListaDePedidosInexistente() throws Exception {
		mockMvc.perform(
				get("/pedido")
				.contentType(JSON)
				.accept(JSON))
			.andExpect(status().isNoContent());
	}

	@Test
	@Repeat(10)
	public void InserirPedidoViaJSON() throws Exception {
		pedido.setId(Math.abs(random.nextLong()));
		
		mockMvc.perform(
					post("/pedido")
					.contentType(JSON)
					.content(converter.toJSON(pedido))
					.accept(JSON)
				)
		.andExpect(status().isCreated());
	}
	
	@Test
	public void TentarInserirDuplicado() throws Exception {
		mockMvc.perform(
				post("/pedido")
				.contentType(JSON)
				.content(converter.toJSON(pedido))
				.accept(JSON)
			)
		.andExpect(status().isConflict());
	}
	
	@Test
	public void TentarInserirSemCorpoJSON() throws Exception {
		mockMvc.perform(
				post("/pedido")
				.contentType(JSON)
				.accept(JSON)
			)
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void TentarInserirSemCorpoXML() throws Exception {
		mockMvc.perform(
				post("/pedido")
				.contentType(XML)
				.accept(XML)
			)
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void BuscarPedidoInseridoJSON() throws Exception {
		mockMvc.perform(
					get("/pedido/" + pedido.getId().toString())
					.contentType(JSON)
					.accept(JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(JSON));
	}
	
	
	@Test
	@Repeat(10)
	public void InserirPedidoViaXML() throws Exception {
		pedido.setId(Math.abs(random.nextLong()));
		
		mockMvc.perform(
					post("/pedido")
					.contentType(XML)
					.content(converter.toXML(pedido))
					.accept(XML))
			.andExpect(status().isCreated())
			.andExpect(content().contentType(XML));
	}
	
	@Test
	public void BuscarPedidoXML() throws Exception {
		mockMvc.perform(
				get("/pedido/" + pedido.getId().toString())
				.contentType(XML)
				.accept(XML))
		.andExpect(status().isOk())
		.andExpect(content().contentType(XML));
	}
	
	@Test
	public void ListarTodosEmJSON() throws Exception {
		mockMvc.perform(
				get("/pedido")
				.contentType(JSON)
				.accept(JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(JSON));
	}
	
	@Test
	public void ListarTodosEmXML() throws Exception {
		mockMvc.perform(
				get("/pedido")
				.contentType(XML)
				.accept(XML))
			.andExpect(status().isOk())
			.andExpect(content().contentType(XML));
	}
	
	@Test
	public void PedidoPorDataJSON() throws Exception {
		mockMvc.perform(
				post("/pedido/data")
				.contentType(JSON)
				.content(converter.toJSONDate(new Date()))
				.accept(JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(JSON));
	}
	
	@Test
	public void PedidoPorDataXML() throws Exception {
		mockMvc.perform(
				post("/pedido/data")
				.contentType(XML)
				.content(converter.toXMLDate(new Date()))
				.accept(XML))
			.andExpect(status().isOk())
			.andExpect(content().contentType(XML));
	}
}