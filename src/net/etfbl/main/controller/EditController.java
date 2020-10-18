package net.etfbl.main.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.mysql.cj.util.StringUtils;

import net.etfbl.main.bean.UserBean;
import net.etfbl.main.dto.User;
import net.etfbl.main.util.Utils;

public class EditController {

	public EditController() {

		super();
	}

	public String saveProfile(HttpServletRequest request, HttpSession session, String address, String imagesPath) {

		String country = request.getParameter("country");
		String countryCode = request.getParameter("countryCode");
		String flag = request.getParameter("flag");
		String city = request.getParameter("city");
		String region = request.getParameter("region");
		String emailNotification = request.getParameter("emailCheck");
		String appNotification = request.getParameter("appCheck");

		try {

			Part part = request.getPart("profilePicture");
			String file = Paths.get(part.getSubmittedFileName()).getFileName().toString();
			InputStream fileContent = part.getInputStream();

			if (StringUtils.isNullOrEmpty(country) || StringUtils.isNullOrEmpty(city) || StringUtils.isNullOrEmpty(region)) {

				session.setAttribute("notification", "All fields must be filled out!");

			} else {

				UserBean userBean = new UserBean();
				User user = (User) session.getAttribute("newUser");

				if (user != null) {

					user.setCountry(country);
					user.setCountryCode(countryCode);
					user.setCity(city);
					user.setRegion(region);

					if (!StringUtils.isNullOrEmpty(appNotification)) {
						user.setAppNotification(Boolean.valueOf(appNotification));
					}
					if (!StringUtils.isNullOrEmpty(emailNotification)) {
						user.setEmailNotification(Boolean.valueOf(emailNotification));
					}
					if (StringUtils.isNullOrEmpty(file)) {
						user.setProfilePicture(flag);
					} else {
						user.setProfilePicture(Utils.saveFile(user, file, fileContent, imagesPath, MainController.IMAGE_LOCATION_PATH));
					}
					userBean.add(user);
					session.setAttribute("newUser", "");
					session.setAttribute("notification", "");
					session.setAttribute("registrationNotification", "You are successfully registered!");

				}
				address = MainController.LOGIN_PAGE;
			}
		} catch (IOException | ServletException e) {

			session.setAttribute("notification", "There was a problem with uploading a profile picture!");
		}
		return address;
	}
}
