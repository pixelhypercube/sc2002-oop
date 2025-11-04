package sc2002OOP.obj.companyrepresentative;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

import sc2002OOP.main.*;
import sc2002OOP.obj.InternshipFilterSettings;
import sc2002OOP.obj.User;
import sc2002OOP.obj.company.CompanyManager;
//IMPORTS 
import sc2002OOP.obj.internshipapplicaton.*;
import sc2002OOP.obj.internshipopportunity.*;
import sc2002OOP.obj.student.Student;
import sc2002OOP.obj.student.StudentManager;


/**
 * <h1>Company Representative (User Role)</h1>
 * <p>
 * This class represents a user with the <b>Company Representative</b> role in the IPMS. 
 * It extends the <code>User</code> base class and implements the <code>ICompanyRepresentative</code> 
 * interface, giving it the specific privileges needed to manage internships for their associated company.
 * </p>
 * @apiNote This specialized user class is responsible for the <b>full lifecycle management</b> of internship opportunities, 
 * including creation (<code>createInternship</code>), visibility toggling, applicant approval/rejection (<code>approveRejectApplicant</code>), 
 * and exporting application reports. It is uniquely linked to one company via <code>companyID</code> 
 * and its access is controlled by its <code>status</code> (e.g., PENDING, APPROVED).
 * @author Kee Kai Wen
 * @author Kelvin Tay Wei Jie
 * @author Koay Jun Zhi
 * @author Lim Jia Wei Jerald
 * @author Teo Kai Jie
 * @version 1.0
 * @see sc2002OOP.obj.User
 * @see sc2002OOP.obj.companyrepresentative.ICompanyRepresentative
 * @see sc2002OOP.obj.company.Company
 */
public class CompanyRepresentative extends User implements ICompanyRepresentative, Serializable  {
	private static final long serialVersionUID = 1849883218046981980L;
	private String companyID, department, position, email;
	private CompanyRepresentativeStatus status;

	public CompanyRepresentative() {}
	
	public CompanyRepresentative(String userID, String name, String companyID, String department, String position, CompanyRepresentativeStatus status, String password) {
		super(userID,name,password);
		this.companyID = companyID;
		this.department = department;
		this.position = position;
		this.status = status;
	}
	
	public void print() {
		System.out.println("Email:          "+getUserID());
		System.out.println("Name:           "+getName());
		System.out.println("Company Name:   "+CompanyManager.getCompanyByID(companyID).getCompanyName());
		System.out.println("Department:     "+department);
		System.out.println("Position:       "+position);
		System.out.println("Status:         "+status);
	}
	
