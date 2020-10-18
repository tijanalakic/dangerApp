package net.etfbl.main.bean;

import java.io.Serializable;
import java.util.Date;

import net.etfbl.main.dto.LoginReview;
import net.etfbl.main.dto.User;
import net.etfbl.main.util.LoginReviewStatusEnum;
import net.etfbl.main.util.UserStatusEnum;
import net.etfbl.main.dao.LoginReviewDAO;
import net.etfbl.main.dao.UserDAO;
public class UserBean implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	private User user=new User();
	private boolean loggedIn=false;
	
	public boolean login(String username, String password) {
		
		if((user = UserDAO.selectByUsernameAndPassword(username,password)) != null) {
		
			user.setLoginCounter(user.getLoginCounter()+1);
			UserDAO.incrementLoginCounter(user.getUserId(), user.getLoginCounter());
			LoginReview lr=new LoginReview(LoginReviewStatusEnum.ACTIVE.getId(), user.getUserId(), new Date(), null);
			LoginReviewDAO.insert(lr);
			setLoggedIn(true);
			
			return true;
		}
		return false;
		
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}
	
	public void logout() {
		LoginReview loginReview=LoginReviewDAO.getLastLogin(user.getUserId());
		LoginReviewDAO.logoutUser(loginReview.getLoginReviewId(), LoginReviewStatusEnum.INACTIVE.getId(), new Date());
		user=new User();
		setLoggedIn(false);
	}

	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user=user;
	}

	public boolean isUsernameAllowed(String username) {		
		return UserDAO.isUsernameUsed(username);
	}
	
	public boolean isEmailAllowed(String email) {
		return UserDAO.isEmailUsed(email);
	}
	
	public boolean add(User user) {
		user.setDateCreated(new Date());
		user.setUserStatus(UserStatusEnum.PENDING.getId());
		return UserDAO.insert(user);
	}
}
