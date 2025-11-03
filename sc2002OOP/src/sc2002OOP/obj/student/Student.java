package sc2002OOP.obj.student;

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
import sc2002OOP.main.Test;
import sc2002OOP.obj.InternshipFilterSettings;
import sc2002OOP.obj.User;
import sc2002OOP.obj.company.CompanyManager;
import sc2002OOP.obj.internshipapplicaton.*;
import sc2002OOP.obj.internshipopportunity.*;
import sc2002OOP.obj.withdrawalrequest.*;

public class Student extends User implements IStudent, Serializable {
	private static final long serialVersionUID = 6023352849918114309L;
	private String major;
	private int year;
	
	public Student() {};
	
	public Student(String userID, String name, String major, int year, String email, String password) {
		super(userID,name,email,password);
		this.major = major;
		this.year = year;
	}
	public void viewInternshipApplications(Scanner sc) {
		System.out.print("\033[H\033[2J");
		ArrayList<InternshipApplication> internshipAppList = InternshipApplicationManager.getInternshipApps(
				"",
				getUserID(),
				"",
				InternshipApplicationStatus.PENDING
			);
		if (internshipAppList != null) {
			System.out.println("====== "+super.getName()+"'s list of Internship Applications ======");
			System.out.println("-".repeat(40));
			for (InternshipApplication internship : InternshipApplicationManager.getInternshipApps("", super.getUserID(), "", null)) {
				internship.print();
				System.out.println("-".repeat(40));
			}
		} else {
			System.out.println("You do not have any internship applications!");
		}
	}
	
