package net.etfbl.main.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

import net.etfbl.main.dto.PostAttachment;
import net.etfbl.main.util.ConnectionPool;
import net.etfbl.main.util.DAOUtil;

public class PostAttachmentDAO {

	private final static Logger LOGGER = Logger.getLogger(PostAttachmentDAO.class.getName());

	private static ConnectionPool connectionPool = ConnectionPool.getConnectionPool();

	private static final String SQL_INSERT = "INSERT INTO post_attachment (value, attachment_type, post_post_id) VALUES (?,?,?)";

	public static boolean insert(PostAttachment postAttachment) {

		boolean result = false;
		Connection connection = null;
		ResultSet generatedKeys = null;
		Object values[] = { postAttachment.getValue(), postAttachment.getAttachmentType(), postAttachment.getPostId() };

		try {

			connection = connectionPool.checkOut();
			PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_INSERT, true, values);
			pstmt.executeUpdate();
			generatedKeys = pstmt.getGeneratedKeys();

			if (pstmt.getUpdateCount() > 0) {
				result = true;
			}

			if (generatedKeys.next()) {
				postAttachment.setPostAttachmentId(generatedKeys.getInt(1));
			}

			pstmt.close();

		} catch (SQLException e) {
			LOGGER.severe(e.getStackTrace().toString());
		} finally {
			connectionPool.checkIn(connection);
		}

		return result;
	}
}
