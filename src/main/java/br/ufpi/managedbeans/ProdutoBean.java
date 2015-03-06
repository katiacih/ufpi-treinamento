package br.ufpi.managedbeans;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.ufpi.ejbs.ProdutoEJB;
import br.ufpi.entidades.Produto;

@Named
@ViewScoped
public class ProdutoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@PersistenceContext
	private EntityManager em;

	private LazyDataModel<Produto> produtos;

	@Inject
	private Produto produto;

	@Inject
	private ProdutoEJB produtoEJB;

	public ProdutoBean() {

	}

	@PostConstruct
	public void postConstructor() {
		produtos = new LazyDataModel<Produto>() {

			private static final long serialVersionUID = 1L;

			@Override
			public List<Produto> load(int first, int pageSize,
					String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {
				TypedQuery<Produto> query = em.createQuery(
						"SELECT p FROM Produto p", Produto.class);
				query.setFirstResult(first);
				query.setMaxResults(pageSize);

				TypedQuery<Long> queryCount = em.createQuery(
						"SELECT COUNT(p) FROM Produto p", Long.class);

				this.setRowCount(queryCount.getSingleResult().intValue());

				return query.getResultList();
			}

		};
	}

	public void remover(Produto produto) {
		if (produto.getId() == this.produto.getId()) {
			cancelar();
		}
		produtoEJB.deletar(produto);
	}

	public void editar(Produto produto) {
		this.produto = produto;
	}

	public void salvar() {

		try {
			produtoEJB.salvar(produto);
		} catch (RuntimeException e) {
			FacesContext.getCurrentInstance().addMessage(
					"form:desc",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e
							.getMessage(), "detalhe da mensagem"));
		}
		cancelar();
	}

	public void cancelar() {
		produto = new Produto();
	}

	public LazyDataModel<Produto> getProdutos() {
		return produtos;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

}
