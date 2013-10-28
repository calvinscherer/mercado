package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;

public class Lista {
	Integer id;
	String nome;
	Date cadastro;
	List<Item> itens;
	List<SelectItem> selecItens;
	
	
	
	public Lista(){
		this.selecItens = new ArrayList<SelectItem>();
	}

	public Lista(Integer id, String nome, Date cadastro, List<Item> itens) {
		super();
		this.id = id;
		this.nome = nome;
		this.cadastro = cadastro;
		this.itens = itens;
	}
	
	
	
	public List<SelectItem> getSelecItens() {
		return selecItens;
	}

	public void setSelecItens(List<SelectItem> selecItens) {
		this.selecItens = selecItens;
	}

	public Lista(Integer id, String nome, Date data) {
		this(id,nome,data,null);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getCadastro() {
		return cadastro;
	}

	public void setCadastro(Date cadastro) {
		this.cadastro = cadastro;
	}
	
	public List<Item> getItens() {
		return itens;
	}

	public void setItens(List<Item> itens) {
		this.itens = itens;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Lista other = (Lista) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
