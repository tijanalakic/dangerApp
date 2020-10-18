package net.etfbl.main.util;

import java.sql.*;
import java.util.*;

public class ConnectionPool {

	private String jdbcURL;
	private String username;
	private String password;
	private int preconnectCount;
	private int connectCount;
	private int maxIdleConnections;
	private int maxConnections;
	private Vector<Connection> usedConnections;
	private Vector<Connection> freeConnections;

	public static ConnectionPool getConnectionPool() {
		return connectionPool;
	}

	private static ConnectionPool connectionPool;

	static {
		ResourceBundle bundle = PropertyResourceBundle.getBundle("net.etfbl.main.util.ConnectionPool");
		String jdbcURL = bundle.getString("jdbcURL");
		String username = bundle.getString("username");
		String password = bundle.getString("password");
		String driver = bundle.getString("driver");
		int preconnectCount = 0;
		int maxIdleConnections = 10;
		int maxConnections = 10;
		try {
			Class.forName(driver);
			preconnectCount = Integer.parseInt(bundle.getString("preconnectCount"));
			maxIdleConnections = Integer.parseInt(bundle.getString("maxIdleConnections"));
			maxConnections = Integer.parseInt(bundle.getString("maxConnections"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		try {
			connectionPool = new ConnectionPool(jdbcURL, username, password, preconnectCount, maxIdleConnections,
					maxConnections);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	protected ConnectionPool(String aJdbcURL, String aUsername, String aPassword, int aPreconnectCount,
			int aMaxIdleConnections, int aMaxConnections) throws ClassNotFoundException, SQLException {

		freeConnections = new Vector<Connection>();
		usedConnections = new Vector<Connection>();
		jdbcURL = aJdbcURL;
		username = aUsername;
		password = aPassword;
		preconnectCount = aPreconnectCount;
		maxIdleConnections = aMaxIdleConnections;
		maxConnections = aMaxConnections;

		for (int i = 0; i < preconnectCount; i++) {
			Connection conn = DriverManager.getConnection(jdbcURL, username, password);
			conn.setAutoCommit(true);
			freeConnections.addElement(conn);
		}
		connectCount = preconnectCount;
	}

	public synchronized Connection checkOut() throws SQLException {

		Connection conn = null;
		if (freeConnections.size() > 0) {
			conn = (Connection) freeConnections.elementAt(0);
			freeConnections.removeElementAt(0);
			usedConnections.addElement(conn);
		} else {
			if (connectCount < maxConnections) {
				conn = DriverManager.getConnection(jdbcURL, username, password);
				usedConnections.addElement(conn);
				connectCount++;
			} else {
				try {
					wait();
					conn = (Connection) freeConnections.elementAt(0);
					freeConnections.removeElementAt(0);
					usedConnections.addElement(conn);
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
			}
		}
		return conn;
	}

	public synchronized void checkIn(Connection aConn) {
		if (aConn == null)
			return;
		if (usedConnections.removeElement(aConn)) {
			freeConnections.addElement(aConn);
			while (freeConnections.size() > maxIdleConnections) {
				int lastOne = freeConnections.size() - 1;
				Connection conn = (Connection) freeConnections.elementAt(lastOne);
				try {
					conn.close();
				} catch (SQLException ex) {
				}
				freeConnections.removeElementAt(lastOne);
			}
			notify();
		}
	}
}
