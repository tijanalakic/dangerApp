package net.etfbl.main.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;

import net.etfbl.main.dao.CommentDAO;
import net.etfbl.main.dto.Comment;

public class CommentBean implements Serializable{


	private static final long serialVersionUID = 1L;
	
	public List<Comment> getAllById(int id){
		
		List<Comment> comments=CommentDAO.getAllByPostId(id);
		Collections.reverse(comments);
		return comments;
	}
	
	public List<JSONObject> getAllByIdJSON(int id){
		
		List<Comment> comments=CommentDAO.getAllByPostId(id);
		List<JSONObject> json=new ArrayList<>();
		for(Comment comment:comments) {
			json.add(comment.toJSON());
		}
		return json;
	}
	
	public Comment addComment(int postId, String message, String imageType, int userId, String image) {
		
		Comment comment=new Comment(postId, message, imageType, userId, image, new Date());
		
		if(CommentDAO.insert(comment)) {
			return comment;
		}
		return null;
	}

	
}
