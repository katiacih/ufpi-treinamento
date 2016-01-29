# Configurando JPA no servidor de aplicações #

## Configurando driver no servidor de aplicações ##

> Primeiramente devemos retirar a dependência do driver do banco (mysql) do arquivo pom.xml, uma forma de se fazer isso é apenas mudar o escopo da dependencia de "compile" (default) para "provided", dessa forma informamos que a dependência não será empacotada quando criar-mos o arquivo de deploy (.war)

```
	<!-- drive para utilização do banco de dados MySQL -->
	<dependency>
		<groupId>mysql</groupId>
		<artifactId>mysql-connector-java</artifactId>
		<version>5.1.34</version>
		<scope>provided</scope> <!-- informa que a dependência não estará disponível no .war gerado -->
	</dependency>
```

> Agora que retiramos o driver da aplicação, devemos configurá-lo no servidor de aplicações. Há duas formas de fazê-lo:

  * ### Deployando o driver (.jar) direto no servidor como se fosse um arquivo .war ###
> > Para fazer o deploy do driver, simplemente abra o console administrativo (localhost:9990) e vá na opção "Deployments", nesta seção procure por um botão "add" para efetuar outro deploy, procure pelo .jar do driver e faça o deploy. Logo após a configuração não esqueça de habilita-lo para uso através da opção "enable/disable".

  * ### Criando um módulo no servidor ###
> > Geralmente mais comum no ambiente de produção, visto que uma vez criado ele estará disponível enquanto o servidor estiver iniciado.

## Configurando o banco no servidor de aplicações ##


> Agora que o driver do banco não se encontra na aplicação não é mais necessário as configurações de acesso ao banco presente no arquivo xml. E o arquivo deverá ficar da seguinte forma:

```
<persistence version="2.1"
	xmlns="http://xmlns.jcp.org/xml/ns/persistence" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/persistence http://www.oracle.com/webfolder/technetwork/jsc/xml/ns/persistence/persistence_2_1.xsd">

	<persistence-unit name="hibernate-jpa-mysql" transaction-type="JTA">

		<provider>org.hibernate.jpa.HibernatePersistenceProvider</provider>

		<!-- lista de classes -->
		<!-- <class>br.ufpi.entity.Produto</class> --> <!-- não é necessário por conta da configuração de detecção automática do hibernate -->

		<jta-data-source>java:jboss/carrinho-compra</jta-data-source>

		<properties>
			<!-- Propriedades padrões do JPA 2.1 -->
			<!-- <property name="javax.persistence.jdbc.driver" value="com.mysql.jdbc.Driver" /> -->
			<!-- <property name="javax.persistence.jdbc.url" value="jdbc:mysql://localhost:3306/carrinho_compra" /> -->
			<!-- <property name="javax.persistence.jdbc.user" value="root" /> -->
			<!-- <property name="javax.persistence.jdbc.password" value="root" /> -->

			<!-- Propriedades específicas do hibernate -->

			<property name="hibernate.archive.autodetection" value="class" />
			<property name="hibernate.hbm2ddl.auto" value="update" />
			<property name="hibernate.show_sql" value="true" />
			<property name="hibernate.format_sql" value="true" />
			<!-- <property name="hibernate.dialect" value="org.hibernate.dialect.MySQLDialect" /> --> <!-- cria as tabelas do banco no formato MyIsam -->
			<property name="hibernate.dialect" value="org.hibernate.dialect.MySQL5InnoDBDialect" /> <!-- cria as tabelas do banco no formato InnoDB -->
			<property name="hibernate.ejb.naming_strategy" value="org.hibernate.cfg.ImprovedNamingStrategy" /> <!-- cria as tabelas do banco com padronização de nomes em minúsculo independente do sistema operacional -->

		</properties>

	</persistence-unit>

</persistence> 
```

No arquivo acima podemos notar a presença de duas configurações extras
  * **transaction-type com valor "JTA"**
> > Para identificar que as transações serão controladas pelo servidor de aplicação e não mais localmente, ou seja, por sua aplicação (default RESOURCE\_LOCAL)
  * **jta-data-source**
> > Determina a onde o contexto de persistencia encontrará a conexão com o banco de dados, uma vez que ela não está mais configurado na aplicação. Esta tag apenas recebe um identificador para obter uma conexão com o banco, este identificador será configurado posteriormente dentro do servidor de aplicações.

## Configurando conexão com o banco no servidor de aplicações ##

Ao acessar o console administrativo (localhost:9990), vá na seção "Create Datasource/datasources" para criar uma conexão com o banco de dados. Clique no botão "add" para criar uma nova conexão, feito isso será apresetada uma janela para configuração. As duas primeira informações solicitadas serão:
  * **Um nome para sua conexão**
> > Nome que será apresentado na sua configuração. Ex: "teste"
  * **JNDI name**
> > Este será o identificador que será utilizado no arquivo persistence.xml para identificar onde estará a configuração no servidor, no Wildfly é necessário que o identificador siga o seguinte padrão: "java:jboss/" acrescido de um nome qualquer. Ex: "java:jboss/teste"

Logo em seguida será perguntado a classe do driver que fará acesso ao banco de dados, se a configuração do driver na seção anterior estiver correta deverá aparece as classes de drivers do Mysql listadas abaixo, caso contrário retorne a seção "Configurando driver no servidor de aplicações" e refaça seus passos.

  * mysql-connector-java-5.1.34.jar\_com.mysql.fabric.jdbc.FabricMySQLDriver\_5\_1
  * mysql-connector-java-5.1.34.jar\_com.mysql.jdbc.Driver\_5\_1

