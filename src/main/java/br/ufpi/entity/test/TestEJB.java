package br.ufpi.entity.test;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.ufpi.entity.Produto;

@Stateless
public class TestEJB {

	@PersistenceContext
	private EntityManager em;

	public void salvar() {
		Produto produto = new Produto();
		produto.setDescricao("produto managedBean");
		produto.setPrecoUnitario(10d);

		em.persist(produto);
	}

}
