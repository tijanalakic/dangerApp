package net.etfbl.main.rss;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FeedMessage {

	private String title;
	private String description;
	private String author;
	private String link;
	private String guid;
	private Date pubDate;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public Date getPubDate() {
		return pubDate;
	}

	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}

	public String getFormattedPubDate() {
		SimpleDateFormat date = new SimpleDateFormat("HH:mm:ss, dd.MM.yyyy.");
		return date.format(pubDate);
	}

	@Override
	public String toString() {

		return "FeedMessage [title=" + title + ", description=" + description + ", link=" + link + ", author=" + author
				+ ", guid=" + guid + "]";
	}

}
