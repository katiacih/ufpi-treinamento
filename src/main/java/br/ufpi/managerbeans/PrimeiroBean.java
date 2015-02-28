package br.ufpi.managerbeans;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import br.ufpi.ejbs.PrimeiroEJB;
import br.ufpi.entity.Produto;

@ManagedBean(name = "x")
public class PrimeiroBean {

	@EJB
	private PrimeiroEJB primeiroEJB;

	private Produto produto;

	public PrimeiroBean() {

	}

	@PostConstruct
	public void postConstruct() {
		produto = new Produto();
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public void salvar() {

		try {
			primeiroEJB.salvar(produto);
		} catch (RuntimeException e) {
			FacesContext.getCurrentInstance().addMessage("form:desc",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), "detalhe da mensagem"));
		}
		postConstruct();
	}

}
