package net.etfbl.main.util;

public enum PostTypeEnum {

	RSS(0), 
	POST(1);

	int id;

	private PostTypeEnum(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
