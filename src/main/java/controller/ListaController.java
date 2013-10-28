package controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import model.Item;
import model.Lista;
import dao.DAOException;
import dao.ItemDAO;
import dao.ListaDAO;

@ManagedBean
@SessionScoped
public class ListaController implements Serializable {
	private static final long serialVersionUID = 1L;
	private ListaDAO listaDAO = ListaDAO.getInstance();
	private ItemDAO itemDAO = ItemDAO.getInstance();
	private Lista lista;
	private String msg;
	private String erro;
	private List<SelectItem> listaCompletaItens;
	private List<SelectItem> listaSele;
	private String[] listaItens;
	private String[] listaItensSele;

	public ListaController() {
		this.lista = new Lista();
		this.listaCompletaItens = getItens();
		this.listaSele = new ArrayList<SelectItem>();
	}

	public void adicionar(ActionEvent evt) {
		List<SelectItem> listTemp = new ArrayList<SelectItem>();
		listTemp.addAll(listaCompletaItens);
		if (listaItens != null) {
			for (Iterator<SelectItem> iterator = listTemp.iterator(); iterator
					.hasNext();) {
				SelectItem item = (SelectItem) iterator.next();
				for (int i = 0; i < this.listaItens.length; i++) {
					Integer valorSele = new Integer(this.listaItens[i]);
					Integer valorItem = (Integer) item.getValue();
					if (valorSele.intValue() == valorItem.intValue()) {
						listaSele.add(item);
						listaCompletaItens.remove(item);
					}
				}
			}
		}
	}

	public void retirar(ActionEvent evt) {
		List<SelectItem> novaLista = new ArrayList<SelectItem>();
		novaLista.addAll(this.listaSele);
		if (listaItensSele != null) {
			for (Iterator<SelectItem> iterator = listaSele.iterator(); iterator
					.hasNext();) {
				SelectItem item = (SelectItem) iterator.next();
				for (int i = 0; i < this.listaItensSele.length; i++) {
					Integer valorSele = new Integer(this.listaItensSele[i]);
					Integer valorItem = (Integer) item.getValue();
					if (valorSele.intValue() == valorItem.intValue()) {
						novaLista.remove(item);
						listaCompletaItens.add(item);
					}
				}
			}
		}
		this.listaSele = novaLista;
	}

	public List<Lista> getListas() {
		List<Lista> listas = null;
		try {
			listas = listaDAO.getAll();
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return listas;
	}
	

	public List<SelectItem> getItens() {
		List<Item> itens = null;
		List<SelectItem> listaItens = new ArrayList<SelectItem>();
		try {
			itens = itemDAO.getAll();
			for (Iterator<Item> iterator = itens.iterator(); iterator.hasNext();) {
				Item item = (Item) iterator.next();
				SelectItem si = new SelectItem(item.getId().intValue(),
						item.getNome());
				listaItens.add(si);
			}
		} catch (DAOException e) {
			e.printStackTrace();
		}

		return listaItens;
	}

	public void salvar(ActionEvent evt) {
		lista.setSelecItens(listaSele);
		
		try {
			listaDAO.save(lista);
			this.msg = "Lista salva com sucesso";
		} catch (DAOException e) {
			this.erro = "Erro ao salvar a lista, "+e.getMessage();
		}
		lista = new Lista();
		listaCompletaItens = getItens();
		listaSele = new ArrayList<SelectItem>();
		listaItens = null;
		listaItensSele = null;
	}

	public void editar(ActionEvent evt) {
		this.msg = "entrei";
		Integer id = Integer.parseInt((String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id"));
		try {
			lista = listaDAO.getById(id);
			this.msg = "Lista recuperada com sucesso";
		} catch (DAOException e) {
			this.erro = "Erro ao recuperar a lista, "+e.getMessage();
		}
		this.msg = "sai";
	}

	public void deletar(ActionEvent evt) {
		try {
			Integer id = Integer.parseInt((String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id"));
			listaDAO.delete(id);
			this.msg = "Lista excluída com sucesso";
		} catch (DAOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			this.erro = "Erro ao excluir Lista";
		}
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

	public String[] getListaItensSele() {
		return listaItensSele;
	}

	public void setListaItensSele(String[] listaItensSele) {
		this.listaItensSele = listaItensSele;
	}

	public String[] getListaItens() {
		return listaItens;
	}

	public void setListaItens(String[] listaItens) {
		this.listaItens = listaItens;
	}

	public List<SelectItem> getListaSele() {
		return listaSele;
	}

	public void setListaSele(List<SelectItem> listaSele) {
		this.listaSele = listaSele;
	}

	public Lista getLista() {
		return lista;
	}

	public void setLista(Lista lista) {
		this.lista = lista;
	}

	public List<SelectItem> getListaCompletaItens() {
		return listaCompletaItens;
	}

	public void setListaCompletaItens(List<SelectItem> listaItens) {
		this.listaCompletaItens = listaItens;
	}

}
