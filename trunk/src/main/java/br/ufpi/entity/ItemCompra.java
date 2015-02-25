/**
 * 
 */
package br.ufpi.entity;

/**
 * @author WermesonReis
 *
 */
public class ItemCompra {
	private Produto produto;
	private Compra compra;
	private Long id;
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
