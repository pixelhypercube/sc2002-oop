package sc2002OOP.obj;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

import sc2002OOP.main.Constants;
import sc2002OOP.main.FileIOHandler;
import sc2002OOP.main.PasswordManager;

public class CareerCenterStaff extends User {
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
	
	public static ArrayList<CareerCenterStaff> getAllStaff() {
		ArrayList<CareerCenterStaff> staffs = new ArrayList<>();
		String contents = FileIOHandler.getFileContents(Constants.STAFF_FILE);
		
		int index = 0;
		ArrayList<String> headers = new ArrayList<>();
		for (String line : contents.split(Constants.NEW_LINE)) {
			String[] data = line.split(Constants.DELIMITER);
			
			CareerCenterStaff newStaff = new CareerCenterStaff();
			for (int i = 0;i<data.length;i++) {
				String cell = data[i];
				if (index==0) headers.add(cell);
				else {
					if (headers.get(i).equals("StaffID"))
						newStaff.setUserID(cell);
					else if (headers.get(i).equals("Name"))
						newStaff.setName(cell);
					else if (headers.get(i).equals("Role"))
						newStaff.setRole(cell);
					else if (headers.get(i).equals("Department"))
						newStaff.setDepartment(cell);
					else if (headers.get(i).equals("Email"))
						newStaff.setEmail(cell);
					else if (headers.get(i).equals("Password"))
						newStaff.setPassword(cell);
				}
			}
			if (index++>0) staffs.add(newStaff);
		}
		return staffs;
	}
	
	public static ArrayList<CareerCenterStaff> getStaffFiltered(
		String staffID,
		String name,
		String role,
		String department,
		String email
	) {
		return (ArrayList<CareerCenterStaff>) getAllStaff()
				.stream()
				.filter(obj -> (
						(staffID.isEmpty() || obj.getUserID().equals(staffID)) &&
						(name.isEmpty() || obj.getUserID().equals(name)) &&
						(role.isEmpty() || obj.getUserID().equals(role)) &&
						(department.isEmpty() || obj.getUserID().equals(department)) &&
						(email.isEmpty() || obj.getUserID().equals(email))
				))
				.collect(Collectors.toList());
	}
	
	public void printCompanyReps() {
		ArrayList<CompanyRepresentative> companyReps = CompanyRepresentative.getCompanyRepsFilter(
			"",
			"",
			"",
			"",
			"",
			"Pending"
		);
		
		System.out.println("==========================================");
		for (CompanyRepresentative companyRep : companyReps) {
			companyRep.print();
			System.out.println("==========================================");
		}
	}
	
	// if we alr have the param
	public void printCompanyReps(ArrayList<CompanyRepresentative> companyReps) {
		System.out.println("==========================================");
		for (CompanyRepresentative companyRep : companyReps) {
			companyRep.print();
			System.out.println("==========================================");
		}
	}
	
	public void approveRejectCompRep(String compRepID, int status) {
		String contents = FileIOHandler.getFileContents(Constants.COMPANY_REPS_FILE);
		
		ArrayList<String> headers = new ArrayList<>();
		int index = 0;
		
		String amendedContents = "";
		for (String line : contents.split(Constants.NEW_LINE)) {
			if (line.trim().isEmpty()) continue;
			
			String[] data = line.split(Constants.DELIMITER);
			for (int i = 0;i<data.length;i++) {
				String cell = data[i].trim();
				if (index==0) {
					headers.add(cell);
					amendedContents += cell + (i==data.length-1 ? Constants.NEW_LINE : Constants.DELIMITER);
				}
				else {
					if (headers.get(i).equals("CompanyRepID")) {
						if (!compRepID.equals(cell)) {
							amendedContents += line.trim() + Constants.NEW_LINE;
							break;
						}
					}
					if (headers.get(i).equals("Status"))
						amendedContents += (status==1 ? "Approved" : "Rejected") + Constants.NEW_LINE;
					else
						amendedContents += cell + Constants.DELIMITER;
				}
			}
			index++;
		}
		FileIOHandler.writeFileContents(Constants.COMPANY_REPS_FILE,amendedContents);
	}
	
