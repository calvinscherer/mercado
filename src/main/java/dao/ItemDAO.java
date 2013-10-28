package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Item;

public class ItemDAO extends BaseDAOJDBC {

	private static ItemDAO instance = new ItemDAO();

	protected ItemDAO() {
	}

	public static ItemDAO getInstance() {
		return instance;
	}

	protected List<Item> getManyByCriteria(String sql) throws DAOException {

		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		List<Item> alista = new ArrayList<Item>();

		try {
			conn = getConnection();
			st = conn.createStatement();
			rs = st.executeQuery(sql);

			while (rs.next()) {
				Item item = new Item(new Integer(rs.getInt("iditem")),
						rs.getString("nome"),
						new Double(rs.getDouble("preco")),
						rs.getString("imagem"));
				alista.add(item);

			}

			return alista;

		} catch (Exception e) {
			throw new DAOException("[ItemDAO - getManyByCriteria] "
					+ e.getMessage(), e);

		} finally {
			closeResources(conn, st, rs);
		}

	}

	public Item save(Item item) throws DAOException {

		Connection conn = null;
		PreparedStatement pst = null;

		try {
			conn = getConnection();
			if (item.getId() == null) {
				pst = conn.prepareStatement("INSERT INTO item (nome, preco, imagem) VALUES ( ?, ?, ?)");
			} else {
				pst = conn.prepareStatement("UPDATE item SET nome=?, preco=?, imagem=? WHERE iditem= ?");
				pst.setInt(4, item.getId().intValue());
			}

			pst.setString(1, item.getNome());
			pst.setDouble(2, item.getPreco().doubleValue());
			pst.setString(3, item.getImagem());
			pst.execute();

			return item;

		} catch (Exception e) {
			throw new DAOException("[ItemDAO - save] " + e.getMessage(), e);

		} finally {
			closeResources(conn, pst);
		}

	}

	public List<Item> getAll() throws DAOException {
		String sql = "SELECT iditem, nome, preco, imagem FROM item";
		return this.getManyByCriteria(sql);
	}

	public void delete(Integer id) throws DAOException {

		Connection conn = null;
		PreparedStatement pst = null;

		try {
			conn = getConnection();
			pst = conn.prepareStatement("DELETE FROM item WHERE iditem = ?");

			pst.setInt(1, id.intValue());

			pst.executeUpdate();

		} catch (Exception e) {
			throw new DAOException("[ItemJDBC - delete] " + e.getMessage(), e);

		} finally {
			closeResources(conn, pst);
		}
	}

	public Item getById(Integer id) throws DAOException {
		String sql = "SELECT iditem, nome, preco, imagem FROM item WHERE iditem = ?";
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Item item = null;
		try {
			conn = getConnection();
			pst = conn.prepareStatement(sql);
			pst.setInt(1, id.intValue());
			rs = pst.executeQuery();
			
			while (rs.next()) {

				item = new Item(new Integer(rs.getInt("iditem")),
					rs.getString("nome"),
					new Double(rs.getDouble("preco")),
					rs.getString("imagem"));
			}
			return item;

		} catch (Exception e) {
			throw new DAOException("[ItemJDBC - delete] " + e.getMessage(), e);

		} finally {
			closeResources(conn, pst);
		}
	
	}
	/*
	 * 
	 * 
	 * public List getByNome(String nome) throws DAOException{ String sql =
	 * "select * from clientes where nome like '" + nome + "%'"; return
	 * this.getManyByCriteria(sql); }
	 * 
	 * public Cliente getByEmail(String email) throws DAOException{ String sql =
	 * "select * from clientes where email = '" + email +"'"; List lista =
	 * this.getManyByCriteria(sql); if (lista != null && lista.size() > 0)
	 * return (Cliente) lista.get(0); else return null; }
	 */
}
