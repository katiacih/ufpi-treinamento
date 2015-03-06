package br.ufpi.ejbs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.builders.ColumnBuilder;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import br.ufpi.entidades.Produto;

@Stateless
public class ProdutoEJB {

	@PersistenceContext
	private EntityManager em;

	public void deletar(Produto produto) {
		em.remove(em.merge(produto));
	}

	public void salvar(Produto produto) {
		em.merge(produto);
	}

	public void gerarRelatorio() {
		TypedQuery<Produto> query = em.createQuery("SELECT p FROM Produto p",
				Produto.class);
		List<Produto> produtos = query.getResultList();

		JRDataSource source = new JRBeanCollectionDataSource(produtos);

		AbstractColumn colunaDescricao = ColumnBuilder.getNew()
				.setTitle("Descrição")
				.setColumnProperty("descricao", String.class).build();
		AbstractColumn colunaPreco = ColumnBuilder.getNew().setTitle("Preço")
				.setColumnProperty("precoUnitario", Double.class).build();

		FastReportBuilder frb = new FastReportBuilder();
		frb.setTitle("Relatório de Produtos");
		frb.addColumn(colunaDescricao);
		frb.addColumn(colunaPreco);

		frb.setPrintBackgroundOnOddRows(true);
		frb.setUseFullPageWidth(true);

		Map<String, Object> params = new HashMap<String, Object>();

		DynamicReport dr = frb.build();

		JasperPrint jasperPrint = null;

		try {
			JasperReport jr = DynamicJasperHelper.generateJasperReport(dr,
					new ClassicLayoutManager(), params);

			jasperPrint = JasperFillManager.fillReport(jr, params, source);
			
			String tmp = System.getProperty("java.io.tmpdir");

			JasperExportManager.exportReportToPdfFile(jasperPrint,
					tmp + "/Relatorio.pdf");
		} catch (JRException e) {
			e.printStackTrace();
		}

	}

}
