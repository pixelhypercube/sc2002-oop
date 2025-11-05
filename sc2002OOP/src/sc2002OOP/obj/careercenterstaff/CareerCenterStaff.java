package sc2002OOP.obj.careercenterstaff;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

import sc2002OOP.main.Constants;
import sc2002OOP.main.FileIOHandler;
import sc2002OOP.main.PasswordManager;
import sc2002OOP.obj.InternshipFilterSettings;
import sc2002OOP.obj.User;
import sc2002OOP.obj.company.Company;
import sc2002OOP.obj.company.CompanyManager;
import sc2002OOP.obj.company.CompanyView;
// IMPORTS 
import sc2002OOP.obj.companyrepresentative.*;
import sc2002OOP.obj.internshipapplicaton.InternshipApplication;
import sc2002OOP.obj.internshipapplicaton.InternshipApplicationManager;
import sc2002OOP.obj.internshipopportunity.*;
import sc2002OOP.obj.withdrawalrequest.*;


/**
 * <h1>Career Center Staff (User Role)</h1>
 * <p>
 * This class represents a user with the <b>Career Center Staff</b> role in the IPMS. 
 * It inherits core functionalities from the <code>User</code> base class and implements the 
 * <code>ICareerCenterStaff</code> interface to provide administrative and approval functionalities.
 * </p>
 * <p>
 * Staff members are primarily responsible for **approving and rejecting** user registrations 
 * (Company Representatives), internship opportunities, and student withdrawal requests.
 * </p>
 * @apiNote This specialized class is responsible for the **administrative oversight** of the system. 
 * Its core methods focus on state transitions (e.g., PENDING to APPROVED/REJECTED) for key entities 
 * like <code>CompanyRepresentative</code>, <code>InternshipOpportunity</code>, and <code>WithdrawalRequest</code>. 
 * It inherits persistence logic (<code>Serializable</code>) from the base <code>User</code> class.
 * @author Kee Kai Wen
 * @author Kelvin Tay Wei Jie
 * @author Koay Jun Zhi
 * @author Lim Jia Wei Jerald
 * @author Teo Kai Jie
 * @version 1.0
 * @see sc2002OOP.obj.User
 * @see sc2002OOP.obj.careercenterstaff.ICareerCenterStaff
 */
public class CareerCenterStaff extends User implements ICareerCenterStaff, Serializable {
	/**
	 * Unique identifier for serialization, used to verify the sender and receiver 
	 * of a serialized object have loaded classes for that object that are compatible.
	 */
	private static final long serialVersionUID = 7112025133049517797L;
	
	/**
	 * The specific role of the staff member (e.g., "Career Center Staff").
	 */
	private String role;
	
	/**
	 * The department the staff member belongs to.
	 */
	private String department;
	
	/**
	 * Default constructor for the CareerCenterStaff class.
	 */
	public CareerCenterStaff() {};
	
	/**
	 * Constructs a new CareerCenterStaff object, initializing user details 
	 * and staff-specific attributes.
	 * * @param userID The unique identification number for the staff member (aka staffID).
	 * @param name The full name of the staff member.
	 * @param role The specific role of the staff member.
	 * @param department The department the staff member belongs to.
	 * @param email The email address of the staff member.
	 * @param password The hashed password of the staff member.
	 */
	public CareerCenterStaff(String userID, String name, String role, String department, String email, String password) {
		super(userID, name, email, password);
		this.role = role;
		this.department = department;
	}

