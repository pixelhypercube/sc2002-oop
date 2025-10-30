package sc2002OOP.obj;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

import sc2002OOP.main.Constants;
import sc2002OOP.main.FileIOHandler;
import sc2002OOP.main.PasswordManager;

public class Student extends User {
	private String major;
	private int year;
	private ArrayList<InternshipApplication> internships;
	
	public Student() {};
	
	public Student(String userID, String name, String major, int year, String email, String password) {
		super(userID,name,email,password);
		this.major = major;
		this.year = year;
	}
	
	public void setInternhips(ArrayList<InternshipApplication> internships) {
		this.internships = internships;
	}
	
	public void viewInternshipOpportunities(Scanner sc) {
		if (internships != null) {
			System.out.println(super.getName()+"'s list of Internship Opportunities available:");
			for (InternshipApplication internship : InternshipApplication.getFilteredInternshipApplications("", super.getUserID(), "", null)) {
				System.out.println("Internship ID: "+internship.getInternshipID());
				
				String statusStr = "";
				switch (internship.getStatus()) {
					case InternshipApplicationStatus.PENDING ->
						statusStr = "Pending";
					case InternshipApplicationStatus.ACCEPTED ->
						statusStr = "Accepted";
					case InternshipApplicationStatus.REJECTED ->
						statusStr = "Rejected";
					case InternshipApplicationStatus.FILLED ->
						statusStr = "Filled";
				}
				System.out.println("Status:        "+statusStr);
			}
		} else {
			System.out.println("You do not have any internship opportunities!");
		}
	}
	
	@Override
	public void viewProfile(Scanner sc) {
		System.out.println("STUDENT PROFILE: ");
		System.out.println("==========================================");
		print();
		System.out.println("==========================================");
	}
	
	public void printAllInternships() {
		ArrayList<InternshipOpportunity> internshipList = 
//				InternshipOpportunity.getAllInternshipOpportunities();
				InternshipOpportunity.getFilteredInternshipOpportunities(
					"",
					"",
					"",
					"",
					"",
					null,
					(year<=2) 
					? new InternshipOpportunityLevel[]{InternshipOpportunityLevel.BASIC,InternshipOpportunityLevel.INTERMEDIATE}
					: new InternshipOpportunityLevel[]{InternshipOpportunityLevel.BASIC,InternshipOpportunityLevel.INTERMEDIATE,InternshipOpportunityLevel.ADVANCED},
					null,
					true
				);
		
		if (internshipList != null) {
			System.out.println("LIST OF INTERNSHIPS: ");
			System.out.println("=====================================================");
			for (InternshipOpportunity internship : internshipList) {
				internship.printInternship();
				System.out.println("=====================================================");
			}
		}
	}

	public static ArrayList<Student> getStudents() {
		ArrayList<Student> students = new ArrayList<>();
		
		String contents = FileIOHandler.getFileContents(Constants.STUDENT_FILE);
		
		int index = 0;
		ArrayList<String> headers = new ArrayList<>();
		for (String line : contents.split(Constants.NEW_LINE)) {
			if (line.trim().isEmpty()) continue;
			
			String[] data = line.split(Constants.DELIMITER);
			
			Student newStudent = new Student();
			for (int i = 0;i<data.length;i++) {
				String field = data[i];
				
				
				if (index==0) headers.add(field);
				else {
					if (headers.get(i).equals("StudentID")) {
						newStudent.setUserID(field);
						
						// fill in student's internship opportunities
						ArrayList<InternshipApplication> internships = 
								InternshipApplication.getFilteredInternshipApplications(
										"", 
										field, 
										"", 
										null
								);
						if (internships!=null) newStudent.setInternhips(internships);
					}
					else if (headers.get(i).equals("Name"))
						newStudent.setName(field);
					else if (headers.get(i).equals("Major"))
						newStudent.setMajor(field);
					else if (headers.get(i).equals("Year"))
						newStudent.setYear(Integer.parseInt(field));
					else if (headers.get(i).equals("Email"))
						newStudent.setEmail(field);
					else if (headers.get(i).equals("Password"))
						newStudent.setPassword(field);
					
				}
			}
			if (index++>0) students.add(newStudent);
		}
		
		return students;
	}
	
