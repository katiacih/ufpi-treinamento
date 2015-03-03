package br.ufpi.managedbeans;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.ufpi.ejbs.ProdutoEJB;
import br.ufpi.entidades.Produto;

@ManagedBean
@ViewScoped
public class ProdutoBean {

	@PersistenceContext
	private EntityManager em;

	private LazyDataModel<Produto> produtos;

	private Produto produto;

	@EJB
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
		cancelar();
	}

	public void remover(Produto produto) {
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
