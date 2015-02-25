package br.ufpi.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
// @Table(name = "produto")
public class Produto {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private Double precoUnitario;

	private String tipo;

	private String descricao;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	/**
	 * @return the precoUnitario
	 */
	public Double getPrecoUnitario() {
		return precoUnitario;
	}

	/**
	 * @param precoUnitario
	 *            the precoUnitario to set
	 */
	public void setPrecoUnitario(Double precoUnitario) {
		this.precoUnitario = precoUnitario;
	}

	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * @param tipo
	 *            the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}
