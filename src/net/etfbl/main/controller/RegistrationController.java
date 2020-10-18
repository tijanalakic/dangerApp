package net.etfbl.main.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.mysql.cj.util.StringUtils;

import net.etfbl.main.bean.UserBean;
import net.etfbl.main.dto.User;

public class RegistrationController {

	private static final String NEW_USER 					= "newUser";
	private static final String NOTIFICATION_REGISTRATION 	= "notificationRegistration";
	private static final String NOTIFICATION				= "notification";

	public RegistrationController() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String register(HttpServletRequest request, HttpSession session, String address) {
		
		String name	 	= request.getParameter("name");
		String surname 	= request.getParameter("surname");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String password2= request.getParameter("password2");
		String email 	= request.getParameter("email");

		if (StringUtils.isNullOrEmpty(name) || StringUtils.isNullOrEmpty(surname) || StringUtils.isNullOrEmpty(username) || StringUtils.isNullOrEmpty(password) || StringUtils.isNullOrEmpty(password2) || StringUtils.isNullOrEmpty(email)) {

			session.setAttribute(NOTIFICATION, "All fields must be filled out.");

		} else if (!password.equals(password2)) {

			session.setAttribute(NOTIFICATION, "Passwords do not match.");

		} else {

			UserBean userBean = new UserBean();

			if (!userBean.isUsernameAllowed(username)) {

				session.setAttribute(NOTIFICATION, "Username already exists!");

			} else if (!userBean.isEmailAllowed(email)) {

				session.setAttribute(NOTIFICATION, "Email is already used!");

			} else {

				User user = new User();
				user.setName(name);
				user.setSurname(surname);
				user.setUsername(username);
				user.setEmail(email);
				user.setPassword(password);

				session.setAttribute(NEW_USER, user);
				address = MainController.EDIT_PAGE;
				session.setAttribute(NOTIFICATION, "");
				session.setAttribute(NOTIFICATION_REGISTRATION, "");

			}
		}
		return address;
	}

}
