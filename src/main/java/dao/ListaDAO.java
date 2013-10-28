package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.model.SelectItem;

import model.Item;
import model.Lista;

public class ListaDAO extends BaseDAOJDBC {

	private static ListaDAO instance = new ListaDAO();

	protected ListaDAO() {
	}

	public static ListaDAO getInstance() {
		return instance;
	}

	protected List<Lista> getManyByCriteria(String sql) throws DAOException {

		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		List<Lista> alista = new ArrayList<Lista>();

		try {
			conn = getConnection();
			st = conn.createStatement();
			rs = st.executeQuery(sql);

			while (rs.next()) {
				Lista lista = new Lista(new Integer(rs.getInt("idlista")),
						rs.getString("nome"),
						rs.getTimestamp("cadastro"));
				alista.add(lista);

			}

		} catch (Exception e) {
			throw new DAOException("[ListaDAO - getManyByCriteria]"+ e.getMessage(), e);

		} finally {
			closeResources(conn, st, rs);
		}
		
		if(alista.isEmpty()){
			for (Iterator<Lista> iterator = alista.iterator(); iterator.hasNext();) {
				Lista lista = (Lista) iterator.next();
				List<Item> itens = getItensByLista(lista.getId().intValue());
				lista.setItens(itens);
			}
		}
		return alista;

	}

	public Lista save(Lista lista) throws DAOException {
		
		Connection conn = null;
		PreparedStatement pst = null;
		
		Integer id = null;

		try {
			conn = getConnection();
			if (lista.getId() == null) {
				pst = conn.prepareStatement("INSERT INTO lista (nome, cadastro) VALUES ( ?, ?)", Statement.RETURN_GENERATED_KEYS);
			} else {
				pst = conn.prepareStatement("UPDATE lista SET nome=?, cadastro=? WHERE idlista=?");
				pst.setInt(3, lista.getId().intValue());
			}
			Timestamp time = new Timestamp(System.currentTimeMillis());  
			pst.setString(1, lista.getNome());
			pst.setTimestamp(2, time);
			pst.executeUpdate();
			
			id = new Integer(super.getUltimoCodigo(pst));
		
		} catch (Exception e) {
			throw new DAOException("[ItemDAO - save] " + e.getMessage(), e);

		} finally {
			closeResources(conn, pst);
		}
		
		if (lista.getId() != null) {
			apagarItensCarrinhoByLista(id);
		}
		lista.setId(id);
		cadastrarItensCarrinho(lista);
		
		
		return lista;

	}
	
	private void apagarItensCarrinhoByLista(int idLista) throws DAOException{
		PreparedStatement pst = null;
		Connection conn = null;
		try {
			conn = getConnection();
			pst = conn.prepareStatement("DELETE FROM carrinho WHERE idlista = ?");
			pst.setInt(1, idLista);

			pst.executeUpdate();

		} catch (Exception e) {
			throw new DAOException("[ListaDAO - delete Carrinho By Lista] " + e.getMessage(), e);
		} finally {
			closeResources(conn, pst);
		}
	}
	
	private void cadastrarItensCarrinho(Lista lista) throws DAOException{
		PreparedStatement pst = null;
		Connection conn = null;
		try {
			conn = getConnection();
			for (Iterator<SelectItem> iterator = lista.getSelecItens().iterator(); iterator.hasNext();) {
				SelectItem iditem = (SelectItem) iterator.next();
				pst = conn.prepareStatement("INSERT INTO carrinho (iditem, idlista) VALUES (?, ?)");
				pst.setInt(1, new Integer(iditem.getValue().toString()).intValue());
				pst.setInt(2, lista.getId().intValue());
				pst.executeUpdate();	
			}
		} catch (Exception e) {
			throw new DAOException("[ListaDAO - cadastrar Carrinho] " + e.getMessage(), e);
		} finally {
			closeResources(conn, pst);
		}
	}

	public List<Lista> getAll() throws DAOException {
		String sql = "SELECT idlista, nome, cadastro FROM lista";
		return this.getManyByCriteria(sql);
	}

	public void delete(Integer id) throws DAOException {

		Connection conn = null;
		PreparedStatement pst = null;

		try {
			conn = getConnection();
			
			pst = conn.prepareStatement("DELETE FROM carrinho WHERE idlista = ?");
			
			pst.setInt(1, id.intValue());

			pst.executeUpdate();
			
			pst = conn.prepareStatement("DELETE FROM lista WHERE idlista = ?");

			pst.setInt(1, id.intValue());

			pst.executeUpdate();

		} catch (Exception e) {
			throw new DAOException("[ListaDAO - delete] " + e.getMessage(), e);

		} finally {
			closeResources(conn, pst);
		}
	}

	public Lista getById(Integer id) throws DAOException {
		String sql = "SELECT idlista, nome, cadastro FROM lista WHERE idlista = ?";
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Lista lista = null;
		try {
			conn = getConnection();
			pst = conn.prepareStatement(sql);
			pst.setInt(1, id.intValue());
			rs = pst.executeQuery();
			
			while (rs.next()) {

				lista = new Lista(new Integer(rs.getInt("idlista")),
					rs.getString("nome"),
					rs.getTimestamp("cadastro"));
			}
			
			List<Item> listaItem = getItensByLista(lista.getId().intValue());
			List<SelectItem> listaSelectItens = new ArrayList<SelectItem>();
			
			for (Iterator<Item> iterator = listaItem.iterator(); iterator.hasNext();) {
				Item item = (Item) iterator.next();
				listaSelectItens.add(new SelectItem(item.getId().intValue(), item.getNome()));
			}
			
			lista.setSelecItens(listaSelectItens);
			
			return lista;

		} catch (Exception e) {
			throw new DAOException("[ListaDAO - getById] " + e.getMessage(), e);

		} finally {
			closeResources(conn, pst);
		}
	}
	
	public List<Item> getItensByLista(int idLista) throws DAOException{
		ItemDAO itemDAO = ItemDAO.getInstance();
		return itemDAO.getManyByCriteria("SELECT i.iditem, i.nome, i.preco, i.imagem FROM item i JOIN carrinho c ON i.iditem = c.iditem WHERE c.idlista = "+idLista);
	}
	
}
