package net.etfbl.main.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mysql.cj.util.StringUtils;

import net.etfbl.main.bean.CommentBean;
import net.etfbl.main.bean.PostBean;
import net.etfbl.main.bean.UserBean;
import net.etfbl.main.dao.DangerDAO;
import net.etfbl.main.dto.Comment;
import net.etfbl.main.dto.Danger;
import net.etfbl.main.dto.Post;
import net.etfbl.main.dto.User;
import net.etfbl.main.util.Utils;

public class HomeController {

	private final static Logger LOGGER = Logger.getLogger(HomeController.class.getName());

	public JSONArray getAllPosts(HttpServletRequest request, HttpSession session) {

		PostBean postBean = new PostBean();
		CommentBean commentBean = new CommentBean();
		JSONArray postList = new JSONArray();

		for (Post post : postBean.getAll()) {

			JSONObject JSONpost = post.toJSON();

			try {

				JSONpost.append("comments", commentBean.getAllByIdJSON(post.getPostId()));

			} catch (JSONException e) {

				LOGGER.severe(e.getStackTrace().toString());

			}
			postList.put(JSONpost);
		}
		return postList;
	}

	public Comment addComment(HttpServletRequest request, HttpSession session, String path) {

		UserBean userBean = (UserBean) session.getAttribute("user");
		String message = request.getParameter("comment");
		String postID = request.getParameter("postId");
		String imagePath = null;

		try {
			
			for (Part part : request.getParts()) {

				if (part.getSubmittedFileName() == null) {
					continue;
				}
				String fileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();

				if (StringUtils.isNullOrEmpty(fileName)) {
					continue;
				}
				InputStream fileContent = part.getInputStream();
				fileName = "comment-" + userBean.getUser().getUsername() + "-" + fileName;
				imagePath = Utils.saveFile(userBean.getUser(), fileName, fileContent, path, MainController.ATTACHMENT_LOCATION_PATH);
			}
			
			CommentBean commentBean = new CommentBean();
			Comment comment = commentBean.addComment(Integer.valueOf(postID), message, null, userBean.getUser().getUserId(), imagePath);
			comment.setUserProfilePicture(userBean.getUser().getProfilePicture());
			comment.setPostedUserFullName(userBean.getUser().getFullName());
			
			return comment;
			
		} catch (IOException | ServletException e) {

			LOGGER.severe(e.getStackTrace().toString());
		}
		return null;
	}

	public void insertDangerNotification(HttpServletRequest request, HttpSession session) {

		String message = request.getParameter("message");
		boolean isEmergency = Boolean.valueOf(request.getParameter("emergencyCheck"));
		String categories[] = request.getParameterValues("categories");
		String latString = request.getParameter("lat");
		String lonString = request.getParameter("lon");
		Double lat = null;
		Double lon = null;

		if (latString != null && !"".equals(latString)) {
			lat = Double.valueOf(latString);
		}
		
		if (lonString != null && !"".equals(lonString)) {
			lon = Double.valueOf(lonString);
		}
		
		UserBean userBean = (UserBean) session.getAttribute("user");

		Danger danger = new Danger(message, isEmergency, lat, lon, userBean.getUser().getUserId(), new Date(), Arrays.asList(categories));
		DangerDAO.insert(danger);
	}

	private boolean postValidation(HttpServletRequest request, HttpSession session, String message) throws IOException, ServletException {

		int videoCount = 0;
		int imageCount = 0;

		for (Part part : request.getParts()) {
			
			if (part.getSubmittedFileName() == null) {
				continue;
			}
			
			String fileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
			if (StringUtils.isNullOrEmpty(fileName)) {
				continue;
			}
			
			if (part.getName().equalsIgnoreCase("video")) {
				videoCount++;
			} else if (part.getName().equalsIgnoreCase("image")) {
				imageCount++;
			}
		}
		
		if (videoCount > 0 && imageCount > 0) {

			session.setAttribute("errorNotification", "You are not allowed to import video and image at the same time!");
		} else if (StringUtils.isNullOrEmpty(message) && videoCount == 0 && imageCount == 0) {
			session.setAttribute("errorNotification", "Your post is empty!");
		} else {
			return true;
		}
		return false;
	}

	public Post sharePost(HttpServletRequest request, HttpSession session, String path) {

		String message = request.getParameter("message");
		UserBean userBean = (UserBean) session.getAttribute("user");
		User user = userBean.getUser();
		int userID = user.getUserId();
		Date insertDate = new Date();
		String videoPath = null;
		List<String> imagesPath = new ArrayList<>();

		try {
			if (postValidation(request, session, message)) {

				for (Part part : request.getParts()) {
					if (part.getSubmittedFileName() == null) {
						continue;
					}
					
					String fileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
					if (StringUtils.isNullOrEmpty(fileName)) {
						continue;
					}
					
					InputStream fileContent = part.getInputStream();
					String filePath = Utils.saveFile(userBean.getUser(), fileName, fileContent, path, MainController.ATTACHMENT_LOCATION_PATH);
					
					if (part.getName().equalsIgnoreCase("video")) {
						videoPath = filePath;
					} else {
						imagesPath.add(filePath);
					}
				}
				
				PostBean postBean = new PostBean();
				Post post = postBean.insertPost(userID, message, imagesPath, videoPath, insertDate);
				post.setPostedProfilePic(user.getProfilePicture());
				post.setPostedByFullName(user.getFullName());
				
				return post;
			}
		} catch (IOException | ServletException e) {
			LOGGER.severe(e.getStackTrace().toString());
		}
		return null;
	}
}