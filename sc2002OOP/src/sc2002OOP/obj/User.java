package sc2002OOP.obj;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sc2002OOP.main.FileIOHandler;

public abstract class User {
	private String userID, name, email, password = "password";
	
	public User() {}
	
	public User(String userID, String name, String email, String password) {
		this.userID = userID;
		this.name = name;
		this.email = email;
		this.password = password;
	}
	
	public static User login(Scanner sc, ArrayList<User> users) {
		String uID = "";
		String pwd = "";

		boolean userFound = false;
		while (uID.isEmpty() || pwd.isEmpty() || !userFound) {
			System.out.print("Enter your user ID: ");
			uID = sc.next();
			if (uID.length()>0) {
				for (User user : users) {
					if (user.getUserID().equals(uID)) {
						userFound = true;
						
						// if user is Company Rep -> check whether status is 'Approved'
						if (user.getClass().getSimpleName().equals("CompanyRepresentative")) {
							if (!((CompanyRepresentative)user).getStatus().equals("Approved")) {
								System.out.println("Sorry, your account is still yet to be approved by one of our career center staff. Please try again later.");
								continue;
							}
						}
						while (pwd.isEmpty() || !user.getPassword().equals(pwd)) {
							System.out.print("Enter your password: ");
							pwd = sc.next();
							if (pwd.length()<=0) System.out.println();
							if (!user.getPassword().equals(pwd)) {
								System.out.println("Wrong password, please try again!");
								continue;
							} else {
								System.out.println("Logged in successfully!");
								return user;
							}
						}
					}
				}
				if (!userFound) System.out.println("Sorry, User ID "+uID+" not found. Please try again.");
			}
		}
		return null;

	}
	
	public void changePassword(Scanner sc) {
		String newPassword = "";
		while (newPassword.length()<8) {
			System.out.print("Enter new password: ");
			newPassword = sc.next();
			if (newPassword.length()<8)
				System.out.println("Password must be at least 8 chars.");
		}
		
		String repeatNewPassword = "";
		while (!repeatNewPassword.equals(newPassword)) {
			System.out.print("Re-enter password: ");
			repeatNewPassword = sc.next();
			if (!repeatNewPassword.equals(newPassword))
				System.out.println("Passwords do not match!");
		}
		
		System.out.println("Your password has been successfully changed!");
	}
	
	public void forgotPassword(Scanner sc) {
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
	
	
	// ABSTRACT METHODS
	public abstract void displayHome(Scanner sc);
	public abstract void viewProfile(Scanner sc);
	public abstract void refreshData(Scanner sc);
	
	// GETTERS & SETTERS

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
