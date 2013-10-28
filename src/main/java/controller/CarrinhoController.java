package controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

import model.Carrinho;
import model.Item;
import model.Lista;
import dao.CarrinhoDAO;
import dao.DAOException;
import dao.ItemDAO;
import dao.ListaDAO;

@ManagedBean
@SessionScoped
public class CarrinhoController  implements Serializable {
	private static final long serialVersionUID = 1L;
	private String msg;
	private ListaDAO listaDAO = ListaDAO.getInstance();
	private ItemDAO itemDAO = ItemDAO.getInstance();
	private CarrinhoDAO carrinhoDAO = CarrinhoDAO.getInstance();
	private String listaSele;
	private List<Carrinho> listaCarrinho;
	
	
	public CarrinhoController() {
		msg = new String();
		
	}
	
	public void realizarCompra(ActionEvent evt){
		String idI = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idItem");
		String idL = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idLista");
		String preco = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("preco");
		
		Carrinho carrinhoBean = new Carrinho();
		
		carrinhoBean.setPrecoPago(new Double(preco));
		
		Lista listaBean = null;
		Item itemBean = null;
		try {
			listaBean = listaDAO.getById(new Integer(idL));
			itemBean = itemDAO.getById(new Integer(idI));
		} catch (NumberFormatException e) {
			this.msg = "Identificadores inválidos, "+ e.getMessage();
		} catch (DAOException e) {
			this.msg = "Erro ao recuperar elementos da compra, "+ e.getMessage();
		}
		
		carrinhoBean.setItem(itemBean);
		carrinhoBean.setLista(listaBean);
		try {
			carrinhoDAO.comprarProduto(carrinhoBean);
		} catch (DAOException e) {
			this.msg = "Erro ao comprar item, "+ e.getMessage();
		}
		try {
			this.listaCarrinho = carrinhoDAO.getCarrinhoByLista(listaBean.getId());
		} catch (DAOException e) {
		}
		this.listaCarrinho.notifyAll();
		
	}
	
	public List<SelectItem> getListas() {
		List<Lista> listas = null;
		List<SelectItem> listasSI = new ArrayList<SelectItem>();
		try {
			listas = listaDAO.getAll();
			for (Iterator<Lista> iterator = listas.iterator(); iterator.hasNext();) {
				Lista lista = (Lista) iterator.next();
				SelectItem si = new SelectItem(lista.getId().intValue(), lista.getNome());
				listasSI.add(si);
			}
		} catch (DAOException e) {
			e.printStackTrace();
		}
		
		return listasSI;
	}
	
	public void montaLista(){
		if(this.listaSele != null && !this.listaSele.equals("")){
			Integer idSele = new Integer(this.listaSele);
			try {
				this.listaCarrinho = carrinhoDAO.getCarrinhoByLista(idSele.intValue());
			} catch (DAOException e) {
			}
			this.listaCarrinho.notifyAll();
		}
	}
	public List<Carrinho> getListaCarrinho() {
		return listaCarrinho;
	}

	public void setListaCarrinho(List<Carrinho> listaCarrinho) {
		this.listaCarrinho = listaCarrinho;
	}
	public String getListaSele() {
		return listaSele;
	}

	public void setListaSele(String listaSele) {
		this.listaSele = listaSele;
	}
	
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
	
	
/*
	public void savar() {
		try {
			listaDAO.save(lista);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		
		lista = new Lista();
	}
	
	public void editar() {
		Integer id = Integer.parseInt((String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id"));
		try {
			lista = listaDAO.getById(id);
			this.listaItensSele = lista.getSelecItens();
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}
	
	public void deletar(){
		Integer id = Integer.parseInt((String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id"));
		try {
			listaDAO.delete(id);
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}
	*/
}