	public void createInternship(Scanner sc) {
		System.out.print("\033[H\033[2J");
		if (sc.hasNextLine()) {
	        sc.nextLine(); 
	    }
		
		// title
		String title = "";
		while (title.isEmpty()) {
			System.out.print("Enter title:");
			title = sc.nextLine();
			if (title.isEmpty()) {
				System.out.println("Title not entered.");
			}
		}
		
		// description
		String description = "";
		while (description.isEmpty()) {
			System.out.print("Enter description:");
			description = sc.nextLine();
			if (description.isEmpty()) {
				System.out.println("Description not entered.");
			}
		}
		
//		// companyID
//		String companyID = "";
//		while (companyID.isEmpty() || CompanyManager.getCompanyByID(companyID)==null) {
//			CompanyManager.printAllCompanies();
//			System.out.print("Enter company ID:");
//			companyID = sc.nextLine();
//			if (companyID.isEmpty()) {
//				System.out.println("Company ID not entered. Please try again.");
//			} else if (CompanyManager.getCompanyByID(companyID)==null) {
//				System.out.println("Company ID does not exist. Please try again.");
//			}
//		}
		
		int level = 0;
		InternshipOpportunityLevel iOppLevel = null;
		while (level<=0 || level>3) {
			System.out.println("Choose a level:");
			System.out.println("(1) Basic");
			System.out.println("(2) Intermediate");
			System.out.println("(3) Advanced");
			System.out.print("Your choice: ");
			level = sc.nextInt();
			if (level<=0 || level>3) {
				System.out.println("Invalid input. Enter either 1, 2 or 3.");
			} else {
				switch (level) {
					case 1 -> iOppLevel = InternshipOpportunityLevel.BASIC;
					case 2 -> iOppLevel = InternshipOpportunityLevel.INTERMEDIATE;
					case 3 -> iOppLevel = InternshipOpportunityLevel.ADVANCED;
				}
			}
		}
		
		if (sc.hasNextLine()) {
	        sc.nextLine(); 
	    }
		
		// preferred majors
		String preferredMajor = "";
		while (preferredMajor.isEmpty()) {
			System.out.print("Enter preferred major:");
			preferredMajor = sc.nextLine();
			if (preferredMajor.isEmpty()) {
				System.out.println("Preferred major not entered.");
			}
		}
//		
//		String companyRepID = "";
//		// FILTERED
//		ArrayList<CompanyRepresentative> companyReps = CompanyRepresentativeManager.getCompanyReps(null, companyID, null,null,null,null);
//		boolean found = false;
//		ArrayList<CompanyRepresentative> newCompanyReps = new ArrayList<>();
//		while (companyRepID.isEmpty() || !companyRepID.equals("z")) {
//			System.out.print("Enter Company Rep Email (type 'z' to stop adding): ");
//			companyRepID = sc.nextLine();
//			for (CompanyRepresentative cRep : companyReps) {
//				if (cRep.getUserID().equals(companyRepID)) {
//					if (!cRep.getCompanyID().equals(companyID)) {
//						System.out.println("Sorry, company reps must be from the same company. Please try again");
//						break;
//					}
//					found = true;
//					newCompanyReps.add(cRep);
//				}
//			}
//			if (companyRepID.isEmpty())
//				System.out.println("Company Rep Email not filled. Please try again");
//			else if (!found)
//				System.out.println("Company Rep Email not found. Please try again.");
//			
//		}
//		
		
		// number of slots
		int numSlots = 0;
		while (numSlots<=0 || numSlots>10) {
			System.out.print("Enter number of slots (1-10): ");
			numSlots = sc.nextInt();
			
			if (numSlots<=0 || numSlots>10) {
				System.out.println("Please enter within the range 1-10.");
				continue;
			}
		}
		
		// opening date
		LocalDate openingDate = LocalDate.now();
		String openingDateStr = "";
		String dateRegex = "\\d{4}-\\d{2}-\\d{2}";
		while (openingDateStr.isEmpty()) {
			System.out.print("Enter Opening Date using this format (YYYY-MM-DD): ");
			openingDateStr = sc.nextLine();
			if (openingDateStr.matches(dateRegex)) {
				openingDate = LocalDate.parse(openingDateStr);
			}
		}
		
		// closing date
		LocalDate closingDate = LocalDate.now();
		String closingDateStr = "";
		while (closingDateStr.isEmpty() || closingDate.isBefore(openingDate)) {
			System.out.print("Enter Closing Date using this format (YYYY-MM-DD): ");
			closingDateStr = sc.nextLine();
			if (closingDateStr.matches(dateRegex)) {
				closingDate = LocalDate.parse(closingDateStr);
				if (closingDate.isBefore(openingDate)) {
					System.out.println("Closing Date should be after opening date ("+openingDate.format(DateTimeFormatter.ISO_DATE)+")");
				}
			}
		}
		
		
		boolean isVisible = false;
		int in = 0;
		while (in<=0 || in>2) {
			System.out.println("Choose Visibility (for Students): ");
			System.out.println("(1) On");
			System.out.println("(2) Off");
			System.out.print("Your Choice: ");
			in = sc.nextInt();
			
			if (in==1) isVisible = true;
			else if (in==2) break; // isVisible already false
			else
				System.out.println("Invalid input. Please enter 0 or 1.");
		}
		
		ArrayList<InternshipOpportunity> iOpps = InternshipOpportunityManager.getInternshipOpps();
		iOpps.add(
			new InternshipOpportunity(
				"I"+InternshipOpportunityManager.getNextIDAndIncrement(),
				title,
				description,
				companyID,
				preferredMajor,
				numSlots,
				iOppLevel,
				InternshipOpportunityStatus.PENDING,
				openingDate,
				closingDate,
				isVisible
			)
		);
		System.out.println("Successfully created internship opportunity! Please wait for one of our career center staff to approve!");
	}
	
//	public void printInternshipOpps(Scanner sc) {
//		System.out.print("\033[H\033[2J");
//		ArrayList<InternshipOpportunity> internshipOpps = InternshipOpportunityManager
//				.getInternshipOpps(
//						"",
//						"",
//						"",
//						companyID,
//						"",
//						(InternshipOpportunityLevel)null,
//						(InternshipOpportunityStatus)null,
//						null,
//						null,
//						null,
//						null,
//						null
//					);
//		
//		System.out.println("==== Internship Opportunities List ====");
//		for (InternshipOpportunity internshipOpp : internshipOpps) {
//			internshipOpp.print();
//			System.out.println("-".repeat(40));
//		}
//	}
	
