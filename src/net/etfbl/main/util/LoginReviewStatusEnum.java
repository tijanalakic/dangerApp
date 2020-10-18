package net.etfbl.main.util;

public enum LoginReviewStatusEnum {

	ACTIVE(1),
	INACTIVE(2);
	
	int id;
	
	private LoginReviewStatusEnum(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
