package net.etfbl.main.dto;

public class PostAttachment {

	
	private int postAttachmentId;
	private int postId;
	private String value;
	private int attachmentType;
	
	public PostAttachment() {
		super();
	}

	public PostAttachment(String value, int attachmentType) {
		super();
		this.value = value;
		this.attachmentType = attachmentType;
	}
	
	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public int getPostAttachmentId() {
		return postAttachmentId;
	}

	public void setPostAttachmentId(int postAttachmentId) {
		this.postAttachmentId = postAttachmentId;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getAttachmentType() {
		return attachmentType;
	}

	public void setAttachmentType(int attachmentType) {
		this.attachmentType = attachmentType;
	}
}
