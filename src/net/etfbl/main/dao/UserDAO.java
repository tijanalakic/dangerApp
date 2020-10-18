package net.etfbl.main.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.etfbl.main.dto.User;
import net.etfbl.main.util.ConnectionPool;
import net.etfbl.main.util.DAOUtil;
import net.etfbl.main.util.UserStatusEnum;

public class UserDAO {

	
	private static ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
	private static final String SQL_SELECT_BY_USERNAME_AND_PASSWORD="SELECT * FROM user WHERE username=? AND password=? AND status=?";
	private static final String SQL_IS_USERNAME_USED 				= "SELECT * FROM user WHERE username = ?";
	private static final String SQL_IS_EMAIL_USED					= "SELECT * FROM user WHERE email = ?";
	private static final String SQL_INSERT 							= "INSERT INTO user (username, password, name, surname, email, "
			+ "country, country_code, app_notification, email_notification, city, region, login_counter, admin, status, date_created, profile_picture) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	private static final String SQL_UPDATE_LOGIN_COUNT 				= "UPDATE danger_app.user set LOGIN_COUNTER=? WHERE USER_ID=?";
	
	
	
	public static User selectByUsernameAndPassword(String username, String password){
		
		User user = null;
		Connection connection = null;
		ResultSet rs = null;
		Object values[] = {username, password, UserStatusEnum.ACTIVE.getId()};
		
		try {
			
			connection = connectionPool.checkOut();
			PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_SELECT_BY_USERNAME_AND_PASSWORD, false, values);
			rs = pstmt.executeQuery();
			
			if (rs.next()){
				 
				user = new User(rs.getInt("USER_ID"),
								rs.getString("NAME"),
								rs.getString("SURNAME"), 
								rs.getString("USERNAME"),
								rs.getString("PASSWORD"),
								rs.getString("EMAIL"),
								rs.getString("PROFILE_PICTURE"),
								rs.getString("COUNTRY"),
								rs.getBoolean("APP_NOTIFICATION"),
								rs.getBoolean("EMAIL_NOTIFICATION"),
								rs.getString("CITY"),
								rs.getString("REGION"),
								rs.getInt("LOGIN_COUNTER"),
								rs.getBoolean("ADMIN"),
								rs.getInt("STATUS"), 
								rs.getDate("DATE_CREATED"),
								rs.getString("COUNTRY_CODE"));
			}
			
			pstmt.close();
			
		} catch (SQLException exp) {
			exp.printStackTrace();
		} finally {
			connectionPool.checkIn(connection);
		}
		return user;
		
	}
	
public static boolean isUsernameUsed(String username) {
		
		boolean result = true;
		Connection connection = null;
		ResultSet rs = null;
		Object values[] = {username};
		
		try {
			connection = connectionPool.checkOut();
			PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_IS_USERNAME_USED, false, values);
			rs = pstmt.executeQuery();
			
			if (rs.next()){
				result = false;
			}
			
			pstmt.close();
			
		} catch (SQLException exp) {
			exp.printStackTrace();
		} finally {
			connectionPool.checkIn(connection);
		}
		
		return result;
	}
	
	public static boolean isEmailUsed(String email) {
		
		boolean result = true;
		Connection connection = null;
		ResultSet rs = null;
		Object values[] = {email};
		
		try {
			connection = connectionPool.checkOut();
			PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_IS_EMAIL_USED, false, values);
			rs = pstmt.executeQuery();
			
			if (rs.next()){
				result = false;
			}
			
			pstmt.close();
			
		} catch (SQLException exp) {
			exp.printStackTrace();
		} finally {
			connectionPool.checkIn(connection);
		}
		
		return result;
	}
	
	public static boolean insert(User user) {
		
		boolean result = false;
		Connection connection = null;
		ResultSet generatedKeys = null;
		Object values[] = { user.getUsername(), user.getPassword(), user.getName(), user.getSurname(),
							user.getEmail(), user.getCountry(), user.getCountryCode(), user.isAppNotification(),
							user.isEmailNotification(), user.getCity(), user.getRegion(), user.getLoginCounter(), user.isAdmin()
							, user.getUserStatus(), user.getDateCreated(), user.getProfilePicture(),};
		
		try {
			
			connection = connectionPool.checkOut();
			PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_INSERT, true, values);
			pstmt.executeUpdate();
			generatedKeys = pstmt.getGeneratedKeys();
			
			if(pstmt.getUpdateCount()>0) {
				result = true;
			}
			
			if (generatedKeys.next())
				user.setUserId(generatedKeys.getInt(1));
			pstmt.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectionPool.checkIn(connection);
		}
		
		return result;
	}
	
	public static int incrementLoginCounter(int userId, int loginCounter) {
		
		Connection connection = null;
		Object values[] = {loginCounter, userId};
		int result = 0;
		
		try {
			connection = connectionPool.checkOut();
			PreparedStatement pstmt = DAOUtil.prepareStatement(connection, SQL_UPDATE_LOGIN_COUNT, false, values);
			result = pstmt.executeUpdate();
			pstmt.close();
			
		} catch (SQLException exp) {
			exp.printStackTrace();
		} finally {
			connectionPool.checkIn(connection);
		}
		
		return result;
	}
}