	public void approveRejectWithdrawalRequest(String applicationID, WithdrawalStatus status) {
		// file processing
		ArrayList<WithdrawalRequest> withdrawalRequests = WithdrawalRequest.getAllWithdrawalRequests();
//		ArrayList<InternshipApplication> internshipApplications = InternshipApplication.getAllInternshipApplications();
		
		// change vars
		String withdrawalContents = "ApplicationID,Status\n"; // put headers first
		for (WithdrawalRequest withdrawalRequest : withdrawalRequests) {
			withdrawalContents += withdrawalRequest.getApplicationID() + ",";
			withdrawalContents += (withdrawalRequest.getApplicationID().equals(applicationID) ? status : withdrawalRequest.getStatus()) + "\n";
		}
		
		FileIOHandler.writeFileContents(Constants.WITHDRAWAL_REQS_FILE, withdrawalContents);
		
		if (status==WithdrawalStatus.SUCCESSFUL) {
			String internshipContents = FileIOHandler.getFileContents(Constants.INTERNSHIP_APPLICATIONS_FILE);
			String updatedContents = "";
			ArrayList<String> headers = new ArrayList<>();
			int index = 0;
			for (String line : internshipContents.split(Constants.NEW_LINE)) {
				String[] data = line.split(Constants.DELIMITER);
				
				for (int i = 0;i<data.length;i++) {
					String cell = data[i];
					if (index==0) {
						headers.add(cell);
						updatedContents += cell + (i==data.length-1 ? Constants.NEW_LINE : Constants.DELIMITER);
					}
					else {
						if (headers.get(i).equals("ApplicationID")) {
							// only add those that are not 'applicationID' as 'applicationID' ones are deleted already
							if (cell.equals(applicationID)) break;
						}
						updatedContents += line + Constants.NEW_LINE;
						break;
					}
				}
				index++;
			}
			FileIOHandler.writeFileContents(Constants.WITHDRAWAL_REQS_FILE,updatedContents);
		} 
//		else if (status==WithdrawalStatus.UNSUCCESSFUL) {
//			
//		}
	}
	
