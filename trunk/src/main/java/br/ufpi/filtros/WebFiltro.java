package br.ufpi.filtros;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.ufpi.entidades.Cliente;

@WebFilter(urlPatterns = { "/*" })
public class WebFiltro implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// sem necessidade de implementação
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		Cliente cliente = (Cliente) httpRequest.getSession().getAttribute(
				"usuarioLogado");

		String paginaAcessada = httpRequest.getRequestURI();
		boolean requestDoLogin = paginaAcessada.contains("login.xhtml");

		if (cliente != null) {
			if (requestDoLogin) {
				HttpServletResponse httpResponse = (HttpServletResponse) response;
				httpResponse.sendRedirect("index.xhtml");
			} else {
				chain.doFilter(request, response);
			}
		} else {
			if (!requestDoLogin
					&& !paginaAcessada.contains("javax.faces.resource")) {
				httpRequest.getRequestDispatcher("/login.xhtml").forward(
						request, response);
			} else {
				chain.doFilter(request, response);
			}
		}

	}

	@Override
	public void destroy() {
		// sem necessidade de implementação
	}

}
