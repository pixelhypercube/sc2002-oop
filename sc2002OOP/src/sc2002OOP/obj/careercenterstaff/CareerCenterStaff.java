package sc2002OOP.obj.careercenterstaff;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

import sc2002OOP.main.Constants;
import sc2002OOP.main.FileIOHandler;
import sc2002OOP.main.PasswordManager;
import sc2002OOP.obj.InternshipFilterSettings;
import sc2002OOP.obj.User;
import sc2002OOP.obj.company.CompanyManager;
// IMPORTS 
import sc2002OOP.obj.companyrepresentative.*;
import sc2002OOP.obj.internshipapplicaton.InternshipApplication;
import sc2002OOP.obj.internshipapplicaton.InternshipApplicationManager;
import sc2002OOP.obj.internshipopportunity.*;
import sc2002OOP.obj.withdrawalrequest.*;

public class CareerCenterStaff extends User implements ICareerCenterStaff, Serializable {
	private static final long serialVersionUID = 7112025133049517797L;
	private String role, department;
	
	public CareerCenterStaff() {};
	public CareerCenterStaff(String userID, String name, String role, String department, String email, String password) {
		super(userID, name, email, password);
		this.role = role;
		this.department = department;
	}
	
	public void print() {
		System.out.println("Staff ID:      "+getUserID());
		System.out.println("Name:          "+getName());
		System.out.println("Email:         "+getEmail());
		System.out.println("Role:          "+role);
		System.out.println("Department:    "+department);
	}
	
	public void printCompanyReps(ArrayList<CompanyRepresentative> companyReps) {
		System.out.println("===== Company Representatives List =====");
		for (CompanyRepresentative companyRep : companyReps) {
			companyRep.print();
			System.out.println("-".repeat(40));
		}
	}
	
	public void printInternshipOpps(ArrayList<InternshipOpportunity> internshipOpps) {
		System.out.println("===== Internship Opportunities List =====");
		for (InternshipOpportunity internshipOpp : internshipOpps) {
			internshipOpp.print();
			System.out.println("-".repeat(40));
		}
	}
	
	public void printWithdrawalReqs(ArrayList<WithdrawalRequest> withdrawalReqs) {
		System.out.println("===== Withdrawal Request List =====");
		for (WithdrawalRequest withdrawalReq : withdrawalReqs) {
			withdrawalReq.print();
			System.out.println("-".repeat(40));
		}
	}
	
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
		printCompanyReps(companyReps);
		
		String compRepID = "";
		boolean found = false;
		while (compRepID.isEmpty() || !found) {
			System.out.print("Enter the ID of the company rep.: ");
			compRepID = sc.next();
			if (compRepID.isEmpty()) {
				System.out.println("Please enter a company rep.");
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
							System.out.println("Successfully Approved Company Representative");
						}
						else if (status==2) {
							companyRep.setStatus(CompanyRepresentativeStatus.REJECTED);
							System.out.println("Successfully Rejected Company Representative");
						}
						else {
							System.out.println("Invalid input. Choose either 1 or (2)");
						}
					}
				}
			}
			if (!found) System.out.println("Sorry, we couldn't find a company representative with the ID \""+compRepID+"\". Please try again.");
		}

	}
	
	public void approveRejectWithdrawalRequest(Scanner sc) {
		System.out.print("\033[H\033[2J");
		ArrayList<WithdrawalRequest> withdrawalReqs = WithdrawalRequestManager.getWithdrawalReqs(null,WithdrawalRequestStatus.PENDING);
		ArrayList<InternshipApplication> iApps = InternshipApplicationManager.getInternshipApps();
		
		if (withdrawalReqs.isEmpty()) {
			System.out.println("Sorry, there are no withdrawal requests to attend at the moment.");
			return;
		}
		
		printWithdrawalReqs(withdrawalReqs);
		
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
							System.out.println("Successfully Withdrawn Internship Application");
						}
						else if (status==2) {
							withdrawalReq.setStatus(WithdrawalRequestStatus.UNSUCCESSFUL);
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
	
	public void approveRejectInternshipOpp(Scanner sc) {
		System.out.print("\033[H\033[2J");
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
		
		printInternshipOpps(internshipOpportunities);
		if (internshipOpportunities.isEmpty()) {
			System.out.println("Sorry, there are no pending internship opportunities at the moment. Please try again later.");
			return;
		}
		
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
							internshipOpp.setStatus(InternshipOpportunityStatus.APPROVED);
							System.out.println("Successfully Approved Internship Opportunity");
						}
						else if (status==2) {
							internshipOpp.setStatus(InternshipOpportunityStatus.REJECTED);
							System.out.println("Successfully Rejected Internship Opportunity");
						}
						else {
							System.out.println("Invalid input. Choose either (1) or (2)");
						}
					}
				}
			}
			if (!found) System.out.println("Sorry, Internship ID not found. Please try again.");
		}
	}
	
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}

	@Override
	public void displayHome(Scanner sc) {
		// TODO Auto-generated method stub
		System.out.println("Welcome, " + super.getName() + " (" + super.getUserID() + ")");
		
		int choice = 0;
		while (choice != 6) {
			System.out.println("=====================================================");
			System.out.println("Choose an option: ");
			System.out.println("(1) Approve/Reject Company Representative");
			System.out.println("(2) Approve/Reject Internship Opportunity");
			System.out.println("(3) Approve/Reject Withdrawal Request");
			System.out.println("(4) View Profile");
			System.out.println("(5) Change Password");
			System.out.println("(6) Logout");
			System.out.println("=====================================================");
			System.out.print("Your choice: ");
			choice = sc.nextInt();
			switch (choice) {
				case 1 -> {
					approveRejectCompRep(sc);
				}
				case 2 -> {
					approveRejectInternshipOpp(sc);
				}
				case 3 -> {
					approveRejectWithdrawalRequest(sc);
				}
				case 4 -> {
					viewProfile(sc);
				}
				case 5 -> {
					changePassword(sc);
				}
				case 6 -> {
					System.out.print("\033[H\033[2J");
					System.out.println("Logged out!");
				}
			}
		}
	}

	@Override
	public void viewProfile(Scanner sc) {
		System.out.print("\033[H\033[2J");
		System.out.println("============== STAFF PROFILE =============");
		print();
		System.out.println("==========================================");
	}
//	@Override
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
	    CompanyManager.printAllCompanies();
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
