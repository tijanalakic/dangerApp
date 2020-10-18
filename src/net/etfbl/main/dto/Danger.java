package net.etfbl.main.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Danger implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int dangerId;
	private String message;
	private boolean emergency;
	private Double lat;
	private Double lon;
	private int userId;
	private Date insertDate;
	private String postedByFullName;
	private List<String> categories;

	public Danger() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Danger(int dangerId, String message, boolean emergency, Double lat, Double lon, Date insertDate,
			String postedByFullName, List<String> categories) {
		super();
		this.dangerId = dangerId;
		this.message = message;
		this.emergency = emergency;
		this.lat = lat;
		this.lon = lon;
		this.insertDate = insertDate;
		this.postedByFullName = postedByFullName;
		this.categories = categories;
	}

	public Danger(int dangerId, String message, boolean emergency, Double lat, Double lon, int userId, Date insertDate,
			List<String> categories) {
		super();
		this.dangerId = dangerId;
		this.message = message;
		this.emergency = emergency;
		this.lat = lat;
		this.lon = lon;
		this.userId = userId;
		this.insertDate = insertDate;
		this.categories = categories;
	}

	public Danger(String message, boolean emergency, Double lat, Double lon, int userId, Date insertDate,
			List<String> categories) {
		super();
		this.message = message;
		this.emergency = emergency;
		this.lat = lat;
		this.lon = lon;
		this.userId = userId;
		this.insertDate = insertDate;
		this.categories = categories;
	}

	public int getDangerId() {
		return dangerId;
	}

	public void setDangerId(int dangerId) {
		this.dangerId = dangerId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isEmergency() {
		return emergency;
	}

	public void setEmergency(boolean emergency) {
		this.emergency = emergency;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getLon() {
		return lon;
	}

	public void setLon(Double lon) {
		this.lon = lon;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Date getInsertDate() {
		return insertDate;
	}

	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}

	public String getPostedByFullName() {
		return postedByFullName;
	}

	public void setPostedByFullName(String postedByFullName) {
		this.postedByFullName = postedByFullName;
	}

	public List<String> getCategories() {
		return categories;
	}

	public void setCategories(List<String> categories) {
		this.categories = categories;
	}

}
