/**
 * 
 */
package br.ufpi.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * @author WermesonReis
 *
 */
@Entity
public class ItemCompra {

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne
	private Produto produto;

	@ManyToOne
	private Compra compra;

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Compra getCompra() {
		return compra;
	}

	public void setCompra(Compra compra) {
		this.compra = compra;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