	/**
	 * Allows the staff member to view and either approve or reject pending 
	 * registration requests from {@code CompanyRepresentative} users.
	 * The decision updates the representative's status in the system.
	 * @param sc The {@code Scanner} object used for user input.
	 */
	public void approveRejectCompRep(Scanner sc) {
		System.out.print("\033[H\033[2J");
		ArrayList<CompanyRepresentative> companyReps = CompanyRepresentativeManager.getCompanyReps(
				null,
				null,
				null,
				null,
				null,
				CompanyRepresentativeStatus.PENDING
				);
		
		if (companyReps.isEmpty()) {
			System.out.println("Sorry, there are no pending company represenatives at the moment.");
			return;
		}
		System.out.println("==== Approve/Reject Company Representative ====\n");
		System.out.println("Company Representatives's Requests:");
		CompanyRepresentativeView.printTable(companyReps);
//		printCompanyReps(companyReps);
		
		String compRepID = "";
		boolean found = false;
		while (compRepID.isEmpty() || !found) {
			System.out.print("Enter the email of the company rep.: ");
			compRepID = sc.next();
			if (compRepID.isEmpty()) {
				System.out.println("Please enter a company rep's email.");
				continue;
			}
			
			for (CompanyRepresentative companyRep : companyReps) {
				if (companyRep.getUserID().equals(compRepID)) {
					found = true;
					
					
					int status = 0;
					while (status<1 || status>2) {
						System.out.println("Choose a status: ");
						System.out.println("(1) Approve");
						System.out.println("(2) Reject");
						System.out.print("Your choice: ");
						status = sc.nextInt();
						
						if (status==1) {
							companyRep.setStatus(CompanyRepresentativeStatus.APPROVED);
							System.out.print("\033[H\033[2J");
							System.out.println("Successfully Approved Company Representative");
						}
						else if (status==2) {
							companyRep.setStatus(CompanyRepresentativeStatus.REJECTED);
							System.out.print("\033[H\033[2J");
							System.out.println("Successfully Rejected Company Representative");
						}
						else {
							System.out.println("Invalid input. Choose either (1) or (2)");
						}
					}
				}
			}
			if (!found) System.out.println("Sorry, we couldn't find a company representative with the ID \""+compRepID+"\". Please try again.");
		}

	}
	
