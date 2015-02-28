package br.ufpi.entity.test;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class ManagerBean {

	@EJB
	private TestEJB testEJB;

	private String mensagem;

	@PostConstruct
	public void managerBean() {
		// EntityManagerFactory emf = Persistence
		// .createEntityManagerFactory("hibernate-jpa-mysql");
		// EntityManager em = emf.createEntityManager();
		// Produto produto = em.find(Produto.class, 4);
		// this.mensagem = produto.getDescricao();
		this.mensagem = "Bom dia";
	}

	public void save() {
		testEJB.salvar();
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

}
