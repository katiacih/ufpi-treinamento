# Autenticação com JSF #

## Maneiras de se fazer ##
  * **JSF** => Controlar manualmente através de ManagedBeans de Sessão e Filtros.
  * **JAAS** => Java Authentication and Authorization Service API (Padrão JavaEE para segurança de sistemas)
  * **Frameworks** => Existem frameworks específicos para este trabalho, dentre os principais podemos citar:
    * **Spring Security:** http://projects.spring.io/spring-security/
    * **Apache Shiro:** http://shiro.apache.org

Para fins de estudos abordaremos apenas a primeira maneira (JSF), e para implementar o módulo de login será necessário seguir os seguintes passos:

  * Criar método para buscar usuário a partir de email e senha (EJB)

```
 	// simples busca de cliente no banco
 	public Cliente procurarClientePorEmailSenha(String email, String senha) {
		TypedQuery<Cliente> query = em
				.createQuery(
						"SELECT c FROM Cliente c WHERE c.email = :email AND c.senha = :senha",
						Cliente.class);
		query.setParameter("email", email);
		query.setParameter("senha", convertStringToMd5(senha));

		List<Cliente> clientes = query.getResultList();

		if (clientes.size() == 1)
			return clientes.get(0);

		return null;
	}
```

  * Conversão da senha para MD5 (EJB)

```
  	// método para criptografia da senha
  	private String convertStringToMd5(String valor) {
		MessageDigest mDigest;
		try {

			// Instanciamos o nosso HASH MD5, poderíamos usar outro como
			// SHA, por exemplo, mas optamos por MD5.
			mDigest = MessageDigest.getInstance("MD5");

			// Convert a String valor para um array de bytes em MD5
			byte[] valorMD5 = mDigest.digest(valor.getBytes("UTF-8"));

			// Convertemos os bytes para hexadecimal, assim podemos salvar
			// no banco para posterior comparação se senhas
			StringBuffer sb = new StringBuffer();
			for (byte b : valorMD5) {
				sb.append(Integer.toHexString((b & 0xFF) | 0x100).substring(1,
						3));
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}
```
  * Buscar usuário logado.

  * Criar métodos para obter usuário logado, efetuar login e logout (ManagedBean)

```
 	// verifica o email e senha, se válidos registra o atributo "usuarioLogado" na sessão do navegador com o valor do objeto cliente buscado no banco
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

	// remove o atributo "usuarioLogado" registrado na sessão no momento do login
	// e invalida a sessão do usuario no navegador
	public void fazerLogout() {
		Map<String, Object> session = FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap();
		session.remove("usuarioLogado");
		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		request.getSession().invalidate(); // invalida sessão do usuario
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("login.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// obtem usuário logado
	public Cliente getUsuarioLogado() {
		Map<String, Object> session = FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap();
		return (Cliente) session.get("usuarioLogado");
	}
```

  * Criar pagina de login, apontando para o bean anterior (.xhtml)

```
 	<!DOCTYPE html>
	<html xmlns="http://www.w3c.org/1999/xhtml"
		xmlns:h="http://java.sun.com/jsf/html"
		xmlns:f="http://java.sun.com/jsf/core"
		xmlns:p="http://primefaces.org/ui"
		xmlns:ui="http://java.sun.com/jsf/facelets">
	<h:head>
	</h:head>
	<h:body>
		<p:messages id="messages" showDetail="true" />
		<h:form style="margin: 0 auto; width: 20%; margin-top: 20%">

			<p:fieldset legend="Login">
				<p:growl showDetail="true" />

				<h:panelGrid columns="2">

					<p:outputLabel value="Email:" for="email" />
					<p:inputText id="email" value="#{usuarioBean.email}" required="true" />

					<p:outputLabel value="Senha:" for="senha" />
					<p:password id="senha" value="#{usuarioBean.senha}" required="true" />

				</h:panelGrid>

				<p:commandButton icon="ui-icon-user" value="Entrar"
					action="#{usuarioBean.fazerLogin}" update="@form :messages" />

			</p:fieldset>

		</h:form>
	</h:body>
	</html>
```

  * Definir filtro para interceptar requisições e evitar que o usuário acesse as páginas sem está logado.

