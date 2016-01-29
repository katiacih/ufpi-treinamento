# JPA (Java Persistence API) #

  * Persistence.xml
  * Entity Class
  * EntityManager e EntityManagerFactory

## Configurando o persistence.xml ##

Em qual banco de dados vamos gravar nossas entidades? Qual é o login? Qual é a senha? O JPA necessita dessas configurações, e para isso criaremos o arquivo persistence.xml.

```
<persistence version="2.1"
	xmlns="http://xmlns.jcp.org/xml/ns/persistence" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/persistence http://www.oracle.com/webfolder/technetwork/jsc/xml/ns/persistence/persistence_2_1.xsd">

	<persistence-unit name="hibernate-jpa-mysql">

		<!-- provedor/implementacao do JPA -->
		<provider>org.hibernate.jpa.HibernatePersistenceProvider</provider>

		<properties>
		
			<!-- Propriedades padrões do JPA 2.1 -->
			<property name="javax.persistence.jdbc.driver" value="com.mysql.jdbc.Driver" /> <!-- classe relativa ao driver de conexão com o banco, muda dependendo do banco escolhido -->
			<property name="javax.persistence.jdbc.url" value="jdbc:mysql://localhost:3306/carrinho_compra" /> <!-- url para acesso ao banco, muda dependendo do banco escolhido -->
			<property name="javax.persistence.jdbc.user" value="root" /> <!-- usuário com permissão de acesso ao banco -->
			<property name="javax.persistence.jdbc.password" value="root" /> <!-- senha do banco -->

			<!-- Propriedades específicas do hibernate -->
			<property name="hibernate.archive.autodetection" value="class" /> <!-- auto detecta as classes mapeadas no projeto -->
			<property name="hibernate.hbm2ddl.auto" value="update" /> <!-- habilita a geração automática das tabelas do banco a partir das 'Entity Classes' -->
			<property name="hibernate.show_sql" value="true" /> <!-- habita a SQL executa no banco no log para cada uma das operações -->
			<property name="hibernate.format_sql" value="true" /> <!-- formata a saída da SQL no log para um formato mais legível -->
			<property name="hibernate.dialect" value="org.hibernate.dialect.MySQLDialect" /> <!-- define o dialeto (dicionário) do banco escolhido -->
		</properties>

	</persistence-unit>

</persistence>
```


## Definindo uma 'JPA Entity Class' ##

Para ser capaz de armazenar objetos em um banco de dados relacional usando JPA primeiramente precisamos definir uma 'Entity Class'. Uma JPA Entity Class nada mais é que uma POJO (Plain Old Java Object) class, ou seja, uma classe java qualquer que é marcada (anotada) para ter a capacidade de representar objetos na base de dados.

Ex:
```
@Entity
public class Produto {

	private String descricao;
	private Double valor;
	
	// get's and setter's

}
```

Como você pode ver na classe acima a unica adição para que a classe represente o formato JPA foi a adição da anotação @Entity na classe, que marca que a classe agora é uma 'Entity Class'.

## Campos persistentes em uma 'Entity Class' ##

Armazenar objetos em um banco de dados não armazena métodos ou código. Somente o estado persistente do objeto gerado a partir da 'Entity Class', representado por seus campos persistentes, é armazenado. Por padrão, qualquer campos que não for declarado como 'static' ou 'transient' é um campo persistente. Em nosso exemplo os campos persistentes de Produto são 'descricao' e 'valor'. Os valores que estiverem armazenados nesses dois campos serão armazenados no banco quando o objeto for persistido (salvo).


### Entity Classes ###
  * **Restrições:**
    * Devem ser uma classe de nível superior. (não uma classe interna ou classe aninhada)
    * Devem ter uma construtor padrão (sem argumentos)
    * Não podem ser 'final' e não pode conter métodos finais ou variáveis de instancia finais.

  * **Possibilidades**:
    * Podem estender outras 'Entity Classes'
    * Podem estender classes que não são 'Entity Classes', porém não podem estender classes de sistemas, tal como ArrayList.
    * Podem implementar qualquer interface.
    * Podem conter construtores, métodos, campos e tipos como qualquer modificador de acesso (public, protected, package ou private).
    * Podem ser concretas ou abstratas.


