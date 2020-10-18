package net.etfbl.main.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;

import net.etfbl.main.bean.CommentBean;
import net.etfbl.main.bean.DangerBean;
import net.etfbl.main.bean.PostBean;
import net.etfbl.main.bean.UserBean;
import net.etfbl.main.dto.Comment;
import net.etfbl.main.dto.Post;

@WebServlet("/Controller")
@MultipartConfig
public class MainController extends HttpServlet {

	//Pages
	private static final long serialVersionUID = 1L;
	public static final String SIGNUP_PAGE 				= "/WEB-INF/pages/signup.jsp";
	public static final String LOGIN_PAGE 				= "/WEB-INF/pages/login.jsp";
	public static final String HOME_PAGE 				= "/WEB-INF/pages/home.jsp";
	public static final String EDIT_PAGE 				= "/WEB-INF/pages/edit.jsp";
	public static final String IMAGE_LOCATION_PATH 		= "images";
	public static final String ATTACHMENT_LOCATION_PATH = "attachments";

	//Beans
	private static final String COMMENT_BEAN		= "commentBean";
	private static final String POST_BEAN 			= "postBean";
	private static final String DANGER_BEAN 		= "dangerBean";
	
	private static final String USER				= "user";
	private static final String ACTION		 		= "action";

	//Actions
	private static final String ACTION_LOGIN 		= "login";
	private static final String ACTION_SIGNUP		= "signup";
	private static final String ACTION_EDIT		 	= "edit";
	private static final String ACTION_SAVE_ACCOUNT = "save-account";
	private static final String ACTION_POST 		= "post";
	private static final String ACTION_LOGOUT 		= "logout";
	private static final String ACTION_DANGER 		= "danger";
	private static final String ACTION_COMMENT 		= "comment";
	private static final String ACTION_LOAD_POSTS 	= "loadPosts";

	public MainController() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		RegistrationController registrationController = new RegistrationController();
		EditController editController = new EditController();
		LoginController loginController = new LoginController();
		HomeController homeController = new HomeController();
		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession();
		String address = LOGIN_PAGE;
		String action = request.getParameter(ACTION);

		if (ACTION_LOGIN.equals(action)) {

			if ((session != null && session.getAttribute(USER) == null)) {
				address = loginController.login(request, session, address);
				initBeans(session);
			} else {
				address = HOME_PAGE;
			}

		} else if (ACTION_SIGNUP.equals(action)) {

			address = SIGNUP_PAGE;
			session.setAttribute("notification", "");

		} else if (ACTION_EDIT.equals(action)) {

			address = SIGNUP_PAGE;
			address = registrationController.register(request, session, address);

		} else if (ACTION_SAVE_ACCOUNT.equals(action)) {

			address = EDIT_PAGE;
			String imagesPath = getServletConfig().getServletContext().getRealPath(IMAGE_LOCATION_PATH);
			address = editController.saveProfile(request, session, address, imagesPath);

		} else if (ACTION_POST.equals(action)) {

			if ((session != null && session.getAttribute(USER) != null)) {

				address = HOME_PAGE;
				String path = getServletConfig().getServletContext().getRealPath(ATTACHMENT_LOCATION_PATH);
				Post post = homeController.sharePost(request, session, path);
				response.getWriter().write(post.toJSON().toString());
				response.getWriter().flush();
				response.getWriter().close();
				return;
			} else {
				address = LOGIN_PAGE;
			}
		} else if (ACTION_COMMENT.equals(action)) {

			if ((session != null && session.getAttribute(USER) != null)) {

				address = HOME_PAGE;
				String path = getServletConfig().getServletContext().getRealPath(ATTACHMENT_LOCATION_PATH);
				Comment comment = homeController.addComment(request, session, path);
				response.getWriter().write(comment.toJSON().toString());
				response.getWriter().flush();
				response.getWriter().close();
				return;
			} else {
				address = LOGIN_PAGE;
			}
		} else if (ACTION_DANGER.equals(action)) {

			if ((session != null && session.getAttribute(USER) != null)) {
				address = HOME_PAGE;
				homeController.insertDangerNotification(request, session);

			} else {
				address = LOGIN_PAGE;

			}
		} else if (ACTION_LOGOUT.equals(action)) {

			if ((session != null && session.getAttribute(USER) != null)) {
				UserBean userBean = (UserBean) session.getAttribute(USER);
				userBean.logout();
				session.invalidate();
			}
			address = LOGIN_PAGE;
		} else if (ACTION_LOAD_POSTS.equals(action)) {

			if ((session != null && session.getAttribute(USER) != null)) {
				address = HOME_PAGE;
				JSONArray posts = homeController.getAllPosts(request, session);
				response.getWriter().write(posts.toString());
				response.getWriter().flush();
				response.getWriter().close();
				return;
			} else {
				address = LOGIN_PAGE;
			}
		} else {

			if ((session != null && session.getAttribute(USER) != null)) {
				address = LOGIN_PAGE;
			}
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher(address);
		dispatcher.forward(request, response);

	}

	private void initBeans(HttpSession session) {
		DangerBean dangerBean = new DangerBean();
		session.setAttribute(DANGER_BEAN, dangerBean);
		PostBean postBean = new PostBean();
		session.setAttribute(POST_BEAN, postBean);
		CommentBean commentBean = new CommentBean();
		session.setAttribute(COMMENT_BEAN, commentBean);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
