package sc2002OOP.obj.careercenterstaff;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

import sc2002OOP.main.Constants;
import sc2002OOP.main.FileIOHandler;
import sc2002OOP.main.PasswordManager;
import sc2002OOP.obj.User;

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
						"",
						"",
						"",
						"",
						"",
						Set.of(),
						null,
						new InternshipOpportunityStatus[]
						{InternshipOpportunityStatus.PENDING},
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
}
