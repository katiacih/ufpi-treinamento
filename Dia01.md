# Maven #

O Maven é uma ferramenta de gerenciamento, construção e implantação de projetos muito interessante, que te ajuda no processo de gerenciamento de dependências e no de build, geração de relatórios e de documentação.

  * Gerenciamento
    * Gerenciamento de Dependencias
    * Gerenciamento de Builds
    * Gerenciamento de Relatórios
    * Gerenciamento de Documentação

  * Construção
  * Implantação

# Configuração, Instalação e Teste #

  * **Maven**: http://maven.apache.org/download.html
  * **Java7**: http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html

# Configurar variáveis de ambiente #

  * M2\_HOME="Diretorio/de/instalação"
  * JAVA\_HOME="Diretorio/de/instalação"

Adicionar as variáveis acima ao "Path" do sistema acrescidos do diretório "bin".

**Ex:**

  * **windows:** ...;%M2\_HOME%\bin
  * **linux:** PATH=$PATH:$M2\_HOME\bin

# Verificar funcionamento #

No terminal (CMD) executar o seguinte comando:

  * **java -version**
  * **mvn --version**

# Estrutura #

A unidade básica de configuração do Maven é um arquivo chamado pom.xml, que deve ficar na raiz do seu projeto. Ele é um arquivo conhecido como Project Object Model: lá você declara a estrutura, dependências e características do seu projeto.

```
<project>
  <modelVersion>4.0.0</modelVersion>
  <groupId>br.ufpi</groupId>
  <artifactId>teste</artifactId>
  <version>0.0.1-SNAPSHOT</version>
</project>
```

Que contém apenas a identificação do projeto, e uma informação a mais: modelVersion, que é a identificação da versão do arquivo pom.xml e deve ser sempre 4.0.0. A identificação do projeto consiste em três informações:

**groupId**: um identificador da empresa/grupo ao qual o projeto pertence. Geralmente o nome do site da empresa/grupo ao contrário. Ex: br.ufpi.
**artifactId**: o nome do projeto. Ex: teste.
**version**: a versão atual do projeto. Ex: 0.0.1-SNAPSHOT.

# Criação de um projeto web utilizando Maven #

  * **Eclipse**:
> > File => New => Maven Project => Selecionar opção para "simple project" para não usar nenhum ArcheType (planta) de projeto => Adicionar informações (ArtifactId, GroupId, Version, Package)

  * **Linha de Comando**:
```
		mvn archetype:generate \
			-DarchetypeGroupId=org.apache.maven.archetypes \
			-DarchetypeArtifactId=maven-archetype-webapp \
			-DgroupId=com.mycompany.app \
			-DartifactId=my-webapp
```

# Ciclo de vida #


> Agora com um projeto Maven já preparado, vamos para a principal funcionalidade: o build. O build do Maven é baseado no conceito de ciclo de vida: o processo de construção e distribuição da sua aplicação é dividido em partes bem definidas chamadas fases, seguindo um ciclo. O ciclo padrão é o seguinte:

  * **compile** – compila o código fonte do projeto
  * **test** – executa os testes unitários do código compilado, usando uma ferramenta de testes unitários, como o junit.
  * **package** – empacota o código compilado de acordo com o empacotamento escolhido, por exemplo, em JAR.
  * **integration-test** – processa e faz o deploy do pacote em um ambiente onde os testes de integração podem ser rodados.
  * **install** – instala o pacote no repositório local, para ser usado como dependência de outros projetos locais
  * **deploy** – feito em ambiente de integração ou de release, copia o pacote final para um repositório remoto para ser compartilhado entre desenvolvedores e projetos

Obs: O ciclo default acima não se limita apenas as estas etapas, vide referencia de documentação.

http://maven.apache.org/guides/introduction/introduction-to-the-lifecycle.html#Lifecycle_Reference


# Escopos #

Escopo de uma dependencia é usado para limitar a transitividade de uma dependência, e também para afetar o classpath usado para várias tarefas de construção.

Abaixo podemos ver os 6 escopos disponíveis:

  * **compile**
> Esse é o escopo padrão, usado quando nenhum escopo é definido. Dependencias com o escopo "compile" estão disponíveis em todos os classpaths de um projeto.

  * **provided**
> Esse escopo é muito parecido com o "compile", porém espera que alguem provenha a dependency em runtime, seja a JDK ou o container da aplicação.

  * **runtime**
> Este escopo indica que a dependencia não é requerida para compilação, mas é para execução.

  * **test**
> Este escopo indica que a dependencia não é requerida para o uso normal da aplicação, e é somente disponível para a compilação/execução de testes.

  * **system**
> Este escopo é similar ao "provided", com a diferenção que você tem que prover o JAR que contenha a dependencia explicitamente.

  * **import** (disponível apenas nas versão do Maven 2.0.9 or superior)


http://maven.apache.org/guides/introduction/introduction-to-dependency-mechanism.html#Dependency_Scope


# Plugins #

  * **maven-compile-plugin**

> documentação:
> http://maven.apache.org/plugins/maven-compiler-plugin/

> referencia de uso:
> http://maven.apache.org/plugins/maven-compiler-plugin/examples/set-compiler-source-and-target.html

  * **jboss-as-maven-plugin**

> documentação:
> https://docs.jboss.org/jbossas/7/plugins/maven/latest/

> referencia de uso:
> https://docs.jboss.org/jbossas/7/plugins/maven/latest/examples/deployment-example.html

  * **maven-glassfish-plugin**

> documentação:
> https://maven-glassfish-plugin.java.net/

> referencia de uso:
> https://maven-glassfish-plugin.java.net/examples/complete.html


# Referencias Extras #

> http://maven.apache.org/plugins/