	@Override
	public void viewProfile(Scanner sc) {
		System.out.print("\033[H\033[2J");
		System.out.println("========= STUDENT PROFILE =========");
		print();
		System.out.println("===================================");
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
//		Boolean visibility = null;
//		System.out.println("Visibility (by students): ");
//		System.out.println("(1) On ");
//		System.out.println("(2) Off");
//		System.out.print("Your Choice" + 
//	    	    ((settings.getVisibility()==null) 
//	    	    		? "" : 
//	    	    			" (Prev: "+settings.getVisibility()+")") + ": ");
//		input = sc.nextLine().trim();
//		if (!input.isEmpty()) {
//	        if (input.equals("1")) visibility = true;
//	        else if (input.equals("2")) visibility = false;
//	        else System.out.println("Invalid input. Ignored visibility filter.");
//	    }
		
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
				true,
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
//		settings.setVisibility(visibility);
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
	
	public void printAllInternships() {
		System.out.print("\033[H\033[2J");
		ArrayList<InternshipOpportunity> internshipList = 
//				InternshipOpportunity.getAllInternshipOpportunities();
				InternshipOpportunityManager.getInternshipOpps(
						null,
					null,
					null,
					null,
					null,
					(year<=2) 
					? new InternshipOpportunityLevel[]{InternshipOpportunityLevel.BASIC,InternshipOpportunityLevel.INTERMEDIATE}
					: new InternshipOpportunityLevel[]{InternshipOpportunityLevel.BASIC,InternshipOpportunityLevel.INTERMEDIATE,InternshipOpportunityLevel.ADVANCED},
					null,
					true,
					null,
					null,
					null,
					LocalDate.now()
				).stream()
				.filter(opp->opp.getStatus()==InternshipOpportunityStatus.APPROVED)
				.collect(Collectors.toCollection(ArrayList::new));
		
		if (internshipList != null) {
			System.out.println("===== LIST OF INTERNSHIPS =====");
			for (InternshipOpportunity internship : internshipList) {
				internship.printForStudent();
				System.out.println("-".repeat(40));
			}
		} else {
			System.out.println("Sorry, the list of internships are not available at the moment. Please try again later.");
		}
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}
	
	public void acceptInternshipPlacement(Scanner sc) {
		
	}
	
//	 WITHDRAW INTERNSHIP APPLICATION
	public void withdrawApp(Scanner sc) {
		System.out.print("\033[H\033[2J");
		ArrayList<WithdrawalRequest> withdrawalReqList = WithdrawalRequestManager.getWithdrawalReqs();
		Set<String> excludedApplicationIDs = withdrawalReqList.stream()
				.map(WithdrawalRequest::getApplicationID)
				.collect(Collectors.toSet());
		
		ArrayList<InternshipApplication> internshipAppList = InternshipApplicationManager.getInternshipApps(
					"",
					super.getUserID(),
					"",
					null
				).stream()
				.filter(app->app.getStatus()==InternshipApplicationStatus.SUCCESSFUL || app.getStatus()==InternshipApplicationStatus.ACCEPTED)
				.filter(app->!excludedApplicationIDs.contains(app.getApplicationID()))
				.collect(Collectors.toCollection(ArrayList::new));
		
		if (internshipAppList.size()>0) {
			System.out.println("===== SUBMIT WITHDRAWAL REQUEST =====");
			for (InternshipApplication internshipApp : internshipAppList) {
				internshipApp.print();
				System.out.println("-".repeat(40));
			}
			
			String applicationID = "";
			boolean found = false;
			boolean inWithdrawalReqList = false;
			while (applicationID.isEmpty() || !found || inWithdrawalReqList) {
				inWithdrawalReqList = false;
				System.out.print("Enter an Application ID to withdraw: ");
				applicationID = sc.next();
				
				// check if it's inside withdrawal req list
				final String currApplicationID = applicationID;
				if (withdrawalReqList.stream().map(req->req.getApplicationID()).anyMatch(id->id.equals(currApplicationID))) {
					System.out.println("You have already submitted the request for this application! Please try another application.");
					inWithdrawalReqList = true;
					continue;
				}
				
				for (InternshipApplication internshipApp : internshipAppList) {
					if (internshipApp.getApplicationID().equals(applicationID)) {
						found = true;
						
						WithdrawalRequestManager.getWithdrawalReqs().add(new WithdrawalRequest(applicationID));
						break;
					}
				}
				if (!found) System.out.println("Application ID not found. Please try again!");
			}
		} else {
			System.out.println("Sorry, you don't have any pending internship applications at the moment.");
		}
		
		
	}
	
	// APPLY INTERNSHIP
	public void applyInternship(Scanner sc) {
		System.out.print("\033[H\033[2J");
		ArrayList<InternshipOpportunity> internshipList = InternshipOpportunityManager.getInternshipOpps();
		ArrayList<InternshipApplication> internshipApps = InternshipApplicationManager.getInternshipApps();
		
		if (InternshipApplicationManager.getInternshipApps(
				null, 
				super.getUserID(), 
				null, 
				null
			).size()>=3) {
			System.out.println("Sorry, you are not allowed to apply more internships as you have reached the maximum limit of applications (3).");
			return;
		}
		
		printAllInternships();
		
		String internshipID = "";
		boolean found = false;
		while (!found || (internshipID == null || internshipID.isEmpty())) {
			System.out.print("Type the Internship ID to submit your application: ");
			internshipID = sc.next();
			if (internshipID.isEmpty()) continue;
			
			final String currInternshipID = internshipID; // for lambda fn
			
			for (InternshipOpportunity internship : internshipList) {
				if (internshipID.equals(internship.getInternshipID())) {
					if (internship.getStatus()==InternshipOpportunityStatus.FILLED) {
						System.out.println("Sorry, the internship you've tried to apply is full. Please select another Internship ID.");
						break;
					}
					else if (LocalDate.now().isBefore(internship.getClosingDate())) {
						System.out.println("Sorry, the internship you've tried to apply is past its closing date. Please select another Internship ID.");
					}
					else if (internship.getStatus()==InternshipOpportunityStatus.PENDING) {
						System.out.println("Sorry, the internship you've tried to apply is currently awaiting approval from one of our career center staff. Please select another Internship ID.");
						break;
					}
					else if (internshipApps.stream().map(its->its.getInternshipID()).anyMatch(id->id.equals(currInternshipID))) {
						System.out.println("You have already subimitted this Internship ID. Please select another Internship ID.");
						break;
					}
					else if (internship.getLevel()==InternshipOpportunityLevel.ADVANCED && year<=2) {
						System.out.println("Sorry, this internship is not available for you as it's only available for year 3 students and above. Please select another Internship ID.");
						break;
					}
						
					found = true;
					InternshipApplication internshipApp = new InternshipApplication(
							"A" + InternshipApplicationManager.getNextIDAndIncrement(),
							getUserID(),
							internshipID,
							InternshipApplicationStatus.PENDING
					);
					InternshipApplicationManager.addInternshipApp(internshipApp);
					System.out.println("Successfully submitted internship application! Good luck with your application!");
					break;
				}
			}
			if (!found) System.out.println("Sorry, internship ID not found. Please try again.");
		}
	}
	
	// ACCEPT/REJECT INTERNSHIP PLACEMENT
	public void acceptRejectInternshipPlacement(Scanner sc) {
		System.out.print("\033[H\033[2J");
		ArrayList<InternshipApplication> iApps = InternshipApplicationManager.getInternshipApps(null, super.getUserID(), null, InternshipApplicationStatus.SUCCESSFUL);
		ArrayList<InternshipApplication> allIApps = InternshipApplicationManager.getInternshipApps();
		if (iApps.isEmpty()) {
			System.out.println("Sorry, you don't have any internship applications that are successful :(");
			return;
		}
		System.out.println("Congratulations! You have sucessful internship applications!");
		System.out.println("==== Your Successful Applications ====");
		for (InternshipApplication iApp : iApps) {
			iApp.print();
			System.out.println("-".repeat(40));
		}
		
		String applicationID = "";
		while (applicationID.isEmpty()) {
			System.out.print("Enter an Application ID: ");
			applicationID = sc.next();
			for (InternshipApplication studentIApp : iApps) {
				if (studentIApp.getApplicationID().equals(applicationID)) {
					int choice = 0;
					while (choice<=0 || choice>2) {
						System.out.println("Enter a choice:");
						System.out.println("(1) Accept");
						System.out.println("(2) Reject");
						System.out.print("Your choice: ");
						choice = sc.nextInt();
						
						if (choice==1) {
							studentIApp.setStatus(InternshipApplicationStatus.ACCEPTED);
							for (InternshipApplication iApplication : allIApps) {
								if (iApplication.getApplicationID().equals(studentIApp.getApplicationID())) break;
								
								// withdraw others
								if (iApplication.getStudentID().equals(super.getUserID())) {
									allIApps.remove(iApplication);
								}
							}
							InternshipOpportunityManager.updateFilledPlaces(); // update filled status if filled
							System.out.println("Successfully accepted internship placement");
						} else if (choice==2) {
							studentIApp.setStatus(InternshipApplicationStatus.REJECTED);
							System.out.println("Successfully rejected internship placement");
						} else {
							System.out.println("Please select either 1 or 2.");
						}
					}
				}
			}
		}
		
	}

	@Override
	public void displayHome(Scanner sc) {
		// TODO Auto-generated method stub
		
		System.out.println("Welcome, " + super.getName() + " (" + super.getUserID() + ")");
		
		int choice = 0;
		while (choice != 8) {
			System.out.println("=====================================================");
			System.out.println("Choose an option: ");
			System.out.println("(1) View Available Internships");
			System.out.println("(2) View Your Internship Application(s)");
			System.out.println("(3) Apply for Internship");
			System.out.println("(4) Accept/Reject Internship Placement");
			System.out.println("(5) Submit Withdrawal Request");
			System.out.println("(6) View Profile");
			System.out.println("(7) Change Password ");
			System.out.println("(8) Logout ");
			System.out.println("=====================================================");
			
			System.out.print("Enter a choice: ");
			choice = sc.nextInt();
			if (choice==1) {
				viewInternshipOpps(sc);
			} else if (choice==2) {
				viewInternshipApplications(sc);
			} else if (choice==3) {
				applyInternship(sc);
			} else if (choice==4) {
				acceptRejectInternshipPlacement(sc);
			} else if (choice==5) {
				withdrawApp(sc);
			} else if (choice==6) {
				viewProfile(sc);
			} else if (choice==7) {
				changePassword(sc);
			} else if (choice==8) {
				System.out.print("\033[H\033[2J");
				System.out.println("Logged out!");
			}
		}
	}

	@Override
	public void print() {
		ArrayList<InternshipApplication> internshipApps = InternshipApplicationManager.getInternshipApps(
				"",
				getUserID(),
				"",
				InternshipApplicationStatus.PENDING
			);
		
		System.out.println("Student ID         : " + getUserID());
		System.out.println("Name               : " + getName());
		System.out.println("Major              : " + major);
		System.out.println("Year               : " + year);
		System.out.println("Email              : " + getEmail());
		String internshipsApplied = internshipApps.stream().map(its->its.getInternshipID()).collect(Collectors.joining(", "));
		System.out.println("Internhips Applied : " + (!internshipsApplied.isEmpty() ? internshipsApplied : "None"));
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
		ArrayList<Student> students = StudentManager.getStudents();
		for (Student student : students) {
			if (student.getUserID().equals(this.getUserID())) {
				student.setPassword(hashedPassword);
				super.setPassword(hashedPassword);
				break;
			}
		}
		System.out.println("Your password has been successfully changed!");
	}
}
