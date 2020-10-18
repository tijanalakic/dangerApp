package net.etfbl.main.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import net.etfbl.main.dto.DangerCategory;
import net.etfbl.main.util.ConnectionPool;
import net.etfbl.main.util.DAOUtil;

public class DangerCategoryDAO {

	private final static Logger LOGGER = Logger.getLogger(DangerCategoryDAO.class.getName());
	private final static String SQL_SELECT_ALL = "SELECT danger_category_id, name FROM danger_categories";

	private static ConnectionPool connectionPool = ConnectionPool.getConnectionPool();

	public static List<DangerCategory> getAll() {

		Connection connection = null;
		ResultSet rs = null;
		List<DangerCategory> dangerCategories = new ArrayList<>();

		try {

			connection = connectionPool.checkOut();
			PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_SELECT_ALL, false);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				DangerCategory category = new DangerCategory(rs.getInt("DANGER_CATEGORY_ID"), rs.getString("NAME"));
				dangerCategories.add(category);
			}

			pstmt.close();

		} catch (SQLException exp) {
			LOGGER.severe(exp.getStackTrace().toString());
		} finally {
			connectionPool.checkIn(connection);
		}
		return dangerCategories;
	}

}
