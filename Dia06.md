## JPA Entity Listeners ##

EntityListeners permite você invocar algum código antes ou depois de certas operações de persistencia.

### Detalhes ###
  * **@PrePersist**

Chamado imediatamente antes que o objeto seja salvo na base de dados.

  * **@PostPersist**

Chamado imediatamente depois que o objeto é salvo na base de dados.

  * **@PreRemove**

Chamado imediatamente antes que o objeto seja removido da base de dados.

  * **@PostRemove**

Chamado imediatamente depois que o objeto é removido da base de dados.

  * **@PreUpdate**

Chamado imediatamente antes que o objeto seja atualizado.

  * **@PostUpdate**

Chamado imediatamente depois que o objeto é atualizado.

### Codigo de Exemplo ###
Para habititar o uso de EntityListeners em uma Entidade, apenas use a anotação @EntityListners na referida classe, através dela você listará as classes que ficarão observando as operações de persistencia sobre a entidade.

```
@Entity
@EntityListeners({ProdutoListener.class, OutroListenerProduto.class})
public class Produto {
// ...
}
```

Você pode listar varias classes seperando-as por , (virgula)

### Exemplo de um EntityListener ###
```
public class ProdutoListener {

    @PrePersist
    public void prePersist(Produto produto) {
        Date now = new Date();
        produto.setDataCriacao(now);
        produto.setDataAtualizacao(now);
    }

    @PreUpdate
    public void preUpdate(Produto produto) {
        produto.setDataAtualizacao(new Date());
    }
}
```

No exemplo acima o EntityListener fica observando as operações de persist e update nos objetos da classe produto, e atribui a data da criação e atualização aos objetos antes que eles sejam salvos ou atualizados no banco.


## JSF I18N (Internacionalization) = Internacionalização ##

Recurso utilizado para padronização de textos (rótulo, nomes, descrições) dentro da aplicação, bem como para tradução dos mesmos em outras linguagens.

### Definindo arquivos de i18n ###

São arquivo .properties compostos apenas de chaves e valores.

mensagens.properties
```
ok=Confirmar
update=Atualizar
cancel=Cancelar
```

### Definindo classes de i18n ###

Uma classe qualquer em Java que estenda a classe ResourceBundle, feito isso você será obrigado a sobrescrever dois métodos.

```
public class MensagemBundle extends ResourceBundle {
	@Override
	protected Object handleGetObject(String key) {
		// deve retorna o valor correspondente a chave do parametro
		return valor;
	}

	@Override
	public Enumeration<String> getKeys() {
		// deve retorna a lista (enumeration) das chaves disponíveis
		return null;
	}
}
```

### Configurando os arquivos/classes de i18n ###

A configuração pode ser feita de duas maneiras, local e global:

  * **Local:**
> > classe:
```
<f:loadBundle var="msg" basename="pacote.da.classe.NomeDaClasse"></f:loadBundle>
```
> > arquivo:
```
<f:loadBundle var="msg" basename="nomeDoArquivoPropertiesSemExtensao"></f:loadBundle>
```
  * **Global:**
> > Configuração feita dentro do arquivo faces-config.xml através das tag "resource-bundle" e "message-bundle".

  * **resource-bundle** => identifica recursos que serão usados para internacionalição do sistema, o JSF por padrão já utiliza esse mecanismo para suas mensagens de erros internas, sendo assim possível sobrescrevê-las.
    * **base-name**
> > Nome do arquivo .properties ou FQN (full qualify name) da Classe (pacote+nome da classe), com a restrição que a classe informada deve obrigatoriamente estender ResourceBundle.
    * **var**
> > Identificador que será usado como referencia nos arquivos .xhtml

```
<application>
   <resource-bundle>
      <base-name>entidades</base-name>
      <var>ent</var>
   </resource-bundle>
   <resource-bundle>
      <base-name>componentes</base-name>
      <var>com</var>
   </resource-bundle>
</application>
```

### Definição de Locale na aplicação ###

Através do arquivo faces-config.xml é possível simular a utilização do sistema em outras linguagens, recurso interessante para testar o recurso de internacionalização e definição do locale padrão que sua aplicação irá utilizar.

```
<application>
   <locale-config>
      <default-locale>pt_BR</default-locale>
   </locale-config>
</application>
```


## Messages de Validação do JSF ##

Podem ser encontradas detalhamente na especificação do JSF: http://download.oracle.com/otndocs/jcp/jsf-2_2-fr-spec/index.html

## ManagedBeans Escopos ##

  * **@ViewScoped** Mesma instancia enquanto o navegador não atualizar ou mudar de página
  * **@RequestScoped** A cada nova requisição uma nova instancia será criada
  * **@ApplicationScoped** Enquanto o servidor estiver "ligado" a mesma instancia será utilizada
  * **@SessionScoped** Enquanto durar a sessão, a instancia do bean será utilizada.

Obs: Todos os escopos vistos foram do pacote: **javax.faces.bean**

## Componentes ##
  * datatable
    * column
    * facet
  * panel