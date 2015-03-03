package br.ufpi.extras;

import java.util.Enumeration;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.ufpi.managedbeans.ProdutoBean;

public class MessageBundle extends ResourceBundle {

	@PersistenceContext
	private EntityManager em;

	@Override
	protected Object handleGetObject(String key) {
		ResourceBundle bundle = ResourceBundle.getBundle("log4j");
		System.out.println(bundle);

		ProdutoBean primeiroBean = FacesContext
				.getCurrentInstance()
				.getApplication()
				.evaluateExpressionGet(FacesContext.getCurrentInstance(),
						"#{x}", ProdutoBean.class);

		System.out.println(primeiroBean);
		return "novo valor";
	}

	@Override
	public Enumeration<String> getKeys() {
		System.out.println("getKeys");
		return null;
	}

}