	/**
	 * Allows the staff member to view and either approve or reject pending 
	 * {@code WithdrawalRequest} submissions from students.
	 * <p>
	 * Approving a request successfully removes the corresponding internship 
	 * application and updates the filled places count for the internship opportunity.
	 * </p>
	 * @param sc The {@code Scanner} object used for user input.
	 */
	public void approveRejectWithdrawalRequest(Scanner sc) {
		System.out.print("\033[H\033[2J");
		ArrayList<WithdrawalRequest> withdrawalReqs = WithdrawalRequestManager.getWithdrawalReqs(null,WithdrawalRequestStatus.PENDING);
		ArrayList<InternshipApplication> iApps = InternshipApplicationManager.getInternshipApps();
		
		if (withdrawalReqs.isEmpty()) {
			System.out.println("Sorry, there are no withdrawal requests to attend at the moment.");
			return;
		}
		
		System.out.println("==== Approve/Reject Withdrawal Request ====\n");
		System.out.println("Withdrawal Requests:");
		WithdrawalRequestView.printTable();
//		printWithdrawalReqs(withdrawalReqs);
		
		String appilcationID = "";
		boolean found = false;
		while (appilcationID.isEmpty() || !found) {
			System.out.print("Enter the ID of the Application ID: ");
			appilcationID = sc.next();
			
			for (WithdrawalRequest withdrawalReq : withdrawalReqs) {
				if (withdrawalReq.getApplicationID().equals(appilcationID) && 
						withdrawalReq.getStatus()==WithdrawalRequestStatus.PENDING) {
					found = true;
					
					int status = 0;
					while (status<1 || status>2) {
						System.out.println("Choose a status: ");
						System.out.println("(1) Approve");
						System.out.println("(2) Reject");
						System.out.print("Your choice: ");
						status = sc.nextInt();
						if (status==1) {
//							withdrawalReqs.remove(withdrawalReq);
							withdrawalReq.setStatus(WithdrawalRequestStatus.SUCCESSFUL);
							for (InternshipApplication iApp : iApps) {
								if (iApp.getApplicationID().equals(appilcationID))
									iApps.remove(iApp);
							}
							InternshipOpportunityManager.updateFilledPlaces(); // update filled status if filled
							System.out.print("\033[H\033[2J");
							System.out.println("Successfully Withdrawn Internship Application");
						}
						else if (status==2) {
							withdrawalReq.setStatus(WithdrawalRequestStatus.UNSUCCESSFUL);
							System.out.print("\033[H\033[2J");
							System.out.println("Successfully Rejected Internship Application");
						}
						else {
							System.out.println("Invalid input. Choose either (1) or (2)");
						}
					}
				}
			}
			if (!found) System.out.println("Sorry, Application ID not found. Please try again.");
		}
	}
	
	
	/**
	 * Allows the staff member to view and either approve or reject pending 
	 * {@code InternshipOpportunity} submissions from Company Representatives.
	 * <p>
	 * Approval makes the internship visible and available for student applications.
	 * </p>
	 * @param sc The {@code Scanner} object used for user input.
	 */
	public void approveRejectInternshipOpp(Scanner sc) {
		ArrayList<InternshipOpportunity> internshipOpportunities =
				InternshipOpportunityManager.getInternshipOpps(
						null,
						null,
						null,
						null,
						null,
						null,
						new InternshipOpportunityStatus[]
						{InternshipOpportunityStatus.PENDING},
						null,
						null,
						null,
						null,
						null
				);
		
		System.out.print("\033[H\033[2J");
		InternshipOpportunityView.printList(internshipOpportunities);
		if (internshipOpportunities.isEmpty()) {
			System.out.println("Sorry, there are no pending internship opportunities at the moment. Please try again later.");
			return;
		}
		
		System.out.println("==== Approve/Reject Internship Opportunities ====\n");
		System.out.println("Internship Opportunities:");
		String internshipID = "";
		boolean found = false;
		while (internshipID.isEmpty() || !found) {
			System.out.print("Enter an Internship ID: ");
			internshipID = sc.next();
			for (InternshipOpportunity internshipOpp : internshipOpportunities) {
				if (internshipOpp.getInternshipID().equals(internshipID)) {
					found = true;
					
					int status = 0;
					while (status<=0 || status>2) {
						System.out.println("Choose a status: ");
						System.out.println("(1) Approve");
						System.out.println("(2) Reject");
						System.out.print("Your choice: ");
						status = sc.nextInt();
						
						if (status==1) {
							System.out.print("\033[H\033[2J");
							internshipOpp.setStatus(InternshipOpportunityStatus.APPROVED);
							System.out.println("Successfully Approved Internship Opportunity");
						}
						else if (status==2) {
							internshipOpp.setStatus(InternshipOpportunityStatus.REJECTED);
							System.out.print("\033[H\033[2J");
							System.out.println("Successfully Rejected Internship Opportunity");
						}
						else {
							System.out.print("\033[H\033[2J");
							System.out.println("Invalid input. Choose either (1) or (2)");
						}
					}
				}
			}
			if (!found) System.out.println("Sorry, Internship ID not found. Please try again.");
		}
	}
	