## Relacionamentos ##

Os relacionamentos entre as entidades de domínio devem ser expressos na modelagem através de vínculos entre as classes. Para isso podemos definir quatro tipo de relacionamentos entre classe de acordo com a cardinalidade, são eles:

  * One to One (Um para Um)
  * One to Many (Um para Muitos)
  * Many to One (Muitos para Um)
  * Many to Many (Muitos para Muitos)

Relacionamento por padrão executam carregamento retardado (Lazy Loading), já campos básicos possuem carregamento imediato (Eager Loading).

## Herança ##

> A JPA define três estratégias para se mapear o uso de herança em uma aplicação, são elas:
    * Single Table (Tabela única)
    * Joined (Junção)
    * Table per Class (Tabela por classe)


## Principais anotações ##

  * **@Table** => Especifica detalhes sobre a tabela no banco.
  * **@Entity** => Define uma classe como 'Entity Class'
  * **@Column** => Especifica detalhes sobre uma coluna de uma tabela.
  * **@Basic** => Define como tipo básico o atributo.
  * **@Id** => Determina a chave primária de uma Entity Class.
  * **@GeneratedValue** => Define que o valor de uma chave primária será gerado automaticamente.
  * **@Timestamp** => Especifica o tipo do formato do campo data que será salvo no banco.
  * **@Version** => Especifica o campo que utilizará para controlar o 'Optimistic Lock'. O campo será preenchido automaticamente e deve ser um desses tipos: int, Integer, short, Short, long, Long, java.sql.Timestamp.
  * **@ElementCollection** => Especifca detalhes sobre uma coleção de tipos básicos.
  * **@CollectionTable** => Especifica a tabela que é usada no mapeamento de coleções básicas ou tipos embutidos.
  * **@ConstructorResult** => Usa em conjunto com @SqlResultSetMapping serve para mapear o resultado da consulta para um construtor especifico de uma classe.
  * **@Convert** => Especifica uma converção de um tipo básico.
  * **@Converts** => Usada para especificar um grupo de converts.
  * **@JoinColumn** => Especifica detalhes sobre a coluna de junção do relacionamento.
  * **@OneToOne** => Mapeia o relacionamento entre classes do tipo 'um-para-um'
  * **@OneToMany** => Mapeia o relacionamento entre classes do tipo 'um-para-muitos'
  * **@ManyToOne** => Mapeia o relacionamento entre classes do tipo 'muitos-para-um'
  * **@ManyToMany** => Mapeia o relacionamento entre classes do tipo 'muitos-para-muitos'
  * **@Lob** => Utilizado para campos com formato binário
  * **@Inheritance** => Define a estratégia a ser utilizada sobre o compartamento da herança da entidade no banco de dados.
  * **@JoinTable** => Especifica detalhes sobre tabela de junção
  * **@JoinColumns** => Especifica detalhes sobre colunas de junção, geralmente quando há o uso de chaves compostas. Para especificar mais de uma @JoinColumn.
  * **@Enumerated** => Especifica detalhes para atributos do tipo enum.
  * **@Embeddable** => Classes anotadas com esta anotação terão cada um de seus campos embutidos na entidade que for declarada como associação.
  * **@Embedded** => Especifica que um campo de uma entidade é instancia de uma classe embutida. (Embeddable class)
  * **@PrimaryKeyJoinColumn** => Especifica a chave primária que é utilizada como chave estrageira na junção (relacionamento) com outra tabela.
  * **@PrimaryKeyJoinColumns** => Para especificar mais de uma @PrimaryKeyJoinColumn.
  * **@SecondaryTable** => Especifica outras tabelas que podem prover dados para a 'EntityClass'.
  * **@SecondaryTables** => Para especificar mais de uma @SecondaryTable.
  * **@SqlResultSetMapping** => Especifica o mapeamento de um resultado de uma query nativa (SQL).
