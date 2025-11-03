package sc2002OOP.obj;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;
import sc2002OOP.main.PasswordManager;
import sc2002OOP.obj.companyrepresentative.CompanyRepresentative;
import sc2002OOP.obj.companyrepresentative.CompanyRepresentativeStatus;

public abstract class User implements Serializable {
	private static final long serialVersionUID = 5930574854473287191L;
	private String userID, name, email, password;
	
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
			System.out.print("\033[H\033[2J");
			System.out.println("==== Login ====");
			System.out.print("Enter Your User ID: ");
			uID = sc.next();
			if (!uID.isEmpty()) {
				for (User user : users) {
					if (user.getUserID().equals(uID)) {
						userFound = true;
						
						// if user is Company Rep -> check whether status is 'Approved'
						if (user.getClass().getSimpleName().equals("CompanyRepresentative")) {
							if (((CompanyRepresentative)user).getStatus()==CompanyRepresentativeStatus.PENDING) {
								System.out.println("Sorry, your account is still yet to be approved by one of our career center staff. Please try again later.");
								continue;
							} else if (((CompanyRepresentative)user).getStatus()==CompanyRepresentativeStatus.REJECTED) {
								System.out.println("Sorry, your account has been rejected by one of our career center staff.");
								continue;
							}
						}
						while (pwd.isEmpty() || !PasswordManager.verifyPassword(pwd, user.getPassword())) {
							System.out.print("Password: ");
							pwd = sc.next();
							
							if (PasswordManager.verifyPassword(pwd, user.getPassword())) {
								System.out.print("\033[H\033[2J");
								System.out.println("Logged in successfully!");
								return user;
							} else {
								System.out.print("\033[H\033[2J");
								System.out.println("Wrong password, please try again!");
							}
//								
						}
					}
				}
				if (!userFound) System.out.println("Sorry, User ID "+uID+" not found. Please try again.");
			}
		}
		return null;

	}
	
	// ABSTRACT METHODS
	public abstract void changePassword(Scanner sc);
	public abstract void displayHome(Scanner sc);
	public abstract void viewProfile(Scanner sc);
	public abstract void print();
	
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
