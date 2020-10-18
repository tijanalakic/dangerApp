package net.etfbl.main.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import net.etfbl.main.dto.Comment;
import net.etfbl.main.util.ConnectionPool;
import net.etfbl.main.util.DAOUtil;

public class CommentDAO {

	private static final Logger LOGGER = Logger.getLogger(CommentDAO.class.getName());
	private static final String SQL_SELECT_ALL = "SELECT * FROM comment c inner join user u  on c.posted_user_id = u.user_id where POST_ID = ?";
	private static final String SQL_INSERT    = "INSERT INTO comment (message, image, post_id, posted_user_id, insert_date) VALUES (?,?,?,?,?)";
	
	private static ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
	
	public static List<Comment> getAllByPostId(int postId){
		
		Connection connection = null;
		ResultSet rs = null;
		
		List<Comment> comments = new ArrayList<>();
		try {
			
			connection = connectionPool.checkOut();
			Object values[] = {postId};
			PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_SELECT_ALL, false, values);
			rs = pstmt.executeQuery();
			
			while (rs.next()){
				 
				int commentId 	= rs.getInt("COMMENT_ID");
				String message 	= rs.getString("MESSAGE");
				String image 	= rs.getString("IMAGE");
				String imageType= rs.getString("IMAGE_TYPE");
				Date insertDate = rs.getTimestamp("INSERT_DATE");

				String postedByFullName   = rs.getString("NAME") + " " + rs.getString("SURNAME");
				String userProfilePicture = rs.getString("PROFILE_PICTURE");
				
				Comment comment = new Comment(commentId, postId, message, imageType, postedByFullName, image, userProfilePicture, insertDate);
				comments.add(comment);
			}
			
			pstmt.close();
			
		} catch (SQLException e) {
			LOGGER.severe(e.getStackTrace().toString());
		} finally {
			connectionPool.checkIn(connection);
		}
		return comments;
	}
	
	public static boolean insert(Comment comment) {
		
		boolean result = false;
		Connection connection = null;
		ResultSet generatedKeys = null;
		Object values[] = { comment.getMessage(), comment.getImage(), comment.getPostId(), comment.getUserId(), comment.getInsertDate()};
		
		try {
			
			connection = connectionPool.checkOut();
			PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_INSERT, true, values);
			pstmt.executeUpdate();
			generatedKeys = pstmt.getGeneratedKeys();
			
			if(pstmt.getUpdateCount()>0) {
				result = true;
			}
			
			if (generatedKeys.next())
				comment.setCommentId(generatedKeys.getInt(1));
			pstmt.close();
			
		} catch (SQLException e) {
			LOGGER.severe(e.getStackTrace().toString());
		} finally {
			connectionPool.checkIn(connection);
		}
		
		return result;
	}
}
