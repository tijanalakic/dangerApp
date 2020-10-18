package net.etfbl.main.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Logger;

import net.etfbl.main.dto.LoginReview;
import net.etfbl.main.util.ConnectionPool;
import net.etfbl.main.util.DAOUtil;

public class LoginReviewDAO {

	private static final Logger LOGGER = Logger.getLogger(LoginReviewDAO.class.getName());
	private static final String SQL_GET_LAST_LOGIN = "SELECT * FROM login_review WHERE USER_ID=? ORDER BY LOGIN_TIME desc";
	private static final String SQL_INSERT = "INSERT INTO login_review (login_time, logout_time, status, user_id) VALUES (?,?,?,?)";
	private static final String SQL_LOGOUT_USER = "UPDATE login_review SET status =?, logout_time =? WHERE login_review_id=?";

	private static ConnectionPool connectionPool = ConnectionPool.getConnectionPool();

	public static LoginReview getLastLogin(int userId) {

		LoginReview loginReview = null;
		Connection connection = null;
		ResultSet rs = null;
		Object values[] = { userId };

		try {

			connection = connectionPool.checkOut();
			PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_GET_LAST_LOGIN, false, values);
			rs = pstmt.executeQuery();

			if (rs.next()) {

				loginReview = new LoginReview(rs.getInt("LOGIN_REVIEW_ID"), rs.getInt("STATUS"), rs.getInt("USER_ID"),
						rs.getDate("LOGIN_TIME"), rs.getDate("LOGOUT_TIME"));
			}

			pstmt.close();

		} catch (SQLException exp) {
			LOGGER.severe(exp.getStackTrace().toString());
		} finally {
			connectionPool.checkIn(connection);
		}
		return loginReview;
	}

	public static boolean logoutUser(int loginReviewId, int status, Date logoutDate) {

		int result = 0;
		Connection connection = null;
		Object values[] = { status, logoutDate, loginReviewId };

		try {
			connection = connectionPool.checkOut();
			PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_LOGOUT_USER, false, values);
			result = pstmt.executeUpdate();
			pstmt.close();

		} catch (SQLException exp) {
			LOGGER.severe(exp.getStackTrace().toString());
		} finally {
			connectionPool.checkIn(connection);
		}

		return result > 0;
	}

	public static boolean insert(LoginReview loginReview) {

		boolean result = false;
		Connection connection = null;
		ResultSet generatedKeys = null;
		Object values[] = { loginReview.getLoginTime(), loginReview.getLogoutTime(), loginReview.getStatus(),
				loginReview.getUserId() };

		try {

			connection = connectionPool.checkOut();
			PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_INSERT, true, values);
			pstmt.executeUpdate();
			generatedKeys = pstmt.getGeneratedKeys();

			if (pstmt.getUpdateCount() > 0) {
				result = true;
			}

			if (generatedKeys.next())
				loginReview.setLoginReviewId(generatedKeys.getInt(1));
			pstmt.close();

		} catch (SQLException e) {
			LOGGER.severe(e.getStackTrace().toString());
		} finally {
			connectionPool.checkIn(connection);
		}

		return result;
	}

}
