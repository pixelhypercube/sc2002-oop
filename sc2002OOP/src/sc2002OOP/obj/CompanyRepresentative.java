package sc2002OOP.obj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

import sc2002OOP.main.*;

public class CompanyRepresentative extends User {
	private String companyName, department, position, email, status;

	public CompanyRepresentative() {}
	
	public CompanyRepresentative(String userID, String name, String companyName, String department, String position, String email, String status, String password) {
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
	
	public static ArrayList<CompanyRepresentative> getCompanyReps() {
		ArrayList<CompanyRepresentative> companyReps = new ArrayList<>();
		
		String contents = FileIOHandler.getFileContents(Constants.COMPANY_REPS_FILE);
		
		int index = 0;
		ArrayList<String> headers = new ArrayList<>();
		for (String line : contents.split(Constants.NEW_LINE)) {
			if (line.trim().isEmpty()) continue;
			
			String[] data = line.split(Constants.DELIMITER);
			
			CompanyRepresentative newCompRep = new CompanyRepresentative();
			for (int i = 0;i<data.length;i++) {
				String field = data[i];
				
				if (index==0) headers.add(field);
				else {
					if (headers.get(i).equals("CompanyRepID"))
						newCompRep.setUserID(field);
					else if (headers.get(i).equals("CompanyName"))
						newCompRep.setName(field);
					else if (headers.get(i).equals("Department"))
						newCompRep.setDepartment(field);
					else if (headers.get(i).equals("Position"))
						newCompRep.setPosition(field);
					else if (headers.get(i).equals("Email"))
						newCompRep.setEmail(field);
					else if (headers.get(i).equals("Status"))
						newCompRep.setStatus(field);
					else if (headers.get(i).equals("Password"))
						newCompRep.setPassword(field);
				}
			}
			if (index++>0) companyReps.add(newCompRep);
		}
		
		return companyReps;
	}
	
	public static ArrayList<CompanyRepresentative> getCompanyRepsFilter(
		String companyRepID, String companyName, String department, String position, String email, String status
	) {
		return (ArrayList<CompanyRepresentative>) getCompanyReps()
				.stream()
				.filter(obj -> (
						(companyRepID.isEmpty() || obj.getUserID().equals(companyRepID)) &&
						(companyName.isEmpty() || Objects.equals(obj.getCompanyName(), companyName)) &&
						(department.isEmpty() || Objects.equals(obj.getDepartment(), department)) &&
						(position.isEmpty() || Objects.equals(obj.getPosition(), position)) &&
						(status.isEmpty() || Objects.equals(obj.getStatus(), status)) &&
						(email.isEmpty() || Objects.equals(obj.getEmail(), email))
					))
				.collect(Collectors.toList());
	}
	
	public static void register(Scanner sc) {
		// name
		String name = "";
		while (name.isEmpty()) {
			System.out.print("Enter name:");
			name = sc.next();
			if (name.isEmpty()) {
				System.out.println("Name not entered.");
			}
		}
				
		// companyName
		String companyName = "";
		while (companyName.isEmpty()) {
			System.out.print("Enter company name:");
			companyName = sc.next();
			if (companyName.isEmpty()) {
				System.out.println("Company Name not entered.");
			}
		}
		
		// department
		String department = "";
		while (department.isEmpty()) {
			System.out.print("Enter department:");
			department = sc.next();
			if (department.isEmpty()) {
				System.out.println("Department not entered.");
			}
		}
		
		// position
		String position = "";
		while (position.isEmpty()) {
			System.out.print("Enter position:");
			position = sc.next();
			if (position.isEmpty()) {
				System.out.println("Position not entered.");
			}
		}
		
		// email
		String email = "";
		while (email.isEmpty()) {
			System.out.print("Enter email:");
			email = sc.next();
			if (email.isEmpty()) {
				System.out.println("Email not entered.");
			}
		}
		
		// status -> pending
		
		String contents = FileIOHandler.getFileContents(Constants.COMPANY_REPS_FILE);
		int noLines = contents.split(Constants.DELIMITER).length-1;
		contents += "S" + noLines + ",";
		contents += name + ",";
		contents += companyName + ",";
		contents += department + ",";
		contents += position + ",";
		contents += email + ",";
		contents += "Pending";
		
//		System.out.println(contents);
		FileIOHandler.writeFileContents(Constants.COMPANY_REPS_FILE, contents);
	}
	
	public void createInternship(Scanner sc) {
		// title
		String title = "";
		while (title.length()<=0) {
			System.out.print("Enter title:");
			title = sc.next();
			if (title.length()<=0) {
				System.out.println("Title not entered.");
			}
		}
		
		// description
		String description = "";
		while (description.length()<=0) {
			System.out.print("Enter description:");
			title = sc.next();
			if (title.length()<=0) {
				System.out.println("Description not entered.");
			}
		}
		
		int level = 0;
		while (level<=0 || level>3) {
			System.out.println("1 -> Basic");
			System.out.println("2 -> Intermediate");
			System.out.println("3 -> Advanced");
			System.out.print("Enter level: ");
			level = sc.nextInt();
			if (level<=0 || level>3) {
				System.out.println("Invalid input. Enter either 1, 2 or 3.");
			}
		}
		
		// preferred majors
		String preferredMajor = "";
		while (preferredMajor.length()<=0) {
			System.out.print("Enter company name:");
			preferredMajor = sc.next();
			if (preferredMajor.length()<=0) {
				System.out.println("Company name not entered.");
			}
		}
		
		// number of slots
		int numSlots = 0;
		while (numSlots<=0 || numSlots>10) {
			System.out.print("Enter number of slots: ");
			numSlots = sc.nextInt();
			
			if (numSlots<=0 || numSlots>10) {
				System.out.println("Enter within the range (1-10).");
				continue;
			}
		}
		
		boolean isVisible = false;
		String in = "";
		while (!in.equals("0") || !in.equals("1")) {
			System.out.println("0 -> off");
			System.out.println("1 -> on");
			System.out.print("Enter Visibility: ");
			in = sc.next();
			
			if (in.equals("0")) isVisible = true;
			else if (in.equals("1")) break;
			else
				System.out.println("Invalid input. Please enter 0 or 1.");
		}
		
		String internshipStr = FileIOHandler.getFileContents(Constants.INTERNSHIP_OPPORTUNITIES_FILE);
		int noLines = internshipStr.split(Constants.NEW_LINE).length-1;
		internshipStr += "I"+noLines+",";
		internshipStr += title+",";
		internshipStr += description+",";
		internshipStr += companyName+",";
		internshipStr += preferredMajor+",";
		internshipStr += String.join(",", super.getUserID()).replaceAll("\"","")+",";
		switch (level) {
			case 1 -> internshipStr += "Basic";
			case 2 -> internshipStr += "Intermediate";
			case 3 -> internshipStr += "Advanced";
		}
		
		internshipStr += status+",";
		internshipStr += isVisible ? "on" : "off"; // AMEND NOWWW
	}
	
	public void toggleInternshipOpportunity(Scanner sc) {
		String internshipID = "";
		String amendedContents = "";
		while (internshipID.trim().isEmpty()) {
			System.out.print("Enter Internship ID: ");
			internshipID = sc.next();
			
			String fileContents = FileIOHandler.getFileContents("internship_opportunities.csv");
			
			ArrayList<String> headers = new ArrayList<>();
			int index = 0;
			for (String line : fileContents.split("\n")) {
				String[] data = line.split(",");
				
				boolean found = false;
				for (int i = 0;i<data.length;i++) {
					if (index==0) {
						headers.add(data[i]);
						amendedContents += data[i] + (i<data.length-1 ? "," : "\n");
					} else {
						if (headers.get(i).equals("InternshipID")) {
							if (!data[i].equals(internshipID) && !found) {
								amendedContents += line + "\n";
								break; // fill the line and skip this line because this line is not selected
							}
						}
						found = true;
						
						// TOGGLES VISIBILITY
						if (headers.get(i).equals("Visibility")) 
							amendedContents += (data[i].equals("on") ? "off" : "on") + "\n";
						else 
							amendedContents += data[i] + ",";
					}
				}
				index++;
			}
		}
		FileIOHandler.writeFileContents(Constants.INTERNSHIP_OPPORTUNITIES_FILE, amendedContents);
	}
	
	public void exportApplicationsReport() {
		ArrayList<InternshipApplication> applications = 
		InternshipApplication.getAllInternshipApplications();
		
		
		ArrayList<InternshipOpportunity> companyRepList = 
		InternshipOpportunity.getFilteredInternshipOpportunities(
				"",
				"",
				"",
				"",
				"",
				new ArrayList<>(Arrays.asList(getUserID())),
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public void displayHome(Scanner sc) {
		// TODO Auto-generated method stub
		System.out.println("Welcome, " + super.getName() + " (" + super.getUserID() + ")");
		
		int choice = 0;
		while (choice != 6) {
			System.out.println("=====================================================");
			System.out.println("Choose an option: ");
			System.out.println("1. View All Internships");
			System.out.println("2. Create Internship Opportunity");
			System.out.println("3. Toggle Visibility of Internship Oppurtunity");
			System.out.println("4. Print/Export Applications Report");
			System.out.println("5. Change Password") 	;
			System.out.println("6. Log Out");
			System.out.println("=====================================================");
			System.out.print("Select a choice: ");
			choice = sc.nextInt();
			switch (choice) {
				case 1 -> {
					for (InternshipOpportunity internship : InternshipOpportunity.getAllInternshipOpportunities()) {
						System.out.println("================================================================");
						System.out.println("InternshipID                 :"+internship.getInternshipID());
						System.out.println("Title                        :"+internship.getTitle());
						System.out.println("Description                  :"+internship.getDescription());
						System.out.println("Company Name                 :"+internship.getCompanyName());
						System.out.println("Preferred Majors             :"+internship.getPreferredMajor());
						System.out.println("Company Rep(s) in Charge     :"+internship.getCompanyRepsInCharge().stream().collect(Collectors.joining(",")).trim());
						System.out.println("Number of Slots              :"+internship.getNumSlots());
						System.out.println("Level                        :"+internship.getLevel());
						System.out.println("Status                       :"+internship.getStatus());
						System.out.println("Visibility (For Students)    :"+(internship.isVisibility() ? "On" : "Off"));
					}
					System.out.println("================================================================");
				}
				case 2 -> {
					createInternship(sc);
				}
				case 3 -> {
					toggleInternshipOpportunity(sc);
				}
				case 4 -> {
					exportApplicationsReport();
				}
				case 5 -> {
					changePassword(sc);
				}
				case 6 -> {
					System.out.println("Logged out!");
					break;
				}
			}
		}
	}

	@Override
	public void viewProfile(Scanner sc) {
		// TODO Auto-generated method stub
		
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
		ArrayList<CompanyRepresentative> companyReps = getCompanyReps();
		for (CompanyRepresentative companyRep : companyReps) {
			if (companyRep.getUserID().equals(getUserID())) {
				companyRep.setPassword(hashedPassword);
				super.setPassword(hashedPassword);
				break;
			}
		}
		
		String contents = "CompanyRepID,Name,CompanyName,Department,Position,Email,Status,Password\n";
		for (CompanyRepresentative companyRep : companyReps) {
			String passwordToSave = (companyRep.getUserID().equals(this.getUserID())) 
                    ? hashedPassword 
                    : companyRep.getPassword();
			
			contents += companyRep.getUserID()+Constants.DELIMITER;
			contents += companyRep.getName()+Constants.DELIMITER;
			contents += companyRep.getCompanyName()+Constants.DELIMITER;
			contents += companyRep.getDepartment()+Constants.DELIMITER;
			contents += companyRep.getPosition()+Constants.DELIMITER;
			contents += companyRep.getEmail()+Constants.DELIMITER;
			contents += companyRep.getStatus()+Constants.DELIMITER;
			contents += passwordToSave+Constants.NEW_LINE;
		}
		
		FileIOHandler.writeFileContents(Constants.COMPANY_REPS_FILE, contents);

		System.out.println("Your password has been successfully changed!");
	}

//	@Override
//	public void refreshData(Scanner sc) {
//		// TODO Auto-generated method stub
//		
//	}
	
}