	public void printInternshipApps(ArrayList<InternshipApplication> iApps) {
		System.out.println("==== Internship Applications List ====");
		for (InternshipApplication iApp : iApps) {
			iApp.print();
			System.out.println("-".repeat(40));
		}
	}
	
	public void approveRejectApplicant(Scanner sc) {
		System.out.print("\033[H\033[2J");
		String applicationID = "";
		
		Set<String> permittedIDs = InternshipOpportunityManager
				.getInternshipOpps(
						"",
						"",
						"",
						companyID,
						"",
						(InternshipOpportunityLevel)null,
						(InternshipOpportunityStatus)null,
						null,
						null,
						null,
						null,
						null
					).stream()
				.map(InternshipOpportunity::getInternshipID)
				.collect(Collectors.toSet());
		
		ArrayList<InternshipApplication> allIApps = 
				(ArrayList<InternshipApplication>) InternshipApplicationManager
		.getInternshipApps()
		.stream()
		.filter(app->permittedIDs.contains(app.getInternshipID()))
		.filter(app -> app.getStatus()==InternshipApplicationStatus.PENDING)
		.collect(Collectors.toList());
		
		printInternshipApps(allIApps);
		
		System.out.println("==== Approve/Reject Applicant ====");
		
		while (applicationID.isEmpty()) {
			System.out.print("Enter an Application ID:");
			applicationID = sc.next();
			
			for (InternshipApplication iApp : allIApps) {
				if (iApp.getApplicationID().equals(applicationID)) {
					int choice = 0;
					while (choice<=0 || choice>2) {
						System.out.println("Select a choice:");
						System.out.println("(1) Approve");
						System.out.println("(2) Reject");
						System.out.print("Your choice: ");
						choice = sc.nextInt();
						
						if (choice==1) {
							iApp.setStatus(InternshipApplicationStatus.SUCCESSFUL);
							System.out.println("Successfully approved Application ID " + applicationID);
						}
						else if (choice==2) {
							iApp.setStatus(InternshipApplicationStatus.UNSUCCESSFUL);
							System.out.println("Successfully rejected Application ID " + applicationID);
						}
						else System.out.println("Please select a choice of either 1 or 2.");
					}
				}
			}
		}
	}
	
