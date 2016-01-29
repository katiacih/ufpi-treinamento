# Bean Validations #

As anotações estão presentes no pacote "javax.validation.constraints", e podem ser usadas para definir restrições nas entidades do sistema.

  * **@AssertFalse** => Deve ser falso
  * **@AssertTrue** => Deve ser verdadeiro
  * **@DecimalMax** => O mesmo que @Max só que aceita decimais.
  * **@DecimalMin** => O mesmo que @Min só que aceita decimais.
  * **@Digits** => Deve ser um número com "interger" (quantidade de digitos antes da virgula) e com "fraction" (quantidade de digito depois da virgula)
  * **@Future** => Aceita somente valores de datas futuras
  * **@Past** => Aceita somente valores de datas passadas
  * **@Max** => Define o valor máximo para o atributo (inclusive)
  * **@Min** => Define o valor mínimo para o atributo (inclusive)
  * **@NotNull** => Obriga o valor ser diferente de nulo
  * **@Null** => Obriga o valor ser nulo
  * **@Pattern** => usa ER (Expressões Regulares) para realizar a validação
  * **@Size** => Define limites (minimo e maximo) de elementos para os tipos: Collection, Array, Map, CharSequence (nesse caso a quantidade de caracteres será avaliada)

## Referência ##
  * **JavaEE 7:** http://docs.oracle.com/javaee/7/tutorial/bean-validation001.htm#GIRCZ
  * **Topicos Avançados:** http://docs.oracle.com/javaee/7/tutorial/bean-validation-advanced001.htm#GKFGX

# Construindo carrinho de compras #

Projeto carrinho de compras disponível no repositório, neste projeto estão contempladas as tecnologias/ferramentas:

  * Maven
  * JSF
    * Primefaces
  * JTA
    * CMT
    * BMT
      * UserTransaction
  * JPA
    * Entity
    * EntityListeners
    * Hibernate
  * CDI
    * NamedBeans
    * Inject
  * BeansValidation
  * EJB
    * Stateless
    * Statefull
  * Servlet
    * WebFilter
    * WebServlet
  * Wildfly

# Referência extras #
  * **Mais componentes para o primefaces:** http://www.primefaces.org/showcase-ext/views/home.jsf
  * **Biblioteca de Recursos:** http://www.mkyong.com/jsf2/resources-library-in-jsf-2-0/