	/**
	 * Generates and exports a comprehensive report of internship applications based on 
	 * specified filter criteria (Internship ID, Title, Company ID, Status, etc.).
	 * <p>
	 * The report is saved as a CSV file to a predefined path ({@code Constants.EXPORT_REPORTS_PATH}).
	 * </p>
	 * @param sc The {@code Scanner} object used for user input.
	 */
	public void exportApplicationsReport(Scanner sc) {
		System.out.print("\033[H\033[2J");
		if (sc.hasNextLine()) sc.nextLine();
		
		ArrayList<InternshipApplication> applications = 
				InternshipApplicationManager.getInternshipApps();
		
		System.out.println("==== Export Internship Applications Report ====\n");
		// FILTERING
		System.out.println("Set Filters (Press ENTER to skip):");
		
		System.out.print("Enter Internship ID: ");
	    String internshipID = sc.nextLine().trim();
	    internshipID = internshipID.isEmpty() ? null : internshipID;

	    System.out.print("Enter Title: ");
	    String title = sc.nextLine().trim();
	    title = title.isEmpty() ? null : title;

	    System.out.print("Enter Description: ");
	    String description = sc.nextLine().trim();
	    description = description.isEmpty() ? null : description;
	    
	    System.out.println("Company Table:");
	    CompanyView.printTable();
	    System.out.print("Enter Company ID: ");
	    String companyID = sc.nextLine().trim();
	    companyID = companyID.isEmpty() ? null : companyID;

	    System.out.print("Enter Preferred Major: ");
	    String preferredMajor = sc.nextLine().trim();
	    preferredMajor = preferredMajor.isEmpty() ? null : preferredMajor;
	    
		InternshipOpportunityLevel level = null;
		System.out.println("Level of Difficulty: ");
		System.out.println("(1) Basic ");
		System.out.println("(2) Intermediate ");
		System.out.println("(3) Advanced ");
		System.out.print("Your choice: ");
		String input = sc.nextLine().trim();
	    if (!input.isEmpty()) {
	        switch (input) {
	            case "1" -> level = InternshipOpportunityLevel.BASIC;
	            case "2" -> level = InternshipOpportunityLevel.INTERMEDIATE;
	            case "3" -> level = InternshipOpportunityLevel.ADVANCED;
	            default -> System.out.println("Invalid input. Ignored level filter.");
	        }
	    }
	    
		InternshipOpportunityStatus status = null;
		System.out.println("Status: ");
		System.out.println("(1) Approved ");
		System.out.println("(2) Pending ");
		System.out.println("(3) Rejected ");
		System.out.println("(4) Filled ");
		System.out.print("Your choice: ");
		input = sc.nextLine().trim();
	    if (!input.isEmpty()) {
	        switch (input) {
	            case "1" -> status = InternshipOpportunityStatus.APPROVED;
	            case "2" -> status = InternshipOpportunityStatus.PENDING;
	            case "3" -> status = InternshipOpportunityStatus.REJECTED;
	            case "4" -> status = InternshipOpportunityStatus.FILLED;
	            default -> System.out.println("Invalid input. Ignored status filter.");
	        }
	    }
		Boolean visibility = null;
		System.out.println("Visibility (by students): ");
		System.out.println("(1) On ");
		System.out.println("(2) Off");
		System.out.print("Your choice: ");
		input = sc.nextLine().trim();
		if (!input.isEmpty()) {
	        if (input.equals("1")) visibility = true;
	        else if (input.equals("2")) visibility = false;
	        else System.out.println("Invalid input. Ignored visibility filter.");
	    }
		
		System.out.print("Enter Opening Date From (YYYY-MM-DD): ");
		String openingFromInput = sc.nextLine().trim();
		LocalDate openingFrom = openingFromInput.isEmpty() ? null : LocalDate.parse(openingFromInput, DateTimeFormatter.ISO_DATE);

		System.out.print("Enter Opening Date To (YYYY-MM-DD): ");
		String openingToInput = sc.nextLine().trim();
		LocalDate openingTo = openingToInput.isEmpty() ? null : LocalDate.parse(openingToInput, DateTimeFormatter.ISO_DATE);

		System.out.print("Enter Closing Date From (YYYY-MM-DD): ");
		String closingFromInput = sc.nextLine().trim();
		LocalDate closingFrom = closingFromInput.isEmpty() ? null : LocalDate.parse(closingFromInput, DateTimeFormatter.ISO_DATE);

		System.out.print("Enter Closing Date To (YYYY-MM-DD): ");
		String closingToInput = sc.nextLine().trim();
		LocalDate closingTo = closingToInput.isEmpty() ? null : LocalDate.parse(closingToInput, DateTimeFormatter.ISO_DATE);

		
		ArrayList<InternshipOpportunity> companyRepList = 
				InternshipOpportunityManager.getInternshipOpps(
				internshipID,
				title,
				description,
				companyID,
				preferredMajor,
				level,
				status,
				visibility,
				openingFrom,
				openingTo,
				closingFrom,
				closingTo
		);
		
		Set<String> managedInternshipIDs = companyRepList
				.stream()
				.map(InternshipOpportunity::getInternshipID)
				.collect(Collectors.toSet());
		
		ArrayList<InternshipApplication> filteredApplications = applications.stream().filter(obj->
					managedInternshipIDs.contains(obj.getInternshipID())
				)
				.collect(Collectors.toCollection(ArrayList::new));
		
		if (filteredApplications.isEmpty()) {
			System.out.println("No matching internship applications found. Export aborted.");
			return;
		}
		
		// include headers first
		StringBuilder contents = new StringBuilder("ApplicationID,StudentID,InternshipID,Status,Internship_Title,Internship_Description,Internship_CompanyName,Internship_PreferredMajors,Internship_Level,Internship_OpeningDate,Internship_ClosingDate\n");
		for (InternshipApplication internshipApp : filteredApplications) {
			InternshipOpportunity internshipOpp = InternshipOpportunityManager.getInternshipOppByID(internshipApp.getInternshipID());
		    
			Company company = CompanyManager.getCompanyByID(internshipOpp.getCompanyID());
			String companyName = "";
			if (company == null) companyName = "Company ID Not Found";
			else companyName = company.getCompanyName();
			
			contents.append(internshipApp.getApplicationID()).append(Constants.DELIMITER)
		            .append(internshipApp.getStudentID()).append(Constants.DELIMITER)
		            .append(internshipApp.getInternshipID()).append(Constants.DELIMITER)
		            .append(internshipApp.getStatus()).append(Constants.DELIMITER)
		            .append(internshipOpp.getTitle()).append(Constants.DELIMITER)
		            .append(internshipOpp.getDescription()).append(Constants.DELIMITER)
		            .append(companyName).append(Constants.DELIMITER)
		            .append(internshipOpp.getPreferredMajor()).append(Constants.DELIMITER)
		            .append(internshipOpp.getLevel()).append(Constants.DELIMITER)
		            .append(internshipOpp.getOpeningDate().format(DateTimeFormatter.ISO_DATE)).append(Constants.DELIMITER)
		            .append(internshipOpp.getClosingDate().format(DateTimeFormatter.ISO_DATE)).append(Constants.NEW_LINE);
		}
		
		boolean success = FileIOHandler.writeFileContents(Constants.EXPORTED_INTERNSHIP_APP_FILE,Constants.EXPORT_REPORTS_PATH, contents.toString());
		
		System.out.print("\033[H\033[2J");
		if (success) {
			System.out.println("Report successfully exported to: " + Constants.EXPORT_REPORTS_PATH + Constants.EXPORTED_INTERNSHIP_APP_FILE);
		} else {
			System.out.println("Export operation completed with errors. Please check the console output above.");
		}
	}

	
	/**
	 * Displays the main menu for the Career Center Staff and handles navigation 
	 * to various functionalities based on user choice.
	 * @param sc The {@code Scanner} object used for user input.
	 */
	@Override
	public void displayHome(Scanner sc) {
		// TODO Auto-generated method stub
		System.out.println("Welcome, " + super.getName() + " (" + super.getUserID() + ")");
		
		int choice = 0;
		while (choice != 8) {
			System.out.println("=====================================================");
			System.out.println("Choose an option: ");
			System.out.println("(1) View Available Internships");
			System.out.println("(2) Approve/Reject Company Representative");
			System.out.println("(3) Approve/Reject Internship Opportunity");
			System.out.println("(4) Approve/Reject Withdrawal Request");
			System.out.println("(5) Print/Export Application Reports");
			System.out.println("(6) View Profile");
			System.out.println("(7) Change Password");
			System.out.println("(8) Logout");
			System.out.println("=====================================================");
			System.out.print("Your choice: ");
			
			String input = sc.next();	
			try {
				choice = Integer.parseInt(input.trim()); 
				switch (choice) {
					case 1 -> {
						viewInternshipOpps(sc);
					}
					case 2 -> {
						approveRejectCompRep(sc);
					}
					case 3 -> {
						approveRejectInternshipOpp(sc);
					}
					case 4 -> {
						approveRejectWithdrawalRequest(sc);
					}
					case 5 -> {
						exportApplicationsReport(sc);
					}
					case 6 -> {
						viewProfile(sc);
					}
					case 7 -> {
						changePassword(sc);
					}
					case 8 -> {
						System.out.print("\033[H\033[2J");
						System.out.println("Logged out!");
					}
					default -> {
    					System.out.print("\033[H\033[2J");
	                    System.out.println("Invalid choice. Please enter a valid number (1-8).");
    				}
				}
			} catch (NumberFormatException e) {
            	System.out.print("\033[H\033[2J");
                System.out.println("Invalid choice. Please enter a valid number (1-8).");
                choice = 0;
            }
		}
	}

