package net.etfbl.main.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.mysql.cj.util.StringUtils;

import net.etfbl.main.bean.UserBean;

public class LoginController {
	
	public LoginController() {
		super();
	}
	
	public String login(HttpServletRequest request, HttpSession session, String address) {
		
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		
		if(StringUtils.isNullOrEmpty(username)|| StringUtils.isNullOrEmpty(password)) {
			
			session.setAttribute("notification", "Username or password is empty!");
			
		} else {
			
			UserBean userBean=new UserBean();
			
			if(userBean.login(username, password)) {
				
				session.setAttribute("user", userBean);
				session.setAttribute("notification", "");
				session.setAttribute("notificationRegistration", "");
				address=MainController.HOME_PAGE;
				
			}else {
				
				session.setAttribute("notification", "You have entered invalid parameters");
			}			
		}
		return address;
	}
}
