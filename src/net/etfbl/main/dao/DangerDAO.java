package net.etfbl.main.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import net.etfbl.main.dto.Danger;
import net.etfbl.main.util.ConnectionPool;
import net.etfbl.main.util.DAOUtil;

public class DangerDAO {

	private final static Logger LOGGER = Logger.getLogger(DangerDAO.class.getName());

	private static ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
	private static final String SQL_SELECT_ALL = "SELECT danger.danger_id, danger.message, danger.emergency, danger.lat, danger.lon,  danger.insert_date,  category.name as category_name, user.NAME, user.SURNAME\r\n"
												+ "FROM danger danger\r\n" + "	INNER JOIN user user on danger.USER_USER_ID = user.USER_ID\r\n"
												+ "    INNER JOIN danger_has_categories link ON danger.danger_id = link.danger_ID\r\n"
												+ "    INNER JOIN danger_categories category ON link.danger_CATEGORIES_ID  = category.danger_CATEGORY_ID \r\n"
												+ "ORDER BY danger.danger_id desc";

	private static final String SQL_INSERT = "INSERT INTO danger (message, emergency, lat, lon, user_user_id, insert_date) VALUES (?,?,?,?,?,?)";
	private static final String SQL_INSERT_CATEGORIES = "INSERT INTO danger_has_categories (danger_id, danger_categories_id) VALUES (?,?)";

	public static List<Danger> getAll() {

		Danger danger = null;
		Map<Integer, Danger> idDangerMap = new HashMap<Integer, Danger>();
		Connection connection = null;
		ResultSet rs = null;

		try {

			connection = connectionPool.checkOut();
			PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_SELECT_ALL, false);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				int dangerId = rs.getInt("DANGER_ID");

				if (idDangerMap.containsKey(dangerId)) {
					idDangerMap.get(dangerId).getCategories().add(rs.getString("CATEGORY_NAME"));
				} else {
					List<String> dangerCategories = new ArrayList<>();
					dangerCategories.add(rs.getString("CATEGORY_NAME"));

					String postedByFullName = rs.getString("NAME") + " " + rs.getString("SURNAME");

					danger = new Danger(rs.getInt("DANGER_ID"), rs.getString("MESSAGE"), rs.getBoolean("EMERGENCY"),
							rs.getDouble("LAT"), rs.getDouble("LON"), rs.getTimestamp("INSERT_DATE"), postedByFullName,
							dangerCategories);
					idDangerMap.put(dangerId, danger);
				}

			}

			pstmt.close();

		} catch (SQLException exp) {
			LOGGER.severe(exp.getStackTrace().toString());
		} finally {
			connectionPool.checkIn(connection);
		}
		
		return new ArrayList<>(idDangerMap.values());
	}

	public static boolean insert(Danger danger) {

		boolean result = false;
		Connection connection = null;
		ResultSet generatedKeys = null;
		Object values[] = { danger.getMessage(), danger.isEmergency(), danger.getLat(), danger.getLon(),
				danger.getUserId(), danger.getInsertDate() };

		try {

			connection = connectionPool.checkOut();
			PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_INSERT, true, values);
			pstmt.executeUpdate();
			generatedKeys = pstmt.getGeneratedKeys();

			if (pstmt.getUpdateCount() > 0) {
				result = true;
			}

			if (generatedKeys.next())
				danger.setDangerId(generatedKeys.getInt(1));
			pstmt.close();

			for (String categoryId : danger.getCategories()) {

				pstmt = DAOUtil.prepareStatement(connection, SQL_INSERT_CATEGORIES, true, danger.getDangerId(),
						categoryId);
				pstmt.executeUpdate();
				generatedKeys = pstmt.getGeneratedKeys();

				if (pstmt.getUpdateCount() > 0) {
					result = true;
				}

				pstmt.close();

			}
		} catch (SQLException e) {
			LOGGER.severe(e.getStackTrace().toString());
		} finally {
			connectionPool.checkIn(connection);
		}

		return result;
	}

}
