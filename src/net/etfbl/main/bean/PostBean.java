package net.etfbl.main.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.mysql.cj.util.StringUtils;

import net.etfbl.main.dao.PostDAO;
import net.etfbl.main.dto.Post;
import net.etfbl.main.dto.PostAttachment;
import net.etfbl.main.rss.Feed;
import net.etfbl.main.rss.FeedMessage;
import net.etfbl.main.rss.RSSFeedParser;
import net.etfbl.main.util.AttachmentTypeEnum;
import net.etfbl.main.util.PostTypeEnum;
import net.etfbl.main.util.Utils;

public class PostBean implements Serializable{

	
	private static final long serialVersionUID = 1L;
	private static final String RSS_FEED="https://europa.eu/newsroom/calendar.xml_en?field_nr_events_by_topic_tid=151";
	
	public List<Post> getAll(){
		
		List<Post> list = getAllFeedPosts();
		List<Post> posts = PostDAO.getAll();
		Collections.sort(posts);
		list.addAll(posts);
		
		return list;
		
	}
	
	public Post insertPost(int id, String messages, List<String> imagePathList, String videoPath, Date insertDate) {
		
		List<PostAttachment> attachments = new ArrayList<>();
		
		if(imagePathList != null && imagePathList.size() > 0) {
			
			for(String img : imagePathList) {
				
				PostAttachment attachment = new PostAttachment(img, AttachmentTypeEnum.IMAGE.getId());
				attachments.add(attachment);
			}
		}else if(videoPath!=null && !"".equals(videoPath)) {
			
			PostAttachment attachment=new PostAttachment(videoPath, AttachmentTypeEnum.VIDEO.getId());
			attachments.add(attachment);
			
		}else {
			
			PostAttachment attachment=getYoutubeAttachments(messages);
			if(attachment!=null) {
				attachments.add(attachment);
			}
		}
		Post post=new Post(PostTypeEnum.POST, id, messages, insertDate, attachments);
		
		if(PostDAO.insert(post)) {
				return post;
		}
		return null;
	}
	

	private List<Post> getAllFeedPosts(){
		
		List<FeedMessage> feedMessageList=getAllFeedMessages();
		List<Post> postList=new ArrayList<>();
		for(FeedMessage message: feedMessageList) {
			
			Post post=new Post(PostTypeEnum.RSS, message.getTitle(), message.getDescription(), message.getAuthor(), message.getPubDate(), message.getLink());
			postList.add(post);
		}
		return postList;
	}
	
	private List<FeedMessage> getAllFeedMessages(){
		
		RSSFeedParser parser = new RSSFeedParser(RSS_FEED);
		Feed feed = parser.readFeed();
		return feed.getInputs();
		
	}
	
	private PostAttachment getYoutubeAttachments(String message) {
		
		String embedVideo = Utils.getYoutubeEmbedVideo(message);
		if(!StringUtils.isNullOrEmpty(embedVideo)) {
			
			PostAttachment attachment = new PostAttachment(embedVideo, AttachmentTypeEnum.YOUTUBE.getId());
			return attachment;
		}
		return null;
	}
}
