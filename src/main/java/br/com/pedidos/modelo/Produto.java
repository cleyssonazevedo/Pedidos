package br.com.pedidos.modelo;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

@XmlRootElement(name = "produto")
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(propOrder = {"id", "nome", "quantidade", "valor"})
@Entity
public class Produto {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(nullable = false)
	private String nome;

	@NotNull
	@Column(nullable = false)
	private BigDecimal valor;

	@Column(nullable = false)
	private Integer quantidade;

	@JsonIgnore
	@OnDelete(action = OnDeleteAction.CASCADE)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_pedido", foreignKey = @ForeignKey(name = "fk_pedido"), nullable = false)
	private Pedido pedido;
	
	public Produto() {
		// TODO Auto-generated constructor stub
	}
	
	public Produto(BuilderProduto builder) {
		// TODO Auto-generated constructor stub
		this.id = builder.id;
		this.nome = builder.nome;
		this.valor = builder.valor;
		this.quantidade = builder.quantidade;
		this.pedido = builder.pedido;
	}
	
	public static class BuilderProduto {
		private Long id;
		private String nome;
		private BigDecimal valor;
		private Integer quantidade;
		private Pedido pedido;
		
		public BuilderProduto setId(Long id) {
			this.id = id;
			return this;
		}
		
		public BuilderProduto setNome(String nome) {
			this.nome = nome;
			return this;
		}
		
		public BuilderProduto setValor(BigDecimal valor) {
			this.valor = valor;
			return this;
		}
		
		public BuilderProduto setQuantidade(Integer quantidade) {
			this.quantidade = quantidade;
			return this;
		}
		
		public BuilderProduto setPedido(Pedido pedido) {
			this.pedido = pedido;
			return this;
		}
		
		public Produto build() {
			return new Produto(this);
		}
	}
	
	@XmlElement
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@XmlElement
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@XmlElement
	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	
	@XmlElement
	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	@XmlTransient
	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}
}
