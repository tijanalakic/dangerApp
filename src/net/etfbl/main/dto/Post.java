package net.etfbl.main.dto;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import net.etfbl.main.util.AttachmentTypeEnum;
import net.etfbl.main.util.PostTypeEnum;
import net.etfbl.main.util.Utils;


public class Post implements Comparable<Post> {

	
	private int postId;
	private int userId;
	private PostTypeEnum postType;
	private String message;
	private String postedByFullName;
	private String username;
	private String title;
	private String link;
	private String postedProfilePic;
	private Date postedDate;
	private List<PostAttachment> postAttachments;
	
	
	
	public Post() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Post(int postId, PostTypeEnum postType, String message, String postedByFullName, String username, String profilePic, Date postedDate, List<PostAttachment> postAttachments) {
		super();
		this.postId = postId;
		this.postType = postType;
		this.message = message;
		this.postedByFullName = postedByFullName;
		this.username = username;
		this.postedDate = postedDate;
		this.postAttachments = postAttachments;
		this.postedProfilePic = profilePic;
	}
	
	public Post(PostTypeEnum postType, int userId, String message, Date postedDate, List<PostAttachment> postAttachments) {
		super();
		this.userId = userId;
		this.postType = postType;
		this.message = message;
		this.postedDate = postedDate;
		this.postAttachments = postAttachments;
	}
	
	public Post(PostTypeEnum postType, String title, String message, String postedByFullName, Date postedDate, String link) {
		super();
		this.postType = postType;
		this.title = title;
		this.message = message;
		this.postedByFullName = postedByFullName;
		this.postedDate = postedDate;
		this.link = link;
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



	public PostTypeEnum getPostType() {
		return postType;
	}



	public void setPostType(PostTypeEnum postType) {
		this.postType = postType;
	}



	public String getMessage() {
		return message;
	}



	public void setMessage(String message) {
		this.message = message;
	}



	public String getPostedByFullName() {
		return postedByFullName;
	}



	public void setPostedByFullName(String postedByFullName) {
		this.postedByFullName = postedByFullName;
	}



	public String getUsername() {
		return username;
	}



	public void setUsername(String username) {
		this.username = username;
	}



	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
	}



	public String getLink() {
		return link;
	}



	public void setLink(String link) {
		this.link = link;
	}



	public String getPostedProfilePic() {
		return postedProfilePic;
	}



	public void setPostedProfilePic(String postedProfilePic) {
		this.postedProfilePic = postedProfilePic;
	}



	public Date getPostedDate() {
		return postedDate;
	}



	public void setPostedDate(Date postedDate) {
		this.postedDate = postedDate;
	}



	public List<PostAttachment> getPostAttachments() {
		return postAttachments;
	}



	public void setPostAttachments(List<PostAttachment> postAttachments) {
		this.postAttachments = postAttachments;
	}



	@Override
	public int compareTo(Post post) {
		// TODO Auto-generated method stub
		return postedDate.compareTo(post.postedDate);
	}
	
	public String getParsedMessage() {
		return Utils.parseMessage(message);
	}
	
	public JSONObject toJSON() {
		
		DateFormat df=new java.text.SimpleDateFormat("dd MMM yyyy HH:mm");
		JSONObject json=new JSONObject();
		
		try {
			json.append("postId", postId);
			json.append("type", postType.getId());
			json.append("parsedMessage",getParsedMessage());
			json.append("message", message);
			json.append("link", link);
			json.append("title",title);
			json.append("date",df.format(postedDate));
			json.append("postedUserProfilePicture", postedProfilePic);
			json.append("postedUsername", postedByFullName);
			
			List<String> images=new ArrayList<>();
			List<String> youtube=new ArrayList<>();
			List<String> videos=new ArrayList<>();
			List<String> links=new ArrayList<>();
			
			if(postAttachments!=null) {
				
				for(PostAttachment a: postAttachments) {
					String value=a.getValue();
					
					if(AttachmentTypeEnum.IMAGE.getId()==a.getAttachmentType()) {
						images.add(value);
					}else if(AttachmentTypeEnum.YOUTUBE.getId()==a.getAttachmentType()) {
						youtube.add(value);
					}else if(AttachmentTypeEnum.VIDEO.getId()==a.getAttachmentType()) {
						videos.add(value);
					}else if(AttachmentTypeEnum.LINK.getId()==a.getAttachmentType()) {
						links.add(value);
					}
				}
				if(!images.isEmpty()) {
					json.append("images",images);
				}
				if(!youtube.isEmpty()) {
					json.append("youtube",youtube);
				}
				if(!videos.isEmpty()) {
					json.append("videos",videos);
				}
				if(!links.isEmpty()) {
					json.append("links",links);
				}
			}
		}
		catch(JSONException ex) {
			ex.printStackTrace();
		}
		return json;
	}

	
}