	public static ArrayList<Student> getStudentsFiltered(
		String studentID, String name, String major, int year, String email
	) {
		return (ArrayList<Student>) getStudents()
				.stream()
				.filter(obj->(
					(!studentID.isEmpty() || obj.getUserID().equals(studentID)) &&
					(!name.isEmpty() || obj.getName().equals(name)) &&
					(!major.isEmpty() || obj.getMajor().equals(major)) &&
					(year==0 || year==obj.getYear()) &&
					(!major.isEmpty() || obj.getMajor().equals(major))
				))
				.collect(Collectors.toList());
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
	
//	 WITHDRWAW INTERNSHIP APPLICATION
	public void withdraw(Scanner sc) {
		ArrayList<WithdrawalRequest> withdrawalReqList = WithdrawalRequest.getAllWithdrawalRequests();
		
		ArrayList<InternshipApplication> internshipAppList = InternshipApplication.getFilteredInternshipApplications(
					"",
					getUserID(),
					"",
					InternshipApplicationStatus.PENDING
				);
		
		Set<String> excludedApplicationIDs = withdrawalReqList.stream()
				.map(WithdrawalRequest::getApplicationID)
				.collect(Collectors.toSet());
		
		ArrayList<InternshipApplication> filteredInternshipAppList = internshipAppList.stream()
				.filter(app->!excludedApplicationIDs.contains(app.getApplicationID()))
				.collect(Collectors.toCollection(ArrayList::new));
		
		if (internshipAppList.size()>0) {
			System.out.println("=====================================================");
			for (InternshipApplication internshipApp : filteredInternshipAppList) {
				internshipApp.print();
				System.out.println("=====================================================");
			}
			
			String applicationID = "";
			boolean found = false;
			boolean inWithdrawalReqList = false;
			while (applicationID.isEmpty() || !found || inWithdrawalReqList) {
				inWithdrawalReqList = false;
				System.out.print("Enter an Application ID to withdraw: ");
				applicationID = sc.next();
				// check if it's inside withdrawal req list
				for (WithdrawalRequest withdrawalReq : withdrawalReqList) {
					if (withdrawalReq.getApplicationID().equals(applicationID)) {
						System.out.println("You have already submitted the request for this application! Please try another application.");
						inWithdrawalReqList = true;
						break;
					}
				}
				
				if (inWithdrawalReqList) continue;
				
				for (InternshipApplication internshipApp : filteredInternshipAppList) {
					if (internshipApp.getApplicationID().equals(applicationID)) {
						found = true;
						
						
						// create new record
						String contents = FileIOHandler.getFileContents(Constants.WITHDRAWAL_REQS_FILE);
						contents += applicationID + ",";
						contents += "PENDING\n";
						
						System.out.println("Successfully Submitted Withdrawal Application!");
						
						FileIOHandler.writeFileContents(Constants.WITHDRAWAL_REQS_FILE, contents);
						break;
					}
				}
				if (!found) System.out.println("Application ID not found. Please try again!");
			}
		} else {
			System.out.println("Sorry, you don't have any pending internship applications at the moment.");
		}
		
		
	}
	
	public void applyInternship(Scanner sc) {
		printAllInternships();
		
		ArrayList<InternshipOpportunity> internshipList = InternshipOpportunity.getAllInternshipOpportunities();
		String internshipID = "";
		boolean found = false;
		while (!found || internshipID.length()<=0) {
			System.out.print("Type the Internship ID to submit your application: ");
			internshipID = sc.next();
			if (internshipID.length()<=0) continue;
			
			for (InternshipOpportunity internship : internshipList) {
				if (internshipID.equals(internship.getInternshipID())) {
					if (internship.getNumSlots()==0 ||
						internship.getStatus()==InternshipOpportunityStatus.FILLED) {
						System.out.println("Sorry, the internship you've tried to apply for is full. Please fill in another internship.");
						break;
					}
						
					found = true;
					InternshipApplication internshipApp = new InternshipApplication(
							"A" + (internshipList.size()),
							getUserID(),
							internshipID,
							InternshipApplicationStatus.PENDING
					);
					internships.add(internshipApp);
					
					// FILE HANDLING
//					FileIOHandler.applyStudentInternship("internship_applications.csv",internshipApp);
					
					String amendedContents = FileIOHandler.getFileContents("internship_applications.csv");
					amendedContents += internshipApp.getStudentID()+",";
					amendedContents += internshipApp.getInternshipID()+",";
					amendedContents += internshipApp.getStatus();
					
					FileIOHandler.writeFileContents("internship_applications.csv", amendedContents);
					
					break;
				}
			}
		}
	}

	@Override
	public void displayHome(Scanner sc) {
		// TODO Auto-generated method stub
		
		System.out.println("Welcome, " + super.getName() + " (" + super.getUserID() + ")");
		
		int choice = 0;
		while (choice != 7) {
			System.out.println("=====================================================");
			System.out.println("Choose an option: ");
			System.out.println("1. View Available Internships  ");
			System.out.println("2. Apply for Internship  ");
			System.out.println("3. View Your Internship Opportunities ");
			System.out.println("4. Submit Withdrawal Request");
			System.out.println("5. View Profile");
			System.out.println("6. Change Password ");
			System.out.println("7. Logout ");
			System.out.println("=====================================================");
			
			System.out.print("Enter a choice: ");
			choice = sc.nextInt();
			if (choice==1) {
				printAllInternships();
			} else if (choice==2) {
				applyInternship(sc);
			} else if (choice==3) {
				viewInternshipOpportunities(sc);
			} else if (choice==4) {
				withdraw(sc);
			} else if (choice==5) {
				viewProfile(sc);
			} else if (choice==6) {
				changePassword(sc);
			} else if (choice==7) {
				System.out.println("See you later!");
			}
		}
	}

//	@Override
//	public void refreshData(Scanner sc) {
//		// TODO Auto-generated method stub
//		
//	}

	@Override
	public void print() {
		// TODO Auto-generated method stub
		System.out.println("Student ID : " + getUserID());
		System.out.println("Name       : " + getName());
		System.out.println("Major      : " + getMajor());
		System.out.println("Year       : " + getYear());
		System.out.println("Email      : " + getEmail());
	}

	@Override
	public void changePassword(Scanner sc) {
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
		ArrayList<Student> students = getStudents();
		for (Student student : students) {
			if (student.getUserID().equals(this.getUserID())) {
				student.setPassword(hashedPassword);
				super.setPassword(hashedPassword);
				break;
			}
		}
		
		String contents = "StudentID,Name,Major,Year,Email,Password\n";
		for (Student student : students) {
			
			String passwordToSave = (student.getUserID().equals(this.getUserID())) 
                    ? hashedPassword 
                    : student.getPassword();
			contents += student.getUserID()+Constants.DELIMITER;
			contents += student.getName()+Constants.DELIMITER;
			contents += student.getMajor()+Constants.DELIMITER;
			contents += student.getYear()+Constants.DELIMITER;
			contents += student.getEmail()+Constants.DELIMITER;
			contents += passwordToSave+Constants.NEW_LINE;
		}
		
		FileIOHandler.writeFileContents(Constants.STUDENT_FILE, contents);

		System.out.println("Your password has been successfully changed!");
	}
	
	
}
