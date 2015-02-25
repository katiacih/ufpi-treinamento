/**
 * 
 */
package br.ufpi.entity;

/**
 * @author Ronyerison
 *
 */
public class Compra {
	private int id;
	private double valor;
	private Cliente c;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getValor() {
		return valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}
	public Cliente getC() {
		return c;
	}
	public void setC(Cliente c) {
		this.c = c;
	}
}
