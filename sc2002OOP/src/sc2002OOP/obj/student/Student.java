package sc2002OOP.obj.student;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

import sc2002OOP.main.Constants;
import sc2002OOP.main.FileIOHandler;
import sc2002OOP.main.PasswordManager;
import sc2002OOP.main.Test;
import sc2002OOP.obj.User;
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
			for (InternshipApplication internship : InternshipApplicationManager.getInternshipApps("", super.getUserID(), "", null)) {
				System.out.println("Application ID: "+internship.getApplicationID());
				System.out.println("Internship ID: "+internship.getInternshipID());
				
				String statusStr = "";
				switch (internship.getStatus()) {
					case InternshipApplicationStatus.PENDING ->
						statusStr = "Pending";
					case InternshipApplicationStatus.SUCCESSFUL ->
					statusStr = "Successful";
					case InternshipApplicationStatus.UNSUCCESSFUL ->
					statusStr = "Unsuccessful";
					case InternshipApplicationStatus.ACCEPTED ->
						statusStr = "Accepted";
					case InternshipApplicationStatus.REJECTED ->
						statusStr = "Rejected";
				}
				System.out.println("Status:        "+statusStr);
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
	
	public void printAllInternships() {
		System.out.print("\033[H\033[2J");
		ArrayList<InternshipOpportunity> internshipList = 
//				InternshipOpportunity.getAllInternshipOpportunities();
				InternshipOpportunityManager.getInternshipOpps(
					"",
					"",
					"",
					"",
					"",
					Set.of(),
					(year<=2) 
					? new InternshipOpportunityLevel[]{InternshipOpportunityLevel.BASIC,InternshipOpportunityLevel.INTERMEDIATE}
					: new InternshipOpportunityLevel[]{InternshipOpportunityLevel.BASIC,InternshipOpportunityLevel.INTERMEDIATE,InternshipOpportunityLevel.ADVANCED},
					null,
					true
				).stream()
				.filter(opp->opp.getStatus()==InternshipOpportunityStatus.APPROVED)
				.collect(Collectors.toCollection(ArrayList::new));
		
		if (internshipList != null) {
			System.out.println("===== LIST OF INTERNSHIPS =====");
			for (InternshipOpportunity internship : internshipList) {
				internship.printForStudent();
				System.out.println("-".repeat(40));
			}
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
					else if (internship.getStatus()==InternshipOpportunityStatus.PENDING) {
						System.out.println("Sorry, the internship you've tried to apply is currently awaiting approval from one of our career center staff. Please select another Internship ID.");
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
					break;
				}
			}
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
			System.out.println("(1) View Available Internships  ");
			System.out.println("(2) View Your Internship Application(s) ");
			System.out.println("(3) Apply for Internship  ");
			System.out.println("(4) Accept/Reject Internship Placement  ");
			System.out.println("(5) Submit Withdrawal Request");
			System.out.println("(6) View Profile");
			System.out.println("(7) Change Password ");
			System.out.println("(8) Logout ");
			System.out.println("=====================================================");
			
			System.out.print("Enter a choice: ");
			choice = sc.nextInt();
			if (choice==1) {
				printAllInternships();
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
