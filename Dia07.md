# JSF e Templates #

Um dos recursos utilizados para evitar a duplicação de código dentro de sistemas desenvolvidos em JSF, é a utilização de templates. Através deste recurso é possível aproveitar parte de códigos de arquivos .xhtml, centralizando o conteúdo e facilitando a manutenção do sistema.

Para utilizar o recurso de templates é necessário a inclusão de uma nova "namespace" na tag raiz do seu arquivo .xhtml:

```
	xmlns:ui="http://java.sun.com/jsf/facelets" <!-- novo namespace -->
```

Uma vez adicionado o namespace, você terá acesso a uma série de novas tags, esta que ajudarão na construção de arquivos .xhtml reutilizáveis, dentre as principais podemos citar:
  * **ui:insert** => definir um local de conteudo que poderá ser alterado
  * **ui:define** => redefinir o conteudo de um local definido previamente
  * **ui:include** => incluir outra pagina .xhtml
  * **ui:composition** => utilizado para fazer uso dos recursos de locais dos templates


# IReport #

O JasperReports é um framework para a geração de relatórios. É uma ferramenta totalmente open source e gratuita, e a mais utilizada com esse propósito atualmente. Entre as funcionalidades do JasperReports podemos destacar:

  * É capaz de exportar relatórios para diversos formatos diferentes, tais como PDF, HTML, XML, XLS, etc.
  * Aceita diversas formas de entrada de dados, tais como um arquivo XML ou CSV, conexão com o banco de dados, uma sessão do Hibernate, uma coleção de objetos em memória, etc.
  * Permite o uso de diagramas, gráficos, e até códigos de barras, dentre outros componentes.

## Como funciona ? ##

Um aspecto importante do JasperReports é que o layout do relatório é definido em um arquivo XML, geralmente com a extensão .jrxml. Este XML possui todas as informações de formatação do relatório, e além disso, possui os campos que serão preenchidos posteriormente, de acordo com a fonte de dados utilizada (data source). Como dito anteriormente, a fonte de dados pode variar, e ser uma tabela em uma base de dados, ou ser um arquivo CSV, porém a formatação do relatório será a mesma em ambos os casos.

Os passos para gerar um relatório são bem simples. O primeiro passo é compilar o relatório em XML. Depois da compilação, o resultado é um objeto do tipo JasperReport. O próximo passo é preencher o relatório com os dados, e o resultado dessa etapa fica armazenado em um objeto do tipo JasperPrint. Esse objeto já representa o relatório finalizado, a partir dele podemos enviar para impressão diretamente, ou podemos exportar para um outro formato, tal como PDF por exemplo. Veja um diagrama ilustrando o processo completo:

