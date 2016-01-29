# DynamicJasper #

## Configuração ##

Para ter acesso as funcionalidades do DynamicJasper é necessário adicionar a dependencia no pom.xml. O detalhe é que esta dependência não está localizada no repositório central no maven, somente está disponível do repositório próprio da dynamicjasper, por conta disso nosso arquivo pom.xml deverá fazer referência a outro repositório diferente do padrão do maven. Para fazer isso basta apenas adicionar a tag 

&lt;repositories&gt;

 para listar o(s) novo(s) repositorio(s).

```
	<repositories>
		<repository>
			<id>fdvsolution.public</id>
			<url>http://archiva.fdvs.com.ar/repository/public1/</url> <!-- repositorio do dynamicjasper -->
		</repository>
	</repositories>
```

Após feito essa configuração você poderá adicionar normalmente a dependência para a biblioteca.

```
		<!-- dependencia do DynamicJasper -->
		<dependency>
			<groupId>ar.com.fdvs</groupId>
			<artifactId>DynamicJasper</artifactId>
			<version>5.0.0</version>
		</dependency>
```

## Utilização ##

Para criação de relatórios dinâmicos apenas necessitamos de uma instancia da classe "FastReportBuilder", através dela faremos as principais configurações:

```

AbstractColumn colunaDescricao = ColumnBuilder.getNew() // coluna referente ao atributo "descricao" da entidade produto
		.setTitle("Descrição") // definindo cabeçalho da coluna
		.setColumnProperty("descricao", String.class) // definindo as propriedades da coluna (nome e tipo), o nome deverá ser igual ao atributo na classe produto
		.build(); // construindo a coluna
		
AbstractColumn colunaPreco = ColumnBuilder.getNew() // coluna refente ao atributo "precoUnitario" da entidade produto
		.setTitle("Preço") // definindo cabeçalho da coluna
		.setColumnProperty("precoUnitario", Double.class) // propriedades da coluna
		.build(); // construindo a coluna
		
// Depois de configurar todas as propriedades desejadas é necessário chamar o método "build()" para ele retornar uma instancia de uma coluna.

FastReportBuilder frb = new FastReportBuilder(); // objeto que define o construtor do relatório dinamico
frb.setTitle("Relatório de Produtos"); // definindo o titulo do relatório
frb.addColumn(colunaDescricao); // adicionando uma coluna previamente criada
frb.addColumn(colunaPreco); // adicionando uma coluna previamente criada

frb.setPrintBackgroundOnOddRows(true); // define cores em forma de zebra no relatório
frb.setUseFullPageWidth(true); // relatório passará a utilizar 100% da largura da página

DynamicReport dr = frb.build(); // constrói o relatório dinâmico

Map<String, Object> params = new HashMap<String, Object>(); // mapa para armazenar parametros para o relatório

try {
	JasperReport jr = DynamicJasperHelper.generateJasperReport(dr, new ClassicLayoutManager(), params); // construção do relatório

	JasperPrint jasperPrint = JasperFillManager.fillReport(jr, params, source); // construção do relatório preenchido
	
	String tmp = System.getProperty("java.io.tmpdir"); // obtem o caminho do diretorio temporário do SO

	JasperExportManager.exportReportToPdfFile(jasperPrint, tmp + "/Relatorio.pdf"); // exporta o relatório em PDF para o diretório temporário
	
} catch (JRException e) {
	e.printStackTrace();
}
 
```

## Fazendo download do relatório gerado ##

ManagedBean

Deverá retornar um objeto do tipo StreamedContent

```

// método no ManagedBean
public StreamedContent getDownload() {
	try {
		this.produtoEJB.gerarRelatorio(); // método que gera o relatório no diretório temporário
		String path = System.getProperty("java.io.tmpdir") + "/Relatorio.pdf"; // referencia para o caminho do arquivo gerado
		java.io.File f = new java.io.File(path); // referencia para o arquivo gerado
		InputStream stream = new java.io.FileInputStream(f); // referencia para a stream do arquivo gerado

		return new DefaultStreamedContent(stream, "application/pdf", "Relatório.pdf"); // parametros: stream do arquivo, tipo do arquivo (mimetype), nome do arquivo de download
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	}

	return null;
}

```

.xhtml

```

<!-- botão para realizar o download do relatório gerado -->
<p:commandButton value="Gerar Relatório de Produtos" 
	ajax="false" <!-- para utilizar a tag de download é necessário que este atributo esteja "false" -->
	icon="ui-icon-arrowthick-1-s" 
	immediate="true"> <!-- define que o botão não passará pela fase de validação -->
	<p:fileDownload value="#{produtoBean.download}" /> <!-- tag especifica para a operação de download -->
</p:commandButton>

```

# Componentes #
  * **fileDownload** => faz download de arquivos
  * **media** => exibe determinados tipos de arquivos

## Referências ##
  * **DynamicJasper:** http://dynamicjasper.com/