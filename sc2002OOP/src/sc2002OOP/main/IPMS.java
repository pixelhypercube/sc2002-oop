package sc2002OOP.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

import sc2002OOP.obj.User;
import sc2002OOP.obj.careercenterstaff.*;
import sc2002OOP.obj.companyrepresentative.*;
import sc2002OOP.obj.internshipapplicaton.InternshipApplication;
import sc2002OOP.obj.internshipapplicaton.InternshipApplicationManager;
import sc2002OOP.obj.internshipopportunity.InternshipOpportunity;
import sc2002OOP.obj.internshipopportunity.InternshipOpportunityManager;
import sc2002OOP.obj.student.*;
import sc2002OOP.obj.withdrawalrequest.WithdrawalRequest;
import sc2002OOP.obj.withdrawalrequest.WithdrawalRequestManager;

public class IPMS {
	/**
	 * 
	 * 
	 * 
	 * @apiNote <api-note>
	 * @author Kee Kai Wen
	 * @author Kelvin Tay Wei Jie
	 * @author Koay Jun Zhi
	 * @author Lim Jia Wei Jerald
	 * @author Teo Kai Jie
	 * @since <since-date>
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
	
	private static void initInstances() {
		// USERS
		CareerCenterStaffManager.getInstance();
		CompanyRepresentativeManager.getInstance();
		StudentManager.getInstance();
		
		// INTERNSHIP STUFF & WITHDRAWAL REQS
		InternshipOpportunityManager.getInstance();
		InternshipApplicationManager.getInstance();
		WithdrawalRequestManager.getInstance();
	}
	
	private static void close() {
		// USERS
		CareerCenterStaffManager.close();
		CompanyRepresentativeManager.close();
		StudentManager.close();
		
		// INTERNSHIP STUFF & WITHDRAWAL REQS
		InternshipOpportunityManager.close();
		InternshipApplicationManager.close();
		WithdrawalRequestManager.close();
	}
	
	public static void printMenu() {
		System.out.println("==== Please select a choice to continue ====");
		System.out.println("(1) Login");
		System.out.println("(2) Register (Company Representative)");
		System.out.println("(3) Exit");
	}
	
	public static void main(String[] args) {
		initInstances();
		
		//save (debugging)
//		ArrayList<Student> students = StudentManager.retrieveStudents();
//		ArrayList<CareerCenterStaff> staff = CareerCenterStaffManager.retrieveStaff();
//		ArrayList<CompanyRepresentative> companyReps = CompanyRepresentativeManager.retrieveCompanyReps();
//		ArrayList<InternshipApplication> iApps = InternshipApplicationManager.getAllInternshipApps();
//		ArrayList<InternshipOpportunity> iOpps = InternshipOpportunityManager.retrieveInternshipOpps();
//		ArrayList<WithdrawalRequest> wReqs = WithdrawalRequestManager.getAllWithdrawalRequests();
//		
//		for (InternshipApplication iApp : iApps) iApp.print();
//		for (InternshipOpportunity iOpp : iOpps) iOpp.print();
//		for (WithdrawalRequest wReq : wReqs) wReq.print();
		
//		StudentManager.saveStudents(students);
//		CompanyRepresentativeManager.saveCompanyReps(companyReps);
//		CareerCenterStaffManager.saveStaffFiles(staff);
//		InternshipApplicationManager.saveInternshipApps(iApps);
//		InternshipOpportunityManager.saveInternshipOpps(iOpps);
//		WithdrawalRequestManager.saveWithdrawalRequests(wReqs);
		
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
				
				
//				Test.printAllRecords();
//				Viewer.printList(new ArrayList<>(Arrays.asList(new String[] {"Student ID","Major","Internships"})), "Student", StudentManager.getAllStudents());
				System.out.print("Your Choice: ");
				if (sc.hasNextInt()) {
					choice = sc.nextInt();
					if (choice==1) {
						User user = User.login(sc,users);
						if (user != null) user.displayHome(sc);
					} else if (choice==2) {
						CompanyRepresentativeManager.register(sc);
					}
					else if (choice==3) {
						System.out.println("Bon Voyage");
						close();
						break;
					}
				} else {
					System.out.println("Invalid choice. Please enter 1, 2, or 3.");
					sc.next();
				}
			}
		} catch (Exception e) {
			System.err.println("An unexpected error occurred: " + e.getMessage());
		}
		
	}
}