	public void approveRejectInternshipOpp(Scanner sc) {
		ArrayList<InternshipOpportunity> internshipOpportunities =
				InternshipOpportunity.getFilteredInternshipOpportunities(
						"",
						"",
						"",
						"",
						"",
						null,
						null,
						new InternshipOpportunityStatus[] {InternshipOpportunityStatus.PENDING},
						null
				);
		String internshipID = "";
		boolean found = false;
		while (internshipID.isEmpty() || !found) {
			System.out.print("Enter the ID of the Internship ID.: ");
			internshipID = sc.next();
			for (InternshipOpportunity internshipOpp : internshipOpportunities) {
				if (internshipOpp.getInternshipID().equals(internshipID)) {
					found = true;
					break;
				}
			}
		}
		
		int status = 0;
		while (status<=0 || status>2) {
			System.out.println("1. Approve");
			System.out.println("2. Reject");
			System.out.print("Enter a status (choose 1 or 2): ");
			
			if (status<=0 || status>2)
				System.out.println("Invalid input. Choose either 1 or 2.");
		}
		
		
		// headers first
		String contents = "InternshipID,Title,Description,CompanyName,PreferredMajors,CompanyRepsInCharge,NumSlots,Level,Status,OpeningDate,ClosingDate,Visibility\n";
		for (InternshipOpportunity internshipOpp : internshipOpportunities) {
			
			contents += internshipOpp.getInternshipID()+Constants.DELIMITER;
			contents += internshipOpp.getTitle()+Constants.DELIMITER;
			contents += internshipOpp.getDescription()+Constants.DELIMITER;
			contents += internshipOpp.getCompanyName()+Constants.DELIMITER;
			contents += internshipOpp.getPreferredMajor()+Constants.DELIMITER;
			contents += "\""+String.join(Constants.SECOND_DELIMITER, internshipOpp.getCompanyRepsInCharge())+"\""+Constants.DELIMITER;
			contents += internshipOpp.getNumSlots()+Constants.DELIMITER;
			contents += internshipOpp.getLevel()+Constants.DELIMITER;
			if (internshipOpp.getInternshipID().equals(internshipID))
				contents += (
						status==1 ? InternshipOpportunityStatus.APPROVED
								: InternshipOpportunityStatus.REJECTED
						)+Constants.DELIMITER;
			else
				contents += internshipOpp.getStatus()+Constants.DELIMITER;
			contents += internshipOpp.getOpeningDate().format(DateTimeFormatter.ISO_LOCAL_DATE)+Constants.DELIMITER;
			contents += internshipOpp.getClosingDate().format(DateTimeFormatter.ISO_LOCAL_DATE)+Constants.DELIMITER;
			contents += (internshipOpp.isVisibility() ? "on" : "off")+Constants.DELIMITER;
		}
		FileIOHandler.writeFileContents(Constants.INTERNSHIP_OPPORTUNITIES_FILE,contents);
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
	
	public static ArrayList<CareerCenterStaff> getAllCareerCenterStaff() {
		ArrayList<CareerCenterStaff> staff = new ArrayList<>();
		
		String contents = FileIOHandler.getFileContents(Constants.STAFF_FILE);
		
		int index = 0;
		ArrayList<String> headers = new ArrayList<>();
		for (String line : contents.split(Constants.NEW_LINE)) {
			if (line.trim().isEmpty()) continue;
			
			String[] data = line.split(Constants.DELIMITER);
			
			CareerCenterStaff newStaff = new CareerCenterStaff();
			for (int i = 0;i<data.length;i++) {
				String field = data[i];
				
				if (index==0) headers.add(field);
				else {
					if (headers.get(i).equals("StaffID"))
						newStaff.setUserID(field);
					else if (headers.get(i).equals("Name"))
						newStaff.setName(field);
					else if (headers.get(i).equals("Role"))
						newStaff.setRole(field);
					else if (headers.get(i).equals("Department"))
						newStaff.setDepartment(field);
					else if (headers.get(i).equals("Email"))
						newStaff.setEmail(field);
					else if (headers.get(i).equals("Password"))
						newStaff.setPassword(field);
				}
			}
			if (index++>0) staff.add(newStaff);
		}
		
		return staff;
	}

	@Override
	public void displayHome(Scanner sc) {
		// TODO Auto-generated method stub
		System.out.println("Welcome, " + super.getName() + " (" + super.getUserID() + ")");
		
		int choice = 0;
		while (choice != 5) {
			System.out.println("=====================================================");
			System.out.println("Choose an option: ");
			System.out.println("1. Approve/Reject Company Representative");
			System.out.println("2. Approve/Reject Internship Opportunity");
			System.out.println("3. Approve/Reject Withdrawal Request");
			System.out.println("4. Change Password");
			System.out.println("5. Logout");
			System.out.println("=====================================================");
			System.out.print("Your choice: ");
			choice = sc.nextInt();
			switch (choice) {
				case 1 -> {
					ArrayList<CompanyRepresentative> companyReps = CompanyRepresentative.getCompanyRepsFilter(
							"",
							"",
							"",
							"",
							"",
							"Pending"
						);
					if (companyReps.isEmpty()) {
						System.out.println("Sorry, there are no pending company represenatives at the moment.");
						break;
					}
					printCompanyReps(companyReps);
					
					String compRep = "";
					while (compRep.isEmpty()) {
						System.out.print("Enter the ID of the company rep.: ");
						compRep = sc.next();
					}
					
					int status = 0;
					while (status<1 || status>2) {
						System.out.println("1. Approve");
						System.out.println("2. Reject");
						System.out.print("Choose a status: ");
						status = sc.nextInt();
					}
					approveRejectCompRep(compRep,status);
				}
				case 2 -> {
					approveRejectInternshipOpp(sc);
				}
				case 3 -> {
					String appilcationID = "";
					while (appilcationID.isEmpty()) {
						System.out.print("Enter the ID of the Application ID.: ");
						appilcationID = sc.next();
					}
					
					int status = 0;
					while (status<1 || status>2) {
						System.out.println("1. Approve");
						System.out.println("2. Reject");
						System.out.print("Choose a status: ");
						status = sc.nextInt();
					}
					approveRejectWithdrawalRequest(appilcationID,status==1 ? WithdrawalStatus.SUCCESSFUL : WithdrawalStatus.UNSUCCESSFUL);
				}
				case 4 -> {
					changePassword(sc);
				}
				case 5 -> {
					System.out.println("Bon voyage!");
				}
			}
		}
	}

	@Override
	public void viewProfile(Scanner sc) {
		// TODO Auto-generated method stub
		
	}
//	@Override
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
		ArrayList<CareerCenterStaff> staffs = getAllStaff();
		for (CareerCenterStaff staff : staffs) {
			if (staff.getUserID().equals(this.getUserID())) {
				staff.setPassword(hashedPassword);
				super.setPassword(hashedPassword);
				break;
			}
		}
		
		String contents = "StaffID,Name,Role,Department,Email,Password\n";
		for (CareerCenterStaff staff : staffs) {
			System.out.println(staff.getUserID() + " " + this.getUserID());
			System.out.println(staff.getUserID().equals(this.getUserID()));
			String passwordToSave = (staff.getUserID().equals(this.getUserID())) 
                    ? hashedPassword 
                    : staff.getPassword();
			contents += staff.getUserID()+Constants.DELIMITER;
			contents += staff.getName()+Constants.DELIMITER;
			contents += staff.getRole()+Constants.DELIMITER;
			contents += staff.getDepartment()+Constants.DELIMITER;
			contents += staff.getEmail()+Constants.DELIMITER;
			contents += passwordToSave+Constants.NEW_LINE;
		}
		FileIOHandler.writeFileContents(Constants.STAFF_FILE, contents);

		System.out.println("Your password has been successfully changed!");
	}
}
