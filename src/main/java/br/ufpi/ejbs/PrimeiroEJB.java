package br.ufpi.ejbs;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.ufpi.entity.Produto;

@Stateless
public class PrimeiroEJB {

	@PersistenceContext
	private EntityManager em;

	public void salvar(Produto produto) {
		em.persist(produto);
	}

}
