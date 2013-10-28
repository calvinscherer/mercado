package controller;

import java.io.Serializable;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import model.Item;
import dao.DAOException;
import dao.ItemDAO;
@ManagedBean
@SessionScoped
public class ItemController implements Serializable{
	private static final long serialVersionUID = 1L;
	private ItemDAO dao = ItemDAO.getInstance();
	private Item item;
	private String msg;
	private String erro;
	
	public ItemController() {
		this.item = new Item();
		this.msg = new String();
		this.erro = new String();
	}
	
	public List<Item> getItens() {
		List<Item> itens = null;
		try {
			itens = dao.getAll();
		} catch (DAOException e) {
			erro = "Erro ao buscar itens, "+e.getMessage();
		} 
		return itens;
	}

	public void savar(ActionEvent evt) {
		try {
			dao.save(item);
			msg = "Item salvo com sucesso";
		} catch (DAOException e) {
			erro = "Erro ao salvar, "+e.getMessage();
		}
		
		item = new Item();
	}
	
	public void editar(ActionEvent evt) {
		Integer id = Integer.parseInt((String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id"));
		try {
			item = dao.getById(id);
			msg = "Item recuperado com sucesso para edição";
		} catch (DAOException e) {
			erro = "Erro ao recuperar item, "+e.getMessage();
		}
	}
	
	public void deletar(ActionEvent evt){
		Integer id = Integer.parseInt((String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id"));
		try {
			dao.delete(id);
			msg = "Item excluído com sucesso";
		} catch (DAOException e) {
			erro = "Erro ao deletar, "+e.getMessage();
		}
	}
	
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	
	public String getErro() {
		return erro;
	}

	public void setErro(String erro) {
		this.erro = erro;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
