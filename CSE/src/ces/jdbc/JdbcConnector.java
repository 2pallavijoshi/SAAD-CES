package ces.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;

public class JdbcConnector {

	public static void main(String args[]) throws Exception {
		JdbcConnector conn = new JdbcConnector();
		Connection connection = null;
		try {
			connection = conn.getConnection();
		} finally {
			conn.closeConnection(connection);
		}
	}

	public Connection getConnection() throws SQLException, ClassNotFoundException {
		Connection conn = null;
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/course_enrollment_system", "root", "xAuTuYxU");
		//System.out.println("Connected to database, returning connection...");
		return conn;
	}

	public void closeConnection(Connection conn) throws SQLException {
		if (conn == null) {
			System.out.println("Connection is null, unable to close the connection. ");
		}
		conn.close();
	}

	public boolean execute(String sql) throws ClassNotFoundException, SQLException {
		boolean result = false;
		Connection conn = null;
		try {
			conn = getConnection();
			System.out.println("Exeucting statement - " + sql);
			java.sql.Statement statement = conn.createStatement();
			result = statement.execute(sql);
		} finally {
			closeConnection(conn);
		}
		return result;
	}

	public boolean cleanup() {
		try {
			return execute("delete from assignments where id > 0");
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Error in cleanup....");
			e.printStackTrace();
		}
		return false;
	}
	
	/* Close connection after use...*/
	public ResultSet executeSelect(String sql) throws ClassNotFoundException, SQLException {
		ResultSet resultset = null;
		Connection conn = null;
		try {
			conn = getConnection();
			System.out.println("Exeucting statement - " + sql);
			java.sql.Statement statement = conn.createStatement();
			resultset = statement.executeQuery(sql);
		} finally {
			// closeConnection(conn);
		}
		return resultset;
	}
}
