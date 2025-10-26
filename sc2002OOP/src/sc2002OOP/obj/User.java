package sc2002OOP.obj;

import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User {
	private String userID, name, email, password;
	
	public User() {}
	
	public User(String userID, String name, String email, String password) {
		this.setUserID(userID);
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
		
		this.setUserID(uID);
		this.password = password;
	}
	
	public void forgotPassword() throws IOException {
		try (Scanner sc = new Scanner(System.in)) {
			String uID = null;
			String password = null;
			
			System.out.print("Enter your NTU Email: ");
			String email = sc.next();
			final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
			Pattern pattern = Pattern.compile(EMAIL_REGEX);
			
			Matcher matcher = pattern.matcher(EMAIL_REGEX);
			if (matcher.matches()) {
				
			}
			
		}
	}
	
	public void displayHome() {
		System.out.println("Welcome, " + name);
		
		
	}
	
	public void logout() {
		
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

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}
}