```

@WebFilter(urlPatterns = { "/*" }) // define o padrão de captura das requisições
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

```

  * Configurar tempo de inatividade no web.xml, após esse tempo configurado a sessão automaticamente será invalidada e o usuário perderá seu login. Para isso basta adicionar o trecho de código abaixo no arquivo web.xml. Lembrando que o valor do parâmetro é dado em minuto, no exemplo abaixo o usuário perderá a sessão automaticamente se ficar 60 minutos sem executar qualquer requisição a aplicação.

```
 	<session-config>
		<session-timeout>60</session-timeout>
	</session-config>
```

Para maior detalhamento sobre o módulo de login veja a referência **"Criando um módulo de login"** no tópico de **Referências**

## Referências ##

  * **Criando um módulo de login:** http://www.devmedia.com.br/jsf-session-criando-um-modulo-de-login/30975 (pt\_BR)
  * **Login utilizando JAAS:** http://www.javacodegeeks.com/2012/06/full-webapplication-jsf-ejb-jpa-jaas.html (en)
  * **JAAS e JSF:** http://uaihebert.com/user-login-validation-with-jaas-and-jsf/ (en)
  * **Configurações de segurança com o JSF:** https://blogs.oracle.com/enterprisetechtips/entry/improving_jsf_security_configuration_with
  * **Segurança com JSF + JavaEE + JBoss:** http://www.javacodegeeks.com/2014/01/securing-a-jsf-application-with-java-ee-security-and-jboss-as-7-x.html

# CDI #
A CDI é uma especificação Java, cujo nome completo é “Context and Dependency Injection"

## Configuração ##

Como estamos trabalhando com uma aplicação Web (war), basta colocarmos um arquivo chamado beans.xml (vazio) dentro da pasta WEB-INF da nossa aplicação. Agora se tiver um jar simples ou ejb-jar, colocamos esse arquivo dentro da pasta META-INF e pronto, a configuração da CDI está pronta.

## Novos Escopos ##
Todos os escopo estão definidos no pacote javax.enterprise.context, então cuidado na hora de importa esses escopos, pois eles possuem o mesmo nome dos escopos padrões do JSF.

  * **@RequestScoped:** Escopo relativo a requisições
  * **@SessionScoped:** Escopo relativo a sessão do navegador
  * **@ApplicationScoped:** O maior escopo possível
  * **@ConversationScoped:** Esse escopo fornece a possibilidade de controlar até onde o escopo é válido.
  * **@Dependent:** O escopo padrão do CDI
  * **@ViewScoped:** O escopo de mantido enquanto o usuário permanecer na mesma página.

> ## Principais Anotações ##

  * **@Inject:** Para fazer o uso de injeções.
  * **@Named:** Para dar nomes aos beans fazendo com que eles sejam acessíveis através da EL (ExpressionLanguagem), essa anotação equivale a @ManagedBean vista anteriormente.

## Referências ##
  * **Livro da Casa do Código:** CDI Integre as Dependencias E Contextos do Seu Codigo Java - Casa do Codigo
  * **CDI Básico:** http://blog.caelum.com.br/use-cdi-no-seu-proximo-projeto-java/
  * **Injeção com CDI:** http://filipeferraz.com/?p=13
  * **Slides de Resumo:** http://www.slideshare.net/mgraciano/introduo-a-cdi-e-como-utilizla-em-aplicaes-reais
  * **JavaEE 7 CDI - Documentação:** http://docs.oracle.com/javaee/7/tutorial/partcdi.htm#GJBNR

Obs: Por conta do escopo de visão ser uma novidade na API 1.2 do CDI, ele não está presente no mesmo pacote dos outros escopos. O pacote do escopo de visão é "javax.faces.view".

# Referências Extras #
  * **TomEE:** http://tomee.apache.org/ (Utilizando padrão JavaEE 6 com o Tomcat)