```
    Query q = em.createNativeQuery(
        "SELECT o.id AS order_id, " +
            "o.quantity AS order_quantity, " +
            "o.item AS order_item, " +
            "i.name AS item_name, " +
        "FROM Order o, Item i " +
        "WHERE (order_quantity > 25) AND (order_item = i.id)",
    "OrderResults");

    @SqlResultSetMapping(name="OrderResults",
        entities={
            @EntityResult(entityClass=com.acme.Order.class, fields={
                @FieldResult(name="id", column="order_id"),
                @FieldResult(name="quantity", column="order_quantity"),
                @FieldResult(name="item", column="order_item")})},
        columns={
            @ColumnResult(name="item_name")}
    ) 
```
  * **@NamedEntityGraph** => permite alterar o tipo de inicialização dos atributos definido na classe (Lazy ou Eager)
  * **@Index** => Permite a criação de indice no banco de dados.

## Obtendo uma EntityManagerFactory ##

Para utilização do EntityManagerFactory fora do servidor de aplicação, usamos da seguinte forma:

```
EntityManagerFactory emf = Persistence.createEntityManagerFactory("hibernate-jpa-mysql");
```

Lembrando que o parâmetro para a chamada do método acima é o nome configurado na unidade de persistencia (**persistence-unit) dentro do arquivo**persistence.xml**.**

## Obtendo um EntityManager ##

De posse da EntityManagerFactory chamamos o método _createEntityManager_

```
EntityManager em = emf.createEntityManager();
```

## Principais métodos do EntityManager ##

```
Produto produto = ...
String consultaJPQL = ...
List<Produto> produtos = null;

EntityTransaction tx = em.getTransaction();

try {

tx.begin(); // inicia uma transação, transação entra no estado de ativa

em.persist(produto);
em.merge(produto);
em.remove(produto);

tx.commit(); // efetiva uma transação, transação deixa de ser ativa

} finally {
   if (tx.isActive()) // verifica se a transação ainda está ativa
      tx.rollback(); // desfaz uma transação, deixa de ser ativa
}

produto = em.find(Produto.class, 1);
produto = em.getReference(Produto.class, 1);

Query queryJPQL = em.createQuery(consultaJPQL);
produtos = (List<Produto>) queryJPQL.getResultList();

Query queryNativa = em.createNativeQuery(consultaNativa);
List<Object[]> resultado = queryNativa.getResultList();

TypedQuery<Produto> typedQuery = em.createQuery(consultaJPQL, Produto.class);
produtos = typedQuery.getResultList();

em.close(); // fecha o EntityManager, liberando os recurso alocados em memória.
emf.close() // fecha a EntityManagerFactory, liberando os recurso alocados em memória.

```

  * **getTransaction**: usado para obter uma transação, esta necessária para a execução do métodos de persist, merge e remove, consultas podem ser feitas sem uma transação ativa.
  * **persist**: usado para salvar objetos na base de dados
  * **merge**: usado para atualizar objetos na base de dados
  * **refresh**: usado para sincronizar o objeto com a base de dados.
  * **remove**: usado para remover um objeto da base de dados.
  * **find**: usado para buscar um objeto na base de dados através da chave primária.
  * **getReference**: usado para obter um referencia para um objeto na base de dados.
  * **createQuery**: usado para obter uma instancia de Query para executar consultas via JPQL, seu retorno é uma lista do objeto que se está procurando.
  * **createQuery (dois parametros)**: mesma finalidade acima, com uma pequena diferença que também é passado a classe de retorno dos objetos no momento da instanciação, afim de trazer o resultado com a tipagem correta, evitando o uso de casting's.
  * **createNativeQuery**: usado para efetuar consultas nativas do banco, seu retorno padrão é uma Lista de Array de Objetos (List<Object[.md](.md)>).

## Referências ##

  * Tutorial JPA: http://www.objectdb.com/java/jpa
  * JPA e ORM's: http://www.caelum.com.br/apostila-java-web/uma-introducao-pratica-ao-jpa-com-hibernate/#14-2-java-persistence-api-e-frameworks-orm