Para esta configuração escolheremos a "com.mysql.jdbc.Driver\_5\_1" e avançaremos a configuração. Na tela seguinte será necessário informar os dados de acesso ao banco, os mesmo que foram comentados no arquivo persistence.xml.
  * **url de conexão** => mesmo valor do arquivo persistence.xml
  * **usuario** => mesmo valor do arquivo persistence.xml
  * **senha** => mesmo valor do arquivo persistence.xml
  * **dominio de segurança** => deverá ficar vazio

Ao informar todos as configurações necessárias, clique no botão "Test Connection" para validar se a configuração foi bem sucedida. Se tudo ocorrer bem você deve receber a mensagem:

```
Successfully created JDBC connection.

Successfully connected to database test.
```

E agora sim poderá concluir a configuração. Caso contrário refaça os passos de configuração até obter a mensagem de validação acima. Ao término da configuração será necessário habilitar a conexão com o banco que acabara de criar, para isso selecione a conexão e clique no botão "Enable", fazendo isso agora sua aplicação deverá ter acessado a conexão através do "JNDI Name" configurado.

Configuração pronta agora nos ManagedBean's podemos ter acesso ao EntityManager apenas declarando e anotando com a anotação @PersistenteContext, conforme ilustrado abaixo:

```
@ManagedBean
public class ManagerBean {

	@PersistenceContext
	private EntityManager em;
	
	public void buscarProduto() {
		Produto produto = em.find(Produto.class, 1);
		System.out.println(produto.getDescricao());
	}
	
}
```

No exemplo acima podemos observar que o atributo "em" é apenas declarado e anotado, mas em nenhum momento é feita sua instanciação, isso é possível porque o servidor de aplicações ficará a cargo deste trabalho para você, então seu único trabalho será utilizar a instancia livremente, fácil não é ?

- Okay. Tudo funcionando, já consegui fazer consultas no meu _ManagedBen_, mas quando fui tentar persistir (salvar) dados no banco, o log indicou erro informando que uma transação era requerida. Tentei criar a transação manualmente e ele indicou que a transação não poderia ser tratada manualmente, o que eu faço agora ?

Lembra que passamos a configuração de persistencia para o servidor de aplicações !? Poís é! Todo o tratamento de instanciação e controle de transações será feito pelo banco, sendo assim você, por padrão, não poderá instanciar e abrir transações. Para fazer uso de transações será necessário o uso de outra API chamada EJB.


## EJB ##

É um componente da plataforma JEE que roda em um container de um servidor de aplicação. Seu principal objetivo consiste em fornecer um desenvolvimento rápido e simplificado de aplicações Java, com base em componentes distribuídos, transacionais, seguros e portáveis. Atualmente, na versão 3.1, o EJB tem seu futuro definido conjuntamente entre grandes empresas como IBM, Oracle e HP, como também por uma vasta comunidade de programadores numa rede mundial de colaboração sob o portal do JCP.

Para definir um EJB e ter acesso a todos os benefícios listados acima, apena precisamos anotar uma classe com uma das seguintes anotações:

@Stateless EJB que não mantem o estado entre as requisições, ou seja, uma nova instância a cada chamada de método.
@Statefull EJB que mantem o estado entre as requisições, ou seja, uma instancia única entre todas as chamadas de métodos.

```
@Stateless
public class TestEJB {

	@PersistenceContext
	private EntityManager em;

	public void salvar() {
		Produto produto = new Produto();
		produto.setDescricao("produto managedBean");
		produto.setPrecoUnitario(10d);

		em.persist(produto);
	}
	
}
```

Para fazer uso dos ejb's definidos é somente necessário a declaração de um atributo e anotá-lo com a anotação @EJB, isso indicará ao servidor de aplicações injetar uma instancia do referido EJB no atributo anotado, conforme exemplo abaixo:

```
@ManagedBean
public class ManagerBean {

	@EJB
	private TestEJB testEJB;


	public void save() {
		testEJB.salvar();
	}

}
```

**EJB Tutorial:** http://docs.oracle.com/javaee/7/tutorial/partentbeans.htm#BNBLR

## Primefaces ##


> Componentes visto:

  * SelectOneMenu
  * InputText
  * InputTextArea
  * CommandButton
    * value => nome do botão
    * action => ação que será executada no ManagedBean

> Obs:
  * a maior parte dos componentes do Primefaces possuem o atributo "id" que serve para identificar unicamente cada componente, necessário quando se quer fazer referencia a eles em um atributo "update", por exemplo.
  * a maior parte dos componentes do Primefaces tem um atributo "update" que é utilizado para atualizar (renderizar novamente) outros componentes através do identificador.

**Showcase dos componentes:** http://www.primefaces.org/showcase

## Configurações extras ##

**Correção do log do hibernate para Wildfly:**
> https://docs.jboss.org/author/display/WFLY8/Logging+Configuration

**JavaEE 7 API Maven dependency versions:**
> https://wikis.oracle.com/display/GlassFish/Java+EE+7+Maven+Coordinates
> http://blog.arungupta.me/java-ee-7-implementations-in-wildfly-tech-tip-3/
> http://wildfly.org/news/2014/11/20/WildFly82-Final-Released/

**Resolver problema de atualizações de views enquanto em desenvolvimento**
> http://stackoverflow.com/questions/5587808/how-to-use-facelets-composition-with-files-from-another-context
> http://www.warski.org/blog/2007/12/instant-facelets-changes-in-xhtml-and-no-redeploying/
> https://rmannibucau.wordpress.com/2012/10/16/tomee-maven-plugin-reload-your-app-automatically/
> http://stackoverflow.com/questions/14285260/avoid-publish-of-ejbs-and-managedbeans-when-saving-xhtml-file-in-maven-project
> http://stackoverflow.com/questions/22727986/jboss-maven-and-jsf-runtime-compilation-xhtml-files