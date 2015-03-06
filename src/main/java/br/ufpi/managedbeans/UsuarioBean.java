package br.ufpi.managedbeans;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import br.ufpi.ejbs.ClienteEJB;
import br.ufpi.entidades.Cliente;

@Named
@SessionScoped
public class UsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private ClienteEJB clienteEJB;

	private String email;
	private String senha;

	public void fazerLogin() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		if (getUsuarioLogado() == null) {
			Cliente cliente = clienteEJB.procurarClientePorEmailSenha(email,
					senha);
			if (cliente != null) {
				Map<String, Object> session = facesContext.getExternalContext()
						.getSessionMap();
				session.put("usuarioLogado", cliente);
				try {
					facesContext.getExternalContext().redirect("index.xhtml");
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				facesContext.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Erro de Login",
						"Usuario e/ou Senha incorretas"));
			}
		}
	}

	public void fazerLogout() {
		Map<String, Object> session = FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap();
		session.remove("usuarioLogado");
		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		request.getSession().invalidate();
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("login.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Cliente getUsuarioLogado() {
		Map<String, Object> session = FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap();
		return (Cliente) session.get("usuarioLogado");
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}
