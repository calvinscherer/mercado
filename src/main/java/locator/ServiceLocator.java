package locator;

import java.sql.*;

public class ServiceLocator {

	private String databaseURL = "jdbc:mysql://localhost:3306/mercado";
	private String databaseUser = "root";
	private String databasePassword = "";

	private static ServiceLocator instance = new ServiceLocator();

	/**
	 * @return Returns the databasePassword.
	 */
	public String getDatabasePassword() {
		return databasePassword;
	}

	/**
	 * @param databasePassword
	 *          The databasePassword to set.
	 */
	public void setDatabasePassword(String databasePassword) {
		this.databasePassword = databasePassword;
	}

	/**
	 * @return Returns the databaseURL.
	 */
	public String getDatabaseURL() {
		return databaseURL;
	}

	/**
	 * @param databaseURL
	 *          The databaseURL to set.
	 */
	public void setDatabaseURL(String databaseURL) {
		this.databaseURL = databaseURL;
	}

	/**
	 * @return Returns the databaseUser.
	 */
	public String getDatabaseUser() {
		return databaseUser;
	}

	/**
	 * @param databaseUser
	 *          The databaseUser to set.
	 */
	public void setDatabaseUser(String databaseUser) {
		this.databaseUser = databaseUser;
	}

	private ServiceLocator() {
	}

	public static ServiceLocator getInstance() {
		return instance;
	}

	public Connection getConnection() throws Exception {
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(databaseURL, databaseUser,
					databasePassword);
		} catch (Exception e) {
			throw new Exception(
					"Erro ao obter conexao via DriverManager: "
							+ e.getMessage());
		}
		return conn;
	}

}
