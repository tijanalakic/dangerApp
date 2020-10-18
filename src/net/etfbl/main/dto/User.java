package net.etfbl.main.dto;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	private int userId;
	private String name;
	private String surname;
	private String username;
	private String password;
	private String email;
	private String profilePicture;
	private String country;
	private boolean appNotification;
	private boolean emailNotification;
	private String city;
	private String region;
	private int loginCounter;
	private boolean admin;
	private int userStatus;
	private Date dateCreated;
	private String countryCode;

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(int userId, String name, String surname, String username, String password, String email,
			String profilePicture, String country, boolean appNotification, boolean emailNotification, String city,
			String region, int loginCount, boolean admin, int userStatus, Date dateCreated, String countryCode) {
		super();
		this.userId = userId;
		this.name = name;
		this.surname = surname;
		this.username = username;
		this.password = password;
		this.email = email;
		this.profilePicture = profilePicture;
		this.country = country;
		this.appNotification = appNotification;
		this.emailNotification = emailNotification;
		this.city = city;
		this.region = region;
		this.loginCounter = loginCount;
		this.admin = admin;
		this.userStatus = userStatus;
		this.dateCreated = dateCreated;
		this.countryCode = countryCode;
	}

	public User(String name, String surname, String username, String password, String email, String profilePicture,
			String country, boolean appNotification, boolean emailNotification, String city, String region,
			int loginCounter, boolean admin, int userStatus, Date dateCreated, String countryCode) {
		super();
		this.name = name;
		this.surname = surname;
		this.username = username;
		this.password = password;
		this.email = email;
		this.profilePicture = profilePicture;
		this.country = country;
		this.appNotification = appNotification;
		this.emailNotification = emailNotification;
		this.city = city;
		this.region = region;
		this.loginCounter = loginCounter;
		this.admin = admin;
		this.userStatus = userStatus;
		this.dateCreated = dateCreated;
		this.countryCode = countryCode;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public boolean isAppNotification() {
		return appNotification;
	}

	public void setAppNotification(boolean appNotification) {
		this.appNotification = appNotification;
	}

	public boolean isEmailNotification() {
		return emailNotification;
	}

	public void setEmailNotification(boolean emailNotification) {
		this.emailNotification = emailNotification;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public int getLoginCounter() {
		return loginCounter;
	}

	public void setLoginCounter(int loginCounter) {
		this.loginCounter = loginCounter;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public int getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(int userStatus) {
		this.userStatus = userStatus;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getFullName() {
		return name + " " + surname;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

}