	/**
	 * Displays the staff member's profile details.
	 * @param sc The {@code Scanner} object used for user input.
	 */
	@Override
	public void viewProfile(Scanner sc) {
		System.out.print("\033[H\033[2J");
		System.out.println("============== STAFF PROFILE =============");
		CareerCenterStaffView.print(this);
		System.out.println("==========================================");
	}
	
	/**
	 * Handles the process for the staff member to change their password.
	 * It ensures password strength and consistency before hashing and updating the record.
	 * @param sc The {@code Scanner} object used for user input.
	 */
	@Override
	public void changePassword(Scanner sc) {
		System.out.print("\033[H\033[2J");
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
		
		String hashedPassword = PasswordManager.hashPassword(newPassword);
		ArrayList<CareerCenterStaff> staffs = CareerCenterStaffManager.getStaff();
		for (CareerCenterStaff staff : staffs) {
			if (staff.getUserID().equals(this.getUserID())) {
				staff.setPassword(hashedPassword);
				super.setPassword(hashedPassword);
				break;
			}
		}

		System.out.println("Your password has been successfully changed!");
	}
	
	/**
	 * Allows the staff member to view and filter all internship opportunities 
	 * in the system, regardless of visibility status (for administrative oversight).
	 * Filter settings are saved for continuity.
	 * @param sc The {@code Scanner} object used for user input.
	 */
	@Override
	public void viewInternshipOpps(Scanner sc) {
		if (super.getInternshipFilterSettings()==null) 
			super.setInternshipFilterSettings(new InternshipFilterSettings());
		InternshipFilterSettings settings = super.getInternshipFilterSettings();
		
		System.out.print("\033[H\033[2J");
		if (sc.hasNextLine())
			sc.nextLine();
		
		System.out.println("==== View All Internships ====");
	    System.out.println();
		System.out.println("Set Filters (Press ENTER to leave blank):");
		
		System.out.print("Enter Internship ID" + 
		((settings.getInternshipID()==null || settings.getInternshipID().isEmpty()) 
				? "" : 
					" (Prev: "+settings.getInternshipID()+")") + ": ");
	    String internshipID = sc.nextLine().trim();
	    internshipID = internshipID.isEmpty() ? null : internshipID;
	    
	    System.out.print("Enter Title" + 
	    ((settings.getTitle()==null || settings.getTitle().isEmpty()) 
	    		? "" : 
	    			" (Prev: "+settings.getTitle()+")") + ": ");
	    String title = sc.nextLine().trim();
	    title = title.isEmpty() ? null : title;

	    System.out.print("Enter Description" + 
	    	    ((settings.getDescription()==null || settings.getDescription().isEmpty()) 
	    	    		? "" : 
	    	    			" (Prev: "+settings.getDescription()+")") + ": ");
	    String description = sc.nextLine().trim();
	    description = description.isEmpty() ? null : description;
	    
	    System.out.println();
	    CompanyView.printTable();
	    System.out.print("Enter Company ID" + 
	    	    ((settings.getCompanyID()==null || settings.getCompanyID().isEmpty()) 
	    	    		? "" : 
	    	    			" (Prev: "+settings.getCompanyID()+")") + ": ");
	    String companyID = sc.nextLine().trim();
	    companyID = companyID.isEmpty() ? null : companyID;

	    System.out.print("Enter Preferred Major" + 
	    	    ((settings.getPreferredMajors()==null || settings.getPreferredMajors().isEmpty()) 
	    	    		? "" : 
	    	    			" (Prev: "+settings.getPreferredMajors()+")") + ": ");
	    String preferredMajor = sc.nextLine().trim();
	    preferredMajor = preferredMajor.isEmpty() ? null : preferredMajor;
	    
		InternshipOpportunityLevel level = null;
		System.out.println("Level of Difficulty: ");
		System.out.println("(1) Basic ");
		System.out.println("(2) Intermediate ");
		System.out.println("(3) Advanced ");
		System.out.print("Your Choice" + 
	    	    ((settings.getLevel()==null) 
	    	    		? "" : 
	    	    			" (Prev: "+settings.getLevel()+")") + ": ");
		String input = sc.nextLine().trim();
	    if (!input.isEmpty()) {
	        switch (input) {
	            case "1" -> level = InternshipOpportunityLevel.BASIC;
	            case "2" -> level = InternshipOpportunityLevel.INTERMEDIATE;
	            case "3" -> level = InternshipOpportunityLevel.ADVANCED;
	            default -> System.out.println("Invalid input. Ignored level filter.");
	        }
	    }
	    
		InternshipOpportunityStatus status = null;
		System.out.println("Status: ");
		System.out.println("(1) Approved ");
		System.out.println("(2) Pending ");
		System.out.println("(3) Rejected ");
		System.out.println("(4) Filled ");
		System.out.print("Your Choice" + 
	    	    ((settings.getStatus()==null) 
	    	    		? "" : 
	    	    			" (Prev: "+settings.getStatus()+")") + ": ");
		input = sc.nextLine().trim();
	    if (!input.isEmpty()) {
	        switch (input) {
	            case "1" -> status = InternshipOpportunityStatus.APPROVED;
	            case "2" -> status = InternshipOpportunityStatus.PENDING;
	            case "3" -> status = InternshipOpportunityStatus.REJECTED;
	            case "4" -> status = InternshipOpportunityStatus.FILLED;
	            default -> System.out.println("Invalid input. Ignored status filter.");
	        }
	    }
		Boolean visibility = null;
		System.out.println("Visibility (by students): ");
		System.out.println("(1) On ");
		System.out.println("(2) Off");
		System.out.print("Your Choice" + 
	    	    ((settings.getVisibility()==null) 
	    	    		? "" : 
	    	    			" (Prev: "+settings.getVisibility()+")") + ": ");
		input = sc.nextLine().trim();
		if (!input.isEmpty()) {
	        if (input.equals("1")) visibility = true;
	        else if (input.equals("2")) visibility = false;
	        else System.out.println("Invalid input. Ignored visibility filter.");
	    }
		
		System.out.print("Enter Opening Date From (YYYY-MM-DD) " + 
	    	    ((settings.getOpeningDateFrom()==null) 
	    	    		? "" : 
	    	    			" (Prev: "+settings.getOpeningDateFrom().format(DateTimeFormatter.ISO_DATE)+")") + ": ");
		String openingFromInput = sc.nextLine().trim();
		LocalDate openingFrom = null;
		if (!openingFromInput.isEmpty()) {
			try {
				openingFrom = LocalDate.parse(openingFromInput, DateTimeFormatter.ISO_DATE);
			} catch (DateTimeParseException e) {
				System.out.println("Invalid date format. Please use YYYY-MM-DD. Opening Date From filter ignored.");
			}
		}
		
		System.out.print("Enter Opening Date To (YYYY-MM-DD) " + 
	    	    ((settings.getOpeningDateTo()==null) 
	    	    		? "" : 
	    	    			" (Prev: "+settings.getOpeningDateTo().format(DateTimeFormatter.ISO_DATE)+")") + ": ");
		String openingToInput = sc.nextLine().trim();
		LocalDate openingTo = null;
		if (!openingToInput.isEmpty()) {
			try {
				openingTo = LocalDate.parse(openingToInput, DateTimeFormatter.ISO_DATE);
			} catch (DateTimeParseException e) {
				System.out.println("Invalid date format. Please use YYYY-MM-DD. Opening Date To filter ignored.");
			}
		}
		
		System.out.print("Enter Closing Date From (YYYY-MM-DD) " + 
	    	    ((settings.getClosingDateFrom()==null) 
	    	    		? "" : 
	    	    			" (Prev: "+settings.getClosingDateFrom().format(DateTimeFormatter.ISO_DATE)+")") + ": ");
		String closingFromInput = sc.nextLine().trim();
		LocalDate closingFrom = null;
		if (!closingFromInput.isEmpty()) {
			try {
				closingFrom = LocalDate.parse(closingFromInput, DateTimeFormatter.ISO_DATE);
			} catch (DateTimeParseException e) {
				System.out.println("Invalid date format. Please use YYYY-MM-DD. Closing Date From filter ignored.");
			}
		}
		
		System.out.print("Enter Closing Date To (YYYY-MM-DD) " + 
	    	    ((settings.getClosingDateTo()==null) 
	    	    		? "" : 
	    	    			" (Prev: "+settings.getClosingDateTo().format(DateTimeFormatter.ISO_DATE)+")") + ": ");
		String closingToInput = sc.nextLine().trim();
		LocalDate closingTo = null;
		if (!closingToInput.isEmpty()) {
			try {
				closingTo = LocalDate.parse(closingToInput, DateTimeFormatter.ISO_DATE);
			} catch (DateTimeParseException e) {
				System.out.println("Invalid date format. Please use YYYY-MM-DD. Closing Date To filter ignored.");
			}
		}
		
		ArrayList<InternshipOpportunity> internshipList = 
				InternshipOpportunityManager.getInternshipOpps(
				internshipID,
				title,
				description,
				companyID,
				preferredMajor,
				level,
				status,
				visibility,
				openingFrom,
				openingTo,
				closingFrom,
				closingTo
		);
		
		// SAVE FILTER SETTINGS
		settings.setInternshipID(internshipID);
		settings.setTitle(title);
		settings.setDescription(description);
		settings.setCompanyID(companyID);
		settings.setPreferredMajors(preferredMajor);
		settings.setLevel(level);
		settings.setStatus(status);
		settings.setVisibility(visibility);
		settings.setOpeningDateFrom(openingFrom);
		settings.setOpeningDateTo(openingTo);
		settings.setClosingDateFrom(closingFrom);
		settings.setClosingDateTo(closingTo);
		
		super.setInternshipFilterSettings(settings);
		
		System.out.print("\033[H\033[2J");
		if (internshipList != null && !internshipList.isEmpty()) {
			System.out.println("===== LIST OF INTERNSHIPS =====");
			InternshipOpportunityView.printList(internshipList);
		} else {
			System.out.println("Sorry, no internship opportunities are available at the moment. Please check back later.");
		}
	}
	
	// GETTERS & SETTERS

	/**
	 * Gets the specific role of the staff member.
	 * @return The staff member's role (e.g., "Career Center Staff").
	 */
	public String getRole() {
		return role;
	}
	
	/**
	 * Sets the specific role of the staff member.
	 * @param role The new role to set.
	 */
	public void setRole(String role) {
		this.role = role;
	}
	
	/**
	 * Gets the department the staff member belongs to.
	 * @return The staff member's department.
	 */
	public String getDepartment() {
		return department;
	}
	
	/**
	 * Sets the department the staff member belongs to.
	 * @param department The new department to set.
	 */
	public void setDepartment(String department) {
		this.department = department;
	}
}
