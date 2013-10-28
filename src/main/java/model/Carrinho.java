package model;

public class Carrinho {
	Item item;
	Lista lista;
	Double precoPago;
	Boolean pagou;
	
	public Carrinho(){
		
	}
	
	public Carrinho(Item item, Lista lista, Double precoPago, Boolean pagou) {
		super();
		this.item = item;
		this.lista = lista;
		this.precoPago = precoPago;
		this.pagou = pagou;
	}
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	public Lista getLista() {
		return lista;
	}
	public void setLista(Lista lista) {
		this.lista = lista;
	}
	public Double getPrecoPago() {
		return precoPago;
	}
	public void setPrecoPago(Double precoPago) {
		this.precoPago = precoPago;
	}
	public Boolean getPagou() {
		return pagou;
	}
	public void setPagou(Boolean pagou) {
		this.pagou = pagou;
	}
	
	
}
