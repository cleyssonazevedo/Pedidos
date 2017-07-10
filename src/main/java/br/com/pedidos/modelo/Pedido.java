package br.com.pedidos.modelo;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

@XmlRootElement(name = "pedido")
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(propOrder = {"id", "cadastro", "cliente", "produtos", "total"})
@Entity
public class Pedido {
	@Id
	private Long id;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date cadastro;

	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_cliente", foreignKey = @ForeignKey(name = "fk_cliente"), nullable = false)
	private Cliente cliente;
	
	@Size(min = 1, max = 10)
	@Transient
	private List<Produto> produtos;

	@Column(nullable = false)
	private BigDecimal total;

	@JsonIgnore
	@Transient
	private final SimpleDateFormat format;

	public Pedido() {
		format = new SimpleDateFormat("dd/MM/yyyy");
	}
	
	
	public Pedido(BuilderPedido builder) {
		format = new SimpleDateFormat("dd/MM/yyyy");
		
		this.id = builder.id;
		this.cadastro = builder.cadastro;
		this.cliente = builder.cliente;
		this.produtos = builder.produtos;
		this.total = builder.total;
	}
	
	public static class BuilderPedido {
		private Long id;
		private Date cadastro;
		private Cliente cliente;
		private List<Produto> produtos;
		private BigDecimal total;
		
		public BuilderPedido setId(Long id) {
			this.id = id;
			return this;
		}
		
		public BuilderPedido setCadastro(Date cadastro) {
			this.cadastro = cadastro;
			return this;
		}
		
		public BuilderPedido setCliente(Cliente cliente) {
			this.cliente = cliente;
			return this;
		}

		public BuilderPedido setProdutos(List<Produto> produtos) {
			this.produtos = produtos;
			return this;
		}
		
		public BuilderPedido setTotal(BigDecimal total) {
			this.total = total;
			return this;
		}
		
		public Pedido build() {
			return new Pedido(this);
		}
	}

	@XmlElement
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JsonIgnore
	@XmlTransient
	public Date getCadastroDate() {
		return cadastro;
	}

	@JsonIgnore
	public void setCadastroDate(Date cadastro) {
		this.cadastro = cadastro;
	}

	@XmlElement
	public String getCadastro() {
		return format.format(cadastro);
	}

	public void setCadastro(String cadastro) throws ParseException {
		this.cadastro = format.parse(cadastro);
	}

	@XmlElement(type = Cliente.class)
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	@JsonGetter
	@XmlElement
	public BigDecimal getTotal() {
		return total.setScale(2, BigDecimal.ROUND_HALF_UP);
	}
	
	@JsonIgnore
	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	@XmlElementWrapper(name = "produtos")
	@XmlElements(@XmlElement(name = "produto", type = Produto.class))
	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}
}