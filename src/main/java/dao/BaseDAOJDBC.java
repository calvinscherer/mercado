package dao;

import java.sql.*;
import java.util.List;

import util.Logger;
import locator.ServiceLocator;

public abstract class BaseDAOJDBC {
    
    protected BaseDAOJDBC() {
    }
    
    public Connection getConnection() throws Exception {
        return ServiceLocator.getInstance().getConnection();
    }
    
    public void closeResources(Connection conn, Statement st) {
        closeResources(conn, st, null);
    }
    
    public void closeResources(Connection conn, Statement st, ResultSet rs) {
        try {
            if (rs != null)
                rs.close();
        } catch (SQLException e) {
            Logger.log("Erro ao fechar ResultSet. " + e.getMessage());
        }
        try {
            if (st != null)
                st.close();
        } catch (SQLException e) {
            Logger.log("Erro ao fechar Statement. " + e.getMessage());
        }
        try {
            if (conn != null)
                conn.close();
        } catch (SQLException e) {
            Logger.log("Erro ao fechar Connection. " + e.getMessage());
        }
    }
    
    public int getUltimoCodigo(PreparedStatement pst) throws DAOException {
        ResultSet rs = null;
        try {
            rs = pst.getGeneratedKeys();
            int codigo = 0;
            while (rs.next()) {
                codigo = rs.getInt(1);
            }
            return codigo;
        } catch (Exception e) {
            throw new DAOException("[BaseDAOJDBC - getUltimoCodigo] : Handled Exception  ", e);
        }
    }
    
    protected abstract List getManyByCriteria(String sql) throws DAOException;
    
    
}
