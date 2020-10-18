package net.etfbl.main.util;

public enum UserStatusEnum {

	PENDING(0),
	ACTIVE(1),
	INACTIVE(2);
	
	int id;
	
	private UserStatusEnum(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
