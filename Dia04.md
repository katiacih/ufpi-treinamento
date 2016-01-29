# Introdução ao JSF e Primefaces #

JSF é uma tecnologia que nos permite criar aplicações Java para Web utilizando componentes visuais pré-prontos, de forma que o desenvolvedor não se preocupe com Javascript e HTML. Basta adicionarmos os componentes (calendários, tabelas, formulários) e eles serão renderizados e exibidos em formato html.

## Características do JSF ##

  * Guarda o estado dos componentes
  * Separa as camadas
  * Especificação: várias implementações
    * Mojarra (http://javaserverfaces.java.net)
    * Apache Myfaces (http://myfaces.apache.org)
  * Extensível: várias implementações
    * Richfaces (http://richfaces.org)
    * Primefaces (http://primefaces.org)
    * Icefaces (http://icefaces.org)

### Preparação do ambiente ###

Ter certeza de ter as dependências no _pom.xml_ referentes as bibliotecas da api do JSF e do Primefaces. Ex:

```

<!-- dependencia para uso do primefaces no projeto -->
<dependency>
	<groupId>org.primefaces</groupId>
	<artifactId>primefaces</artifactId>
	<version>5.1</version>
</dependency>

<!-- dependencia para o uso das classes do JSF no projeto -->
<dependency>
	<groupId>javax.faces</groupId>
	<artifactId>jsf-api</artifactId>
	<version>2.1</version>
	<scope>provided</scope> <!-- escopo definido para evitar que a dependência seja inclusa no pacote gerado -->
</dependency>

```

Lembrar de adicionar suporte ao JSF no projeto do eclipse.

Botão direito no projeto => propriedades => project facets => marcar a opção JavaServerFaces => Desabilitar a configuração de biblioteca de implementação => Confirmar.


### Configuração do controlador do JSF ###

Adicionar a configuração no arquivo web.xml, para fazer o mapeamento das requisições que serão processadas pelo Servlet do JSF. É comum usar-mos o padrão _**`*`.xhtml**_ para o mapeamento do Servlet para evitar que o usuário consiga ter acesso ao código fonte da nossa página. (View).

```
<servlet>
  <servlet-name>FacesServlet</servlet-name>
  <servlet-class>javax.faces.webapp.FacesServlet</servlet-class>
  <load-on-startup>1</load-on-startup>
</servlet>
<servlet-mapping>
  <servlet-name>FacesServlet</servlet-name>
  <url-pattern>*.xhtml</url-pattern>
</servlet-mapping>
```

**Obs**: Esse mapeamento é opcional, por convensão as requisições com o padrão "`*`.faces" serão tratadas pelo Servlet.

### A primeira página com JSF ###

```
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml"
    xmlns:h="http://java.sun.com/jsf/html">

    <!-- aqui usaremos as tags do JSF -->

</html>
```

### Adicionando suporte ao Primefaces ###

```
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml"
    xmlns:h="http://java.sun.com/jsf/html"
    xmlns:p="http://primefaces.org/ui"> <!-- <= suporte para tag/componententes do primefaces -->

    <!-- aqui usaremos as tags do JSF e/ou tags do Primefaces -->

</html>
```

### Integração com o modelo ###

Para mostrar tais informações, precisaremos executar um código Java e certamente não faremos isso na camada de visualização: esse código ficará separado da view, em uma classe de modelo. Essas classes de modelo que interagem com os componentes do JSF são os Managed Beans.

Estes, são apenas classezinhas simples que com as quais o JSF consegue interagir através do acesso a seus métodos. Nada mais são do que POJOs anotados com @ManagedBean.

```
@ManagedBean
public class OlaMundoBean {

  public String getHorario() {
    SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
    return "Atualizado em " + sdf.format(new Date());
  }
}

<h:outputText value="#{olaMundoBean.horario}" />

```

Lembretes:
  * Todo ManagedBean é acessado através de seu nome (nome da classe definida com a primeira minúscula)
  * É possível modificar o nome do ManagedBean atráves do atributo _name_ da anotação _@ManagedBean_
  * O acesso aos ManagedBeans da aplicação apartir das views(páginas) é feito através da EL (Expression Languagem).

## Referências ##

  * **Tutorial JavaEE 7**: http://docs.oracle.com/javaee/7/JEETT.pdf
  * **Tutorial JSF e Primefaces**: http://www.caelum.com.br/apostila-java-testes-jsf-web-services-design-patterns/introducao-ao-jsf-e-primefaces/
  * **Componentes Primafaces:** http://www.primefaces.org/showcase/
  * **Tutorial Primefaces:** http://www.primefaces.org/docs/guide/primefaces_user_guide_5_1.pdf
  * **EL (Expression Language):** http://docs.oracle.com/javaee/7/tutorial/jsf-el.htm#GJDDD