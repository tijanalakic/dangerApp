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

import net.etfbl.main.dto.Post;
import net.etfbl.main.dto.PostAttachment;
import net.etfbl.main.util.ConnectionPool;
import net.etfbl.main.util.DAOUtil;
import net.etfbl.main.util.PostTypeEnum;

public class PostDAO {

	private static final Logger LOGGER = Logger.getLogger(PostDAO.class.getName());
	private static final String SQL_SELECT_ALL = "SELECT * FROM danger_app.post p left join post_attachment a on p.POST_ID = a.POST_POST_ID\r\n"
												+ "inner join user u on p.POSTED_USER_ID = u.USER_ID";
	private static final String SQL_INSERT = "INSERT INTO post (post_type, message, posted_date, posted_user_id) VALUES (?,?,?,?)";
	private static ConnectionPool connectionPool = ConnectionPool.getConnectionPool();

	public static List<Post> getAll() {

		Map<Integer, Post> idPostMap = new HashMap<Integer, Post>();
		Connection connection = null;
		ResultSet rs = null;

		try {

			connection = connectionPool.checkOut();
			PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_SELECT_ALL, false);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				int postId = rs.getInt("POST_ID");
				int postAttachmentId = rs.getInt("POST_ATTACHMENT_ID");
				int attachmentType = rs.getInt("ATTACHMENT_TYPE");
				String attachmentPath = rs.getString("VALUE");
				PostAttachment postAttachment = null;
				if (postAttachmentId > 0) {
					postAttachment = new PostAttachment(attachmentPath, attachmentType);
				}

				if (idPostMap.containsKey(postId) && postAttachment != null) {
					idPostMap.get(postId).getPostAttachments().add(postAttachment);
				} else {
					List<PostAttachment> postAttachments = new ArrayList<>();
					if (postAttachment != null) {
						postAttachments.add(postAttachment);
					}
					String postedByFullName = rs.getString("NAME") + " " + rs.getString("SURNAME");

					Post post = new Post(postId, PostTypeEnum.POST, rs.getString("MESSAGE"), postedByFullName,
							rs.getString("USERNAME"), rs.getString("PROFILE_PICTURE"), rs.getTimestamp("POSTED_DATE"),
							postAttachments);

					idPostMap.put(postId, post);
				}
			}

			pstmt.close();

		} catch (SQLException exp) {
			LOGGER.severe(exp.getStackTrace().toString());
		} finally {
			connectionPool.checkIn(connection);
		}
		return new ArrayList<>(idPostMap.values());
	}

	public static boolean insert(Post post) {

		boolean result = false;
		Connection connection = null;
		ResultSet generatedKeys = null;
		Object values[] = { post.getPostType().getId(), post.getMessage(), post.getPostedDate(), post.getUserId() };

		try {

			connection = connectionPool.checkOut();
			PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_INSERT, true, values);
			pstmt.executeUpdate();
			generatedKeys = pstmt.getGeneratedKeys();

			if (pstmt.getUpdateCount() > 0) {
				result = true;
			}

			if (generatedKeys.next())
				post.setPostId(generatedKeys.getInt(1));
			pstmt.close();

		} catch (SQLException e) {
			LOGGER.severe(e.getStackTrace().toString());
		} finally {
			connectionPool.checkIn(connection);
		}

		if (post.getPostAttachments() != null) {
			for (PostAttachment postAttachment : post.getPostAttachments()) {
				postAttachment.setPostId(post.getPostId());
				PostAttachmentDAO.insert(postAttachment);
			}
		}

		return result;
	}
}