	public void toggleInternshipOpportunity(Scanner sc) {
		System.out.print("\033[H\033[2J");
		ArrayList<InternshipOpportunity> internshipOpps = InternshipOpportunityManager
				.getInternshipOpps(
						"",
						"",
						"",
						companyID,
						"",
						(InternshipOpportunityLevel)null,
						(InternshipOpportunityStatus)null,
						null,
						null,
						null,
						null,
						null
					);
		
		String internshipID = "";
		boolean found = false;
		while (internshipID.trim().isEmpty() || !found) {
			System.out.println("==== Internship Opportunities List ====");
			for (InternshipOpportunity internshipOpp : internshipOpps) {
				System.out.println("Internship ID: "+internshipOpp.getInternshipID());
				System.out.println("Visible:       "+(internshipOpp.isVisibility() ? "On" : "Off"));
				System.out.println("-".repeat(40));
			}
			System.out.print("Enter Internship ID: ");
			internshipID = sc.next();
			
			for (InternshipOpportunity iOpp : internshipOpps) {
				if (iOpp.getInternshipID().equals(internshipID)) {
					found = true;
					iOpp.setVisibility(!iOpp.isVisibility());
					System.out.println("Visibility of Internship ID " + 
					iOpp.getInternshipID() + 
					" has been set to " + 
					(iOpp.isVisibility() ? "On" : "Off"));
				}
			}
			if (internshipID.trim().isEmpty())
				System.out.println("Please fill in an Internship ID.");
			else if (!found)
				System.out.println("Sorry, Internship ID not found. Please try again.");
		}
	}
	public void exportApplicationsReport(Scanner sc) {
		System.out.print("\033[H\033[2J");
		if (sc.hasNextLine()) sc.nextLine();
		
		ArrayList<InternshipApplication> applications = 
				InternshipApplicationManager.getInternshipApps();
		
		System.out.println("==== Export Internship Applications Report ====");
		System.out.println();
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
		StringBuilder contents = new StringBuilder("ApplicationID,StudentID,InternshipID,Status\n");
		for (InternshipApplication internshipApp : filteredApplications) {
		    contents.append(internshipApp.getApplicationID()).append(Constants.DELIMITER)
		            .append(internshipApp.getStudentID()).append(Constants.DELIMITER)
		            .append(internshipApp.getInternshipID()).append(Constants.DELIMITER)
		            .append(internshipApp.getStatus()).append(Constants.NEW_LINE);
		}
		FileIOHandler.writeFileContents(Constants.EXPORTED_INTERNSHIP_APP_FILE,Constants.EXPORT_REPORTS_PATH, contents.toString());
		System.out.println("Report successfully exported to: " + Constants.EXPORT_REPORTS_PATH + Constants.EXPORTED_INTERNSHIP_APP_FILE);

	}
	
	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public CompanyRepresentativeStatus getStatus() {
		return status;
	}

	public void setStatus(CompanyRepresentativeStatus status) {
		this.status = status;
	}

	@Override
	public void displayHome(Scanner sc) {
		// TODO Auto-generated method stub
		System.out.println("Welcome, " + super.getName() + " (" + super.getUserID() + ")");
		
		int choice = 0;
		while (choice != 8) {
			System.out.println("=====================================================");
			System.out.println("Choose an option: ");
//			System.out.println("1. View All Internships");
			System.out.println("1. Approve/Reject Applicants");
			System.out.println("2. Create Internship Opportunity");
			System.out.println("3. Toggle Visibility of Internship Oppurtunity");
			System.out.println("4. Print/Export Applications Report");
			System.out.println("5. View Profile");
			System.out.println("6. Change Password");
			System.out.println("7. Log Out");
			System.out.println("=====================================================");
			System.out.print("Select a choice: ");
			choice = sc.nextInt();
			switch (choice) {
//				case 1 -> {
//					printInternshipOpps();
//				}
				case 1 -> {
					approveRejectApplicant(sc);
				}
				case 2 -> {
					createInternship(sc);
				}
				case 3 -> {
					toggleInternshipOpportunity(sc);
				}
				case 4 -> {
					exportApplicationsReport(sc);
				}
				case 5 -> {
					viewProfile(sc);
				}
				case 6 -> {
					changePassword(sc);
				}
				case 7 -> {
					System.out.print("\033[H\033[2J");
					System.out.println("Logged out!");
					break;
				}
			}
		}
	}

