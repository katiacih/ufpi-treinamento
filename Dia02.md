# MySQL #

  * **Instalação**: http://dev.mysql.com/downloads/mysql/
    * **Windows**: Next, Next, Next and Install =D
    * **Linux**: apt-get install mysql-server

  * **Commandos Básicos:**

```
mysql -u USUAURIO -p 
```
Utilizado para logar no console administrativo do Mysql, e ao executar o comando será solicitado a senha para acesso. Obs: o usuário e senha são os mesmos configurados na instalação do BD (Banco de Dados).

```
create database NOME_DO_BANCO; 
```
Criar um banco de dados

```
use NOME_DO_BANCO; 
```
Passar a utilizar um banco de dados, necessário para execuções de comandos em um banco de dados específicos

# Jboss Wildfly #

  * **Instalação:** http://wildfly.org/downloads/

  * **Configuração**
    * standalone => inicializa o servidor
    * add-user => cria um usuário

Obs: No windows utilizar a versão ".bat" dos arquivos, no linux usar a versão ".sh". Ex:
  * ./add-user.sh (linux)
  * ./add-user.bat (windows)
  * ./standalone.sh (linux)
  * ./standalone.bat (windows)

# SCM #

Software Configuration Management

### Dentre os principais podemos citar ###
  * Subversion (SVN)
  * Git
  * Mercurial
  * CVS

### Funcionalidades Básicas do SVN ###
  * **Checkout**: Obter uma cópia do projeto do repositório
  * **Commit**: Enviar informações ao repositório
  * **Update**: Receber informações do repositório
  * **Synchronize**: Verificar as diferenças entre o projeto local e o projeto remoto (repositório)
  * **Edit Conflits**: Resolver conflitos quandos as informações de enviar e receber conflitam.

### Boas Práticas de utilização do SVN ###
  * Sempre _commit_ com comentários objetivos.
  * Sempre faça _synchronize_ antes de efetuar seus _commits_ e _updates_
  * Tenha cautela ao utilizar as opções de _override and commit_ e _override and update_, pois as mesma são perigosas e podem lhe causar problemas.
  * Mantenha a calma, pois você agora está usando um controlador de versões e na maioria dos casos tudo pode ser recuperado.

### SVN no Eclipse ###
Para utilização do Subversion pelo eclipse é necessário a instalação de um plugin para adicionar novas funcionalidades ao eclipse, dentre os mais conhecidos temos:
  * **Subclipse**: http://subclipse.tigris.org/servlets/ProjectProcess?pageID=p4wYuA
  * **Subversive**: http://eclipse.org/subversive/

Ambos os plugins podem ser instalados diretamente pelo eclipse através da opção _"Marketplace"_ (Help => Marketplace).
Após a instalação do plugin será perguntado o tipo de _"connector"_ que deve ser usado, os mais comuns são:
  * **SVN Kit**
  * **JavaHL**
Na demonstração em laboratório utilizamos a versão 1.7.13 do SVN Kit.

# Modelo DER (Diagrama Entidade Relacionamento) #

O Modelo Entidade Relacionamento (também chamado Modelo ER, ou simplesmente MER), como o nome sugere, é um modelo conceitual utilizado na Engenharia de Software para descrever os objetos (entidades) envolvidos em um domínio de negócios, com suas características (atributos) e como elas se relacionam entre si (relacionamentos).

Em laboratório construímos o modelo base que será utilizado para a construção da aplicação de carrinho de compras, segue abaixo as entidades e atributos que foram definidos:

  * Produto
    * id
    * descrição
    * valor\_unitário
    * tipo (perecível ou não-perecível)

  * Compra
    * id
    * data\_compra
    * cliente

  * Cliente
    * id
    * nome
    * cpf
    * email
    * senha
    * data\_nascimento
    * endereço
    * telefone

  * Item\_Compra
    * id
    * produto
    * compra

# Referências Extras #
  * **Tutorial Subversive:** http://www.cs.wustl.edu/~cytron/cse132/HelpDocs/Subversive/subversive.htm
  * **Documentação Subversion:** http://svnbook.red-bean.com/en/1.7/svn-book.html
  * **JSR e API's:** http://www.oracle.com/technetwork/java/javaee/tech/index-jsp-142185.html
  * **O que mudou ? Java 7:** http://www.oracle.com/technetwork/java/javase/jdk7-relnotes-418459.html
  * **Eclipse + Java 7:** http://www.eclipse.org/jdt/ui/r3_8/Java7news/whats-new-java-7.html