package sc2002OOP.obj;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;
import sc2002OOP.main.PasswordManager;
import sc2002OOP.obj.companyrepresentative.CompanyRepresentative;
import sc2002OOP.obj.companyrepresentative.CompanyRepresentativeStatus;


/**
 * <h1>User Base Class</h1>
 * <p>
 * This is the <b>abstract base class</b> for all user roles within the IPMS ({@link sc2002OOP.obj.student.Student students}, 
 * {@link sc2002OOP.obj.companyrepresentative.CompanyRepresentative company representatives}, and 
 * {@link sc2002OOP.obj.careercenterstaff.CareerCenterStaff career center staff}).
 * It contains all the core user functions, such as login/logout authentication, viewing/filtering internships, and viewing common profile details.
 * </p>
 * @author Kee Kai Wen
 * @author Kelvin Tay Wei Jie
 * @author Koay Jun Zhi
 * @author Lim Jia Wei Jerald
 * @author Teo Kai Jie
 * @version 1.0
 * @see sc2002OOP.obj.careercenterstaff.CareerCenterStaff
 * @see sc2002OOP.obj.companyrepresentative.CompanyRepresentative
 * @see sc2002OOP.obj.student.Student
 */

public abstract class User implements Serializable {
	private static final long serialVersionUID = 5930574854473287191L;
	/**
     * The unique identifier for the user (e.g., student matriculation number, staff ID).
     */
	private String userID;
    
    /**
     * The full name of the user.
     */
    private String name;
    
    /**
     * The official email address of the user.
     */
    private String email;
    
    /**
     * The securely hashed password of the user.
     */
    private String password;
    
    /**
     * Stores the current filtering preferences for viewing internships, persisted across sessions.
     */
	private InternshipFilterSettings internshipFilterSettings;
	
	/**
     * Default constructor.
     */
	public User() {}
	
	/**
     * Constructs a User object with all mandatory authentication fields.
     * The password should be stored as a securely hashed value.
     * * @param userID The unique identifier for the user.
     * @param name The user's full name.
     * @param email The user's email address.
     * @param password The user's hashed password.
     */
	public User(String userID, String name, String email, String password) {
		this.userID = userID;
		this.name = name;
		this.email = email;
		this.password = password;
		this.internshipFilterSettings = new InternshipFilterSettings();
	}

	/**
     * Constructs a partial User object, typically used during registration before a password is set, 
     * or for users whose password is managed externally (Note: not generally used in final system logic).
     * * @param userID The unique identifier for the user.
     * @param name The user's full name.
     * @param email The user's email address.
     */
	public User(String userID, String name, String email) {
		this.userID = userID;
		this.name = name;
		this.email = email;
		this.internshipFilterSettings = new InternshipFilterSettings();
	}
	
	/**
     * Handles the static authentication process for any user logging into the system.
     * This method iterates through all available user accounts, verifies the User ID, 
     * and securely checks the entered password against the stored hash.
     * * @param sc The <code>Scanner</code> object used to capture User ID and password input.
     * @param users An <code>ArrayList</code> containing all registered user accounts (Students, Staff, Reps).
     * @return The authenticated <code>User</code> object if login is successful, or <code>null</code> if authentication fails or if a Company Representative is pending/rejected.
     */
	public static User login(Scanner sc, ArrayList<User> users) {
		System.out.print("\033[H\033[2J");
		String uID = "";
		String pwd = "";

		boolean userFound = false;
		while (uID.isEmpty() || pwd.isEmpty() || !userFound) {
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
				if (!userFound) {
					System.out.print("\033[H\033[2J");
					System.out.println("Sorry, User ID "+uID+" not found. Please try again.");
				}
			}
		}
		return null;

	}
	
	// ABSTRACT METHODS
	
	/**
     * Abstract method enforced on all subclasses to handle the user-specific logic 
     * for securely changing their password.
     * @param sc The <code>Scanner</code> object for input.
     */
	public abstract void changePassword(Scanner sc);
	
	/**
     * Abstract method enforced on all subclasses to display the role-specific main menu 
     * and handle navigation for the authenticated user.
     * @param sc The <code>Scanner</code> object for input.
     */
	public abstract void displayHome(Scanner sc);
	
	/**
     * Abstract method enforced on all subclasses to display the user's personal profile details.
     * @param sc The <code>Scanner</code> object for input.
     */
	public abstract void viewProfile(Scanner sc);
	
	/**
     * Abstract method enforced on all subclasses to view and filter internship opportunities, 
     * with potential differences in filtering criteria or visibility based on the user's role.
     * @param sc The <code>Scanner</code> object for input.
     */
	public abstract void viewInternshipOpps(Scanner sc);
	
	// GETTERS & SETTERS
	
	/**
     * Retrieves the user's current filter settings used for viewing internships.
     * @return The <code>InternshipFilterSettings</code> object.
     */
	public InternshipFilterSettings getInternshipFilterSettings() {
		return internshipFilterSettings;
	}

    /**
     * Sets the user's current filter settings for viewing internships.
     * @param internshipFilterSettings The new filter settings object.
     */
	public void setInternshipFilterSettings(InternshipFilterSettings internshipFilterSettings) {
		this.internshipFilterSettings = internshipFilterSettings;
	}
	
    /**
     * Retrieves the full name of the user.
     * @return The name as a <code>String</code>.
     */
	public String getName() {
		return name;
	}

    /**
     * Sets the full name of the user.
     * @param name The new name as a <code>String</code>.
     */
	public void setName(String name) {
		this.name = name;
	}

    /**
     * Retrieves the user's email address.
     * @return The email as a <code>String</code>.
     */
	public String getEmail() {
		return email;
	}

    /**
     * Sets the user's email address.
     * @param email The new email as a <code>String</code>.
     */
	public void setEmail(String email) {
		this.email = email;
	}

    /**
     * Retrieves the user's securely hashed password.
     * @return The hashed password as a <code>String</code>.
     */
	public String getPassword() {
		return password;
	}

    /**
     * Sets the user's password (should always be a securely hashed value).
     * @param password The new hashed password as a <code>String</code>.
     */
	public void setPassword(String password) {
		this.password = password;
	}

    /**
     * Retrieves the user's unique identifier.
     * @return The User ID as a <code>String</code>.
     */
	public String getUserID() {
		return userID;
	}

    /**
     * Sets the user's unique identifier.
     * @param userID The new User ID as a <code>String</code>.
     */
	public void setUserID(String userID) {
		this.userID = userID;
	}
}
