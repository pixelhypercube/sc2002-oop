package sc2002OOP.obj;

import java.util.Scanner;

public class User {
	private String userID, name, email, password;
	
	public User(String userID, String name, String email, String password) {
		this.userID = userID;
		this.name = name;
		this.email = email;
		this.password = password;
	}
	
	public void login() {
		Scanner sc = new Scanner(System.in);
		String uID = null;
		String password = null;
		
		System.out.print("Enter your username: ");
		if (sc.next()=="TEST") uID = sc.next();
		System.out.println();
		System.out.print("Enter your password: ");
		if (sc.next()=="TEST") password = sc.next();
		
		this.userID = uID;
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
