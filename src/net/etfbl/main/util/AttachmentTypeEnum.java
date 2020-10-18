package net.etfbl.main.util;

public enum AttachmentTypeEnum {

	LINK(0),
	IMAGE(1),
	VIDEO(2),
	YOUTUBE(3);
	
	int id;
	
	private AttachmentTypeEnum(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
