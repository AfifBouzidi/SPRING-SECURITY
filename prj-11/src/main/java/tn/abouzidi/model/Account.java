package tn.abouzidi.model;

public class Account {

	private String name;
	private String password;
	private String role;

	public Account(String name, String password, String role) {
		super();
		this.name = name;
		this.password = password;
		this.role = role;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public String getRole() {
		return role;
	}
}