	@Override
	public void viewProfile(Scanner sc) {
		System.out.print("\033[H\033[2J");
		System.out.println("=========== COMPANY REP PROFILE ==========");
		print();
		System.out.println("==========================================");
	}

	@Override
	public void changePassword(Scanner sc) {
		System.out.print("\033[H\033[2J");
		// TODO Auto-generated method stub
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
		ArrayList<CompanyRepresentative> companyReps = CompanyRepresentativeManager.getCompanyReps();
		for (CompanyRepresentative companyRep : companyReps) {
			if (companyRep.getUserID().equals(getUserID())) {
				companyRep.setPassword(hashedPassword);
				super.setPassword(hashedPassword);
				break;
			}
		}

		System.out.println("Your password has been successfully changed!");
	}

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
	    
//	    System.out.print("Enter Company ID" + 
//	    	    ((settings.getCompanyID()==null || settings.getCompanyID().isEmpty()) 
//	    	    		? "" : 
//	    	    			" (Prev: "+settings.getCompanyID()+")") + ": ");
//	    String companyID = sc.nextLine().trim();
//	    companyID = companyID.isEmpty() ? null : companyID;

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
		LocalDate openingFrom = openingFromInput.isEmpty() ? null : LocalDate.parse(openingFromInput, DateTimeFormatter.ISO_DATE);

		System.out.print("Enter Opening Date To (YYYY-MM-DD) " + 
	    	    ((settings.getOpeningDateTo()==null) 
	    	    		? "" : 
	    	    			" (Prev: "+settings.getOpeningDateTo().format(DateTimeFormatter.ISO_DATE)+")") + ": ");
		String openingToInput = sc.nextLine().trim();
		LocalDate openingTo = openingToInput.isEmpty() ? null : LocalDate.parse(openingToInput, DateTimeFormatter.ISO_DATE);

		System.out.print("Enter Closing Date From (YYYY-MM-DD) " + 
	    	    ((settings.getClosingDateFrom()==null) 
	    	    		? "" : 
	    	    			" (Prev: "+settings.getClosingDateFrom().format(DateTimeFormatter.ISO_DATE)+")") + ": ");
		String closingFromInput = sc.nextLine().trim();
		LocalDate closingFrom = closingFromInput.isEmpty() ? null : LocalDate.parse(closingFromInput, DateTimeFormatter.ISO_DATE);

		System.out.print("Enter Closing Date To (YYYY-MM-DD) " + 
	    	    ((settings.getClosingDateTo()==null) 
	    	    		? "" : 
	    	    			" (Prev: "+settings.getClosingDateTo().format(DateTimeFormatter.ISO_DATE)+")") + ": ");
		String closingToInput = sc.nextLine().trim();
		LocalDate closingTo = closingToInput.isEmpty() ? null : LocalDate.parse(closingToInput, DateTimeFormatter.ISO_DATE);
		
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
//		settings.setCompanyID(companyID);
		settings.setPreferredMajors(preferredMajor);
		settings.setLevel(level);
		settings.setStatus(status);
		settings.setVisibility(visibility);
		settings.setOpeningDateFrom(openingFrom);
		settings.setOpeningDateTo(openingTo);
		settings.setClosingDateFrom(closingFrom);
		settings.setClosingDateTo(closingTo);
						
		super.setInternshipFilterSettings(settings);
		
		if (internshipList != null) {
			System.out.println("===== LIST OF INTERNSHIPS =====");
			for (InternshipOpportunity iOps : internshipList) {
				iOps.printForStudent();
				System.out.println("-".repeat(40));
			}
		} else {
			System.out.println("Sorry, the list of internships are not available at the moment. Please try again later.");
		}
	}
}
