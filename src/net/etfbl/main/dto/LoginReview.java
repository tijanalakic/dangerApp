package net.etfbl.main.dto;

import java.util.Date;

public class LoginReview {

	private int loginReviewId;
	private int status;
	private int userId;
	private Date loginTime;
	private Date logoutTime;

	public LoginReview() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LoginReview(int status, int userId, Date loginTime, Date logoutTime) {
		super();
		this.status = status;
		this.userId = userId;
		this.loginTime = loginTime;
		this.logoutTime = logoutTime;
	}

	public LoginReview(int loginReviewId, int status, int userId, Date loginTime, Date logoutTime) {
		super();
		this.loginReviewId = loginReviewId;
		this.status = status;
		this.userId = userId;
		this.loginTime = loginTime;
		this.logoutTime = logoutTime;
	}

	public int getLoginReviewId() {
		return loginReviewId;
	}

	public void setLoginReviewId(int loginReviewId) {
		this.loginReviewId = loginReviewId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public Date getLogoutTime() {
		return logoutTime;
	}

	public void setLogoutTime(Date logoutTime) {
		this.logoutTime = logoutTime;
	}

}
