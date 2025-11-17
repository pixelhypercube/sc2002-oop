package sc2002OOP.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

import sc2002OOP.obj.User;
import sc2002OOP.obj.careercenterstaff.*;
import sc2002OOP.obj.company.Company;
import sc2002OOP.obj.company.CompanyManager;
import sc2002OOP.obj.companyrepresentative.*;
import sc2002OOP.obj.internshipapplicaton.InternshipApplication;
import sc2002OOP.obj.internshipapplicaton.InternshipApplicationManager;
import sc2002OOP.obj.internshipopportunity.InternshipOpportunity;
import sc2002OOP.obj.internshipopportunity.InternshipOpportunityManager;
import sc2002OOP.obj.student.*;
import sc2002OOP.obj.withdrawalrequest.WithdrawalRequest;
import sc2002OOP.obj.withdrawalrequest.WithdrawalRequestManager;
import sc2002OOP.test.Test;


/**
 * <h1>Internship Placement Management System (IPMS) Main Application</h1>
 * <p>
 * This class serves as the <strong>primary entry point</strong> for the entire system, which contains the <code>main</code> method.
 * </p>
 * <p>
 * It is responsible for initializing all core data managers (users, opportunities, companies, etc.) using the 
 * <b>Singleton design pattern</b>, handling user authentication (login/registration), and driving the primary application loop and menu system.
 * </p>
 * @author Kee Kai Wen
 * @author Kelvin Tay Wei Jie
 * @author Koay Jun Zhi
 * @author Lim Jia Wei Jerald
 * @author Teo Kai Jie
 * @version 1.0
 * @see sc2002OOP.obj.User
 */
public class IPMS {
	
	/**
	 * Displays the application title and a welcoming message to the console.
	 */
	public static void printLogo() {
		System.out.println("===================================================================");
		System.out.println();
		System.out.println(
				  "   █████████████  ██████████████  ████      ████  ██████████████   \r\n"
				+ "  ▒▒▒▒▒▒████▒▒▒  ▒████     ▒████ ▒██████  ██████ ▒████             \r\n"
				+ "       ▒████     ▒████     ▒████ ▒████▒████▒████ ▒████             \r\n"
				+ "       ▒████     ▒██████████████ ▒████▒▒▒▒ ▒████ ▒██████████████   \r\n"
				+ "       ▒████     ▒████▒▒▒▒▒▒▒▒▒  ▒████     ▒████ ▒▒▒▒▒▒▒▒▒▒▒████   \r\n"
				+ "       ▒████     ▒████           ▒████     ▒████           ▒████   \r\n"
				+ "       ▒████     ▒████           ▒████     ▒████           ▒████   \r\n"
				+ "  ▒█████████████ ▒████           ▒████     ▒████ ▒██████████████   \r\n"
				+ "  ▒▒▒▒▒▒▒▒▒▒▒▒▒  ▒▒▒▒            ▒▒▒▒      ▒▒▒▒  ▒▒▒▒▒▒▒▒▒▒▒▒▒▒     ");
		System.out.println();
		System.out.println("===================================================================");
		System.out.println();
		System.out.println("Welcome to the Internship Placement Management System (IPMS)!");
		System.out.println();
	}
	
	/**
	 * Initializes all core data managers using the Singleton pattern (<code>getInstance()</code>), 
	 * which triggers the loading of persistent data from disk if available.
	 * This method is called once at application startup.
	 */
	private static void initInstances() {
		// USERS
		CareerCenterStaffManager.getInstance();
		CompanyRepresentativeManager.getInstance();
		StudentManager.getInstance();
		
		// INTERNSHIP STUFF, WITHDRAWAL REQS & COMPANIES
		InternshipOpportunityManager.getInstance();
		InternshipApplicationManager.getInstance();
		WithdrawalRequestManager.getInstance();
		CompanyManager.getInstance();
	}
	
	/**
	 * Safely shuts down and persists all managed data by calling the <code>close()</code> method 
	 * on all Singleton manager instances.
	 * This method is called upon application exit to ensure data integrity.
	 */
	private static void close() {
		// USERS
		CareerCenterStaffManager.close();
		CompanyRepresentativeManager.close();
		StudentManager.close();
		
		// INTERNSHIP STUFF, WITHDRAWAL REQS & COMPANIES
		InternshipOpportunityManager.close();
		InternshipApplicationManager.close();
		WithdrawalRequestManager.close();
		CompanyManager.close();
	}
	
	/**
	 * Prints the main application menu options (Login, Register, Exit) to the console.
	 */
	public static void printMenu() {
		System.out.println("==== Please select a choice to continue ====");
		System.out.println("(1) Login");
		System.out.println("(2) Register (Company Representative)");
		System.out.println("(3) Exit");
	}
	
	/**
	 * The main entry point of the IPMS application.
	 * It initializes all data, enters the primary application loop, handles user input for 
	 * authentication (login/registration), and drives the session based on the authenticated user's role.
	 *
	 * @param args Command line arguments (not used).
	 */
	public static void main(String[] args) {
		initInstances();
		System.out.print("\033[H\033[2J");
		try (
				Scanner sc = new Scanner(System.in);
			) {
			int choice = 0;
			while (choice != 3) {
				printLogo();
				printMenu();
				
				// load users
				ArrayList<User> users = new ArrayList<>();
				// retrieve all users from all files
				for (Student student : StudentManager.getStudents()) {
					users.add(student);
				}
				for (CareerCenterStaff st : CareerCenterStaffManager.getStaff()) {
					users.add(st);
				}
				for (CompanyRepresentative companyRep : CompanyRepresentativeManager.getCompanyReps()) {
					users.add(companyRep);
				}
				
				// TESTING DATA
//				Test.printCsvData();
				Test.printAllRecords();
//				Test.printTestTables();
//				Test.getFromCSVFilesAndUpdate();
				System.out.print("Your Choice: ");
				
				
				String input = sc.next();
				try {
	                choice = Integer.parseInt(input.trim()); 
	                
	                if (choice==1) {
	                    User user = User.login(sc, users);
	                    if (user != null) user.displayHome(sc);
	                } else if (choice==2) {
	                    CompanyRepresentativeManager.register(sc);
	                } else if (choice==3) {
	                    System.out.println("Bon Voyage");
	                    close();
	                    break;
	                } else {
	                	System.out.print("\033[H\033[2J");
	                    System.out.println("Invalid choice. Please enter a valid number (1, 2 or 3).");
	                }
	            } catch (NumberFormatException e) {
	            	System.out.print("\033[H\033[2J");
	                System.out.println("Invalid choice. Please enter a valid number (1, 2 or 3).");
	                choice = 0;
	            }
			}
		} catch (Exception e) {
			System.err.println("An unexpected error occurred: " + e.getMessage());
		}
	}
}
