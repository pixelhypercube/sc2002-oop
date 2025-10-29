package sc2002OOP.obj;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.stream.Collectors;

import sc2002OOP.main.Constants;
import sc2002OOP.main.FileIOHandler;

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
			System.out.println(super.getName()+"'s list of Internship Opportunities:");
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
//				System.out.println("Company Name: "+internship.getCompanyName());
//				System.out.println("Title: "+internship.getTitle());
//				System.out.println("Description: "+internship.getDescription());
			}
		} else {
			System.out.println("You do not have any internship opportunities!");
		}
	}
	
	@Override
	public void viewProfile(Scanner sc) {
		System.out.println("STUDENT PROFILE: ");
		System.out.println("==========================================");
		System.out.println("Student ID : " + getUserID());
		System.out.println("Name       : " + getName());
		System.out.println("Major      : " + getMajor());
		System.out.println("Year       : " + getYear());
		System.out.println("Email      : " + getEmail());
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
					? new InternshipLevel[]{InternshipLevel.BASIC,InternshipLevel.INTERMEDIATE}
					: new InternshipLevel[]{InternshipLevel.BASIC,InternshipLevel.INTERMEDIATE,InternshipLevel.ADVANCED},
					true
				);
		
		if (internshipList != null) {
			System.out.println("LIST OF INTERNSHIPS: ");
			System.out.println("=======================================");
			for (InternshipOpportunity internship : internshipList) {
				internship.printInternship();
				System.out.println("=======================================");
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
						internship.getStatus()==InternshipStatus.FILLED) {
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
		while (choice != 6) {
			System.out.println("=====================================================");
			System.out.println("Choose an option: ");
			System.out.println("1. View Available Internships  ");
			System.out.println("2. Apply for Internship  ");
			System.out.println("3. View Your Internship Opportunities ");
			System.out.println("4. View Profile");
			System.out.println("5. Change Password ");
			System.out.println("6. Logout ");
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
				viewProfile(sc);
			} else if (choice==5) {
				changePassword(sc);
			} else if (choice==6) {
				System.out.println("See you later!");
			}
		}
	}

	@Override
	public void refreshData(Scanner sc) {
		// TODO Auto-generated method stub
		
	}
	
	
}
