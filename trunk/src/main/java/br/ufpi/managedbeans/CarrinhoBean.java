package br.ufpi.managedbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.primefaces.event.DragDropEvent;

import br.ufpi.entidades.ItemCompra;
import br.ufpi.entidades.Produto;

@Named
@SessionScoped
public class CarrinhoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<ItemCompra> itemCarrinho;

	private Produto produtoSelecionado;

	public CarrinhoBean() {

	}

	@PostConstruct
	public void depoisDoContrutor() {
		itemCarrinho = new ArrayList<ItemCompra>();
	}

	public void quandoSelecionarUmProduto(DragDropEvent ddEvent) {
		Produto produto = (Produto) ddEvent.getData();

		incrementarProdutoNoCarrinho(produto);

	}

	private void incrementarProdutoNoCarrinho(Produto produto) {
		ItemCompra itemCompraProduto = null;
		for (ItemCompra itemCompra : itemCarrinho) {
			if (itemCompra.getProduto().getId().equals(produto.getId())) {
				itemCompraProduto = itemCompra;
				break;
			}
		}

		if (itemCompraProduto != null) {
			itemCompraProduto
					.setQuantidade(itemCompraProduto.getQuantidade() + 1);
		} else {
			ItemCompra itemCompra = new ItemCompra();
			itemCompra.setProduto(produto);
			itemCompra.setQuantidade(1);

			itemCarrinho.add(itemCompra);
		}
	}

	public void adicionarNoCarrinho(Produto produto) {
		incrementarProdutoNoCarrinho(produto);
	}

	public void remover(ItemCompra itemCompra) {
		itemCarrinho.remove(itemCompra);
	}

	public List<ItemCompra> getItemCarrinho() {
		return itemCarrinho;
	}

	public void setItemCarrinho(List<ItemCompra> itemCarrinho) {
		this.itemCarrinho = itemCarrinho;
	}

	public Produto getProdutoSelecionado() {
		return produtoSelecionado;
	}

	public void setProdutoSelecionado(Produto produtoSelecionado) {
		this.produtoSelecionado = produtoSelecionado;
	}

}
