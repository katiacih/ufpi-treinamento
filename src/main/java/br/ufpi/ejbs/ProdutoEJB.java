package br.ufpi.ejbs;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.ufpi.entidades.Produto;

@Stateless
public class ProdutoEJB {

	@PersistenceContext
	private EntityManager em;

	public void deletar(Produto produto) {
		em.remove(em.merge(produto));
	}

	public void salvar(Produto produto) {
		em.merge(produto);
	}

}
