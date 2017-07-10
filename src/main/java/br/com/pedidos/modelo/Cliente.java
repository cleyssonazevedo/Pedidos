package br.com.pedidos.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

@XmlRootElement(name = "cliente")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"id", "nome"})
@Entity
public class Cliente {
	@XmlElement
	@NotNull
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@XmlElement
	@Column(nullable = false)
	private String nome;

	
	public Cliente() {
		// TODO Auto-generated constructor stub
	}
	
	public Cliente(BuilderCliente builder) {
		// TODO Auto-generated constructor stub
		this.id = builder.id;
		this.nome = builder.nome;
	}
	
	public static class BuilderCliente {
		private Long id;
		private String nome;
		
		public BuilderCliente setId(Long id) {
			this.id = id;
			return this;
		}
		
		public BuilderCliente setNome(String nome) {
			this.nome = nome;
			return this;
		}
		
		public Cliente build() {
			return new Cliente(this);
		}
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JsonGetter
	public String getNome() {
		return nome;
	}
	
	@JsonIgnore
	public void setNome(String nome) {
		this.nome = nome;
	}

}