![http://www.k19.com.br/artigos/wp-content/uploads/2010/11/diagrama.png](http://www.k19.com.br/artigos/wp-content/uploads/2010/11/diagrama.png)

### Tipo de arquivos ###

Arquivo gerados pelos IReport:

  * **.jrxml** => arquivo do relatório em xml
  * **.jasper** => arquivo do relatório compilado

### Tipo de dados ###

  * **JasperReport** => Representa o arquivo compilado (.jasper)
  * **JasperPrint** => Representa o relatório preenchido com a fonte de dados.

### Classes Gerenciadoras/Auxliares ###

  * **JasperCompileManager** => compila arquivos .jrxml e gera arquivos .jasper, devolve um objeto do tipo JasperReport (objeto do relatório em si)
  * **JasperFillManager** => preenche o objeto do relatório JasperReport apartir de uma fonte de dados (datasource), que poderá ser uma consulta SQL, coleção de objeto, arquivo XML, arquivo CVS, consulta HQL, dentre outros, e devolve um objeto do tipo JasperPrint para representar o relatório preenchido com os dados da fonte de dados.
  * **JasperExportManager** => exporta o relatório preenchido (JasperPrint) para os mais variados tipos de arquivos: PDF, XML, HTML.
  * **JasperPrintManager** => usado para enviar um relatório preenchido (JasperPrint) para a impressão.
  * **JasperViewer** => visualiza um relatório preenchido (JasperPrint) sem a necessidade da exportação.

### Fontes de dados ###

Todas as fontes de dados implementam a interface JRDataSource, dentre as principais implementações temos:

  * **JREmptyDataSource** => representa uma fonte de dados vazia, geralmente utilizada para informar ao relatório que a seção "No data" deverá ser exibida.
  * **JRBeanCollectionDataSource** => representa uma fonte de dados criada apartir de uma coleção de objetos java.
  * **JRResultSetDataSource** => representa uma fonte de dados criada a partir de um ResultSet.

## Criando um design (modelo) com o IReport ##

Ver referências.

### Estrutura de um relatório no IReport ###

O IReport divide o relatório em seções, cada seção é chamada de 'Banda' (Band, no inglês), e cada 'Band' possui um comportamento distinto na construção do relatório. São elas:

  * **Title** => onde fica o título do relatório;
  * **Page Header** =>  adiciona algum tipo de informação sobre o relatório (por exemplo, o ano vigente);
  * **Column Header** => onde ficam os cabeçalhos da tabela;
  * **Detail** => aqui ficam as “linhas” da tabela, a informação principal do relatório;
  * **Column Footer** => usado para o rodapé da tabela;
  * **Summary** => geralmente usado para uma conclusão, diagrama ou gráfico;
  * **Page Footer** => informações como data, página do relatório, etc.
  * **No data** => Esta seção aparecerá apenas quando a fonte de dados do relatório não retornar informação, ou for do tipo JREmptyDataSource

### Compilando e Exportando no Java ###

De posse do design do relatório (arquivo .jrxml), são necessário os seguinte passos para exportar um relatório: compilar, preencher e exportar. Ex:

```

		Collection<Produto> produtos = new ArrayList<Produto>();

		Produto p = null;
		for (int i = 0; i < 10; i++) {
			p = new Produto();
			p.setDescricao("produto " + i);
			p.setPrecoUnitario(Double.valueOf("12") + i);
			produtos.add(p);
		}

		JRDataSource datasource = new JRBeanCollectionDataSource(produtos); // definindo a fonte de dados através de uma coleção de produtos

		try {

			Map<String, Object> params = new HashMap<String, Object>(); // parametros que enviarei para o relatório
			InputStream is = TestReport.class.getResourceAsStream("/report1.jrxml"); // buscando o .jrxml do template do relatório
			String arquivoPDF = "arquivo.pdf"; // nome do arquivo que será gerado

			JasperReport jasperReport = JasperCompileManager.compileReport(is); // compilando o arquivo .jrxml e recebendo uma instancia do relatório
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, datasource); // preenchendo a instancia do relatório com a fonte de dados e recebendo uma instancia do relatório preeenchida
			JasperExportManager.exportReportToPdfFile(jasperPrint, arquivoPDF); // exportando a instancia do relatorio preenchida para .pdf

			JasperViewer.viewReport(jasperPrint); // visualizando a instancia do relatorio preenchida, ideal para sistemas desktop

		} catch (JRException e) {
			e.printStackTrace();
		}

```

## Componentes do Primefaces ##
  * **BreadCrumb** => menu de rastro do usuário
  * **Menubar** => barra de menu do sistema
  * **ResetInput** => componente para resetar os valores de uma formulário
  * **Layout** => define unidades de layout em uma pagina .xhtml

## Corrigindo conflitos entre componentes ##
Quando utilizando o componentes Layout juntamente com o componente Menubar, notamos que o menu aparece por traz do componente de Layout, impossibilitando a sua utilização. Para resolver esse problema adicionamos o seguinte código CSS em nossa aplicação:

```
<style>

.ui-layout-north {
	z-index: 20 !important;
	overflow: visible !important;
}

.ui-layout-north .ui-layout-unit-content {
	overflow: visible !important;
}

</style>
```

### Removendo bordas do componente Layout ###
Afim de proporcionar um visual mais 'clean' (limpo) em nossa aplicação fazemos uso do CSS para remover as bordas do componente de Layout.

```
<style>

.ui-layout-unit {
	border: 0px !important;
}

</style>
```
Obs:
  * Componentes do tipo menu obrigatoriamente devem está localizado dentro de um formulário para funcionar
  * Componentes do tipo butão obrigatoriamente também necessitam de um formulário para funcionar (Exceto quando o mesmo não fizer ação em um ManagedBean)



## Referências ##
  * **Tutorial IReport:** http://www.k19.com.br/artigos/relatorios-em-java-jasperreports-e-irepor/ (pt\_BR)
  * **Criação de um relatório básico com IReport:** https://community.jaspersoft.com/wiki/basic-report-creation (en)
  * **Documentação Oficial:** http://community.jaspersoft.com/documentation?version=13758 (en)
  * **Template com JSF:** http://www.devmedia.com.br/java-facelets-aprenda-a-criar-templates/28888 (pt\_BR)
  * **Jquery icons:** http://api.jqueryui.com/theming/icons/
  * **Formatação de decimais:** http://docs.oracle.com/javase/tutorial/i18n/format/decimalFormat.html