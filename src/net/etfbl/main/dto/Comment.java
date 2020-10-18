package net.etfbl.main.dto;

import java.text.DateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

public class Comment {

	private int commentId;
	private int postId;
	private int userId;
	private String message;
	private String imageType;
	private String postedUserFullName;
	private String userProfilePicture;
	private Date insertDate;
	private String image;

	public Comment() {
		super();
	}

	public Comment(int commentId, int postId, String message, String imageType, String postedUserFullName, String image,
			String userProfilePicture, Date insertDate) {
		super();
		this.commentId = commentId;
		this.postId = postId;
		this.message = message;
		this.imageType = imageType;
		this.postedUserFullName = postedUserFullName;
		this.image = image;
		this.userProfilePicture = userProfilePicture;
		this.insertDate = insertDate;
	}

	public Comment(int postId, String message, String imageType, int userId, String image, Date insertDate) {
		super();
		this.postId = postId;
		this.message = message;
		this.imageType = imageType;
		this.userId = userId;
		this.image = image;
		this.insertDate = insertDate;
	}

	public int getCommentId() {
		return commentId;
	}

	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getImageType() {
		return imageType;
	}

	public void setImageType(String imageType) {
		this.imageType = imageType;
	}

	public String getPostedUserFullName() {
		return postedUserFullName;
	}

	public void setPostedUserFullName(String postedUserFullName) {
		this.postedUserFullName = postedUserFullName;
	}

	public String getUserProfilePicture() {
		return userProfilePicture;
	}

	public void setUserProfilePicture(String userProfilePicture) {
		this.userProfilePicture = userProfilePicture;
	}

	public Date getInsertDate() {
		return insertDate;
	}

	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public JSONObject toJSON() {

		DateFormat df = new java.text.SimpleDateFormat("dd MMM yyyy HH:mm");
		JSONObject json = new JSONObject();

		try {

			json.append("postId", postId);
			json.append("profilePicture", userProfilePicture);
			json.append("fullname", postedUserFullName);
			json.append("comment", message);
			json.append("image", image);
			json.append("date", df.format(insertDate));

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return json;
	}

}
