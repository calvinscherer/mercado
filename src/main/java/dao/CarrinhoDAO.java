package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.faces.model.SelectItem;
import model.Carrinho;
import model.Item;
import model.Lista;

public class CarrinhoDAO extends BaseDAOJDBC {

	private static CarrinhoDAO instance = new CarrinhoDAO();

	protected CarrinhoDAO() {
	}

	public static CarrinhoDAO getInstance() {
		return instance;
	}

	protected List<Carrinho> getManyByCriteria(String sql) throws DAOException {

		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		List<Carrinho> alista = new ArrayList<Carrinho>();

		try {
			conn = getConnection();
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			
			while (rs.next()) {
				Carrinho carrinho = new Carrinho(
						new Item(new Integer(rs.getInt("iditem")),rs.getString("nomeItem"),new Double(rs.getDouble("preco")),rs.getString("imagem")),
						new Lista(new Integer(rs.getInt("idlista")),rs.getString("nomeLista"),rs.getTimestamp("cadastro")),
						new Double(rs.getDouble("precoPago")),
						new Boolean(rs.getBoolean("isComprou"))	
				);
				
				alista.add(carrinho);
			}
		} catch (Exception e) {
			throw new DAOException("[ListaDAO - getManyByCriteria]"+ e.getMessage(), e);
		} finally {
			closeResources(conn, st, rs);
		}
		
		return alista;

	}
	
	public void comprarProduto(Carrinho carrinho) throws DAOException {
		
		Connection conn = null;
		PreparedStatement pst = null;
		
		try {
			conn = getConnection();
			pst = conn.prepareStatement("update carrinho set isComprou = true, precoPago = ? WHERE idLista = ? and iditem = ?;");
			pst.setDouble(1, carrinho.getPrecoPago());
			pst.setInt(2, carrinho.getLista().getId());
			pst.setInt(3, carrinho.getItem().getId());
			pst.executeUpdate();
		
		} catch (Exception e) {
			throw new DAOException("[ItemDAO - save] " + e.getMessage(), e);

		} finally {
			closeResources(conn, pst);
		}
	}
	/*
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
	*/
	public List<Carrinho> getAll() throws DAOException {
		String sql = "SELECT idlista, nome, cadastro FROM lista";
		return this.getManyByCriteria(sql);
	}
	
	public List<Carrinho> getCarrinhoByLista(int idLista) throws DAOException {
		String sql = "SELECT i.iditem, i.nome as nomeItem, i.preco, i.imagem, l.idlista, l.nome as nomeLista, l.cadastro, c.precoPago, c.isComprou FROM carrinho c JOIN item i ON i.iditem = c.iditem JOIN lista l ON c.idlista = l.idLista WHERE c.idlista = "+idLista;
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
