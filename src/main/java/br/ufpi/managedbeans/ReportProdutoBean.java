package br.ufpi.managedbeans;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import br.ufpi.ejbs.ProdutoEJB;

@Named
@RequestScoped
public class ReportProdutoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private ProdutoEJB produtoEJB;

	public StreamedContent getDownload() {
		try {
			this.produtoEJB.gerarRelatorio();
			String path = System.getProperty("java.io.tmpdir")
					+ "/Relatorio.pdf";
			java.io.File f = new java.io.File(path);
			InputStream stream = new java.io.FileInputStream(f);

			return new DefaultStreamedContent(stream, "application/pdf",
					"Relatório.pdf");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return null;
	}

}
