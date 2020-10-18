package net.etfbl.main.dto;

public class DangerCategory {

	private int dangerId;
	private String name;

	public DangerCategory() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DangerCategory(int dangerId, String name) {
		super();
		this.dangerId = dangerId;
		this.name = name;
	}

	public int getDangerId() {
		return dangerId;
	}

	public void setDangerId(int dangerId) {
		this.dangerId = dangerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
