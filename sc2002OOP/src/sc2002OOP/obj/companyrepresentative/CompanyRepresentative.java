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
import sc2002OOP.obj.User;

//IMPORTS 
import sc2002OOP.obj.internshipapplicaton.*;
import sc2002OOP.obj.internshipopportunity.*;
import sc2002OOP.obj.student.Student;
import sc2002OOP.obj.student.StudentManager;

public class CompanyRepresentative extends User implements ICompanyRepresentative, Serializable  {
	private static final long serialVersionUID = 1849883218046981980L;
	private String companyName, department, position, email;
	private CompanyRepresentativeStatus status;

	public CompanyRepresentative() {}
	
	public CompanyRepresentative(String userID, String name, String companyName, String department, String position, String email, CompanyRepresentativeStatus status, String password) {
		super(userID,name,email,password);
		this.companyName = companyName;
		this.department = department;
		this.position = position;
		this.email = email;
		this.status = status;
	}
	
	public void print() {
		System.out.println("Company Rep ID: "+getUserID());
		System.out.println("Name:           "+getName());
		System.out.println("Email:          "+getEmail());
		System.out.println("Company Name:   "+companyName);
		System.out.println("Department:     "+department);
		System.out.println("Position:       "+position);
		System.out.println("Email:          "+email);
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
		
		// companyName
		String companyName = "";
		while (companyName.isEmpty()) {
			System.out.print("Enter company name:");
			companyName = sc.nextLine();
			if (companyName.isEmpty()) {
				System.out.println("Company Name not entered.");
			}
		}
		
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
		
		String companyRepID = "";
		ArrayList<CompanyRepresentative> companyReps = CompanyRepresentativeManager.getCompanyReps();
		boolean found = false;
		ArrayList<CompanyRepresentative> newCompanyReps = new ArrayList<>();
		while (companyRepID.isEmpty() || !companyRepID.equals("z")) {
			System.out.print("Enter Company Rep ID (type 'z' to stop adding): ");
			companyRepID = sc.nextLine();
			for (CompanyRepresentative cRep : companyReps) {
				if (cRep.getUserID().equals(companyRepID)) {
					found = true;
					newCompanyReps.add(cRep);
				}
			}
			if (companyRepID.isEmpty())
				System.out.println("Company Rep ID not filled.");
			else if (!found)
				System.out.println("Company Rep ID not found. Please try again.");
			
		}
		
		
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
		while (in<=1 || in>2) {
			System.out.println("Choose Visibility (for Students): ");
			System.out.println("(1) on");
			System.out.println("(2) off");
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
				companyName,
				preferredMajor,
				newCompanyReps,
				numSlots,
				iOppLevel,
				InternshipOpportunityStatus.PENDING,
				openingDate,
				closingDate,
				isVisible
			)
		);
	}
	
	public void printInternshipOpps() {
		System.out.print("\033[H\033[2J");
		ArrayList<InternshipOpportunity> internshipOpps = InternshipOpportunityManager
				.getInternshipOpps(
						null,
						null,
						null,
						null,
						null,
						Set.of(this.getUserID()),
						null,
						null,
						null
					);
		
		System.out.println("==== Internship Opportunities List ====");
		for (InternshipOpportunity internshipOpp : internshipOpps) {
			internshipOpp.print();
			System.out.println("-".repeat(40));
		}
	}
	
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
						null,
						null,
						null,
						null,
						null,
						Set.of(this.getUserID()),
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
						
						if (choice==1) iApp.setStatus(InternshipApplicationStatus.SUCCESSFUL);
						else if (choice==2) iApp.setStatus(InternshipApplicationStatus.UNSUCCESSFUL);
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
						null,
						null,
						null,
						null,
						null,
						Set.of(this.getUserID()),
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
					System.out.println("Visibility of Internship  ID " + 
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
	public void exportApplicationsReport() {
		System.out.print("\033[H\033[2J");
		ArrayList<InternshipApplication> applications = 
				InternshipApplicationManager.getInternshipApps();
		
		
		ArrayList<InternshipOpportunity> companyRepList = 
				InternshipOpportunityManager.getInternshipOpps(
				"",
				"",
				"",
				"",
				"",
				Set.of(this.getUserID()),
				null,
				null,
				null
		);
		
		Set<String> managedInternshipIDs = companyRepList
				.stream()
				.map(InternshipOpportunity::getInternshipID)
				.collect(Collectors.toSet());
		
		ArrayList<InternshipApplication> filteredApplications = applications.stream().filter(obj->
					managedInternshipIDs.contains(obj.getInternshipID())
				)
				.collect(Collectors.toCollection(ArrayList::new));
		
		
		// include headers first
		String contents = "ApplicationID,StudentID,InternshipID,Status\n";
		for (InternshipApplication internshipApp : filteredApplications) {
			contents += internshipApp.getApplicationID() + Constants.DELIMITER;
			contents += internshipApp.getStudentID() + Constants.DELIMITER;
			contents += internshipApp.getInternshipID() + Constants.DELIMITER;
			contents += internshipApp.getStatus() + Constants.NEW_LINE;
		}
		FileIOHandler.writeFileContents(Constants.EXPORTED_INTERNSHIP_APP_FILE, Constants.EXPORT_REPORTS_PATH, contents);
		
		System.out.println("Exported to "+Constants.EXPORTED_INTERNSHIP_APP_FILE);
	}
	
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
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
			System.out.println("1. View All Internships");
			System.out.println("2. Approve/Reject Applicants");
			System.out.println("3. Create Internship Opportunity");
			System.out.println("4. Toggle Visibility of Internship Oppurtunity");
			System.out.println("5. Print/Export Applications Report");
			System.out.println("6. View Profile");
			System.out.println("7. Change Password");
			System.out.println("8. Log Out");
			System.out.println("=====================================================");
			System.out.print("Select a choice: ");
			choice = sc.nextInt();
			switch (choice) {
				case 1 -> {
					printInternshipOpps();
				}
				case 2 -> {
					approveRejectApplicant(sc);
				}
				case 3 -> {
					createInternship(sc);
				}
				case 4 -> {
					toggleInternshipOpportunity(sc);
				}
				case 5 -> {
					exportApplicationsReport();
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
}
