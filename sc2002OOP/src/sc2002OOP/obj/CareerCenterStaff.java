package sc2002OOP.obj;

import java.util.ArrayList;
import java.util.Scanner;

import sc2002OOP.main.Constants;
import sc2002OOP.main.FileIOHandler;

public class CareerCenterStaff extends User {
	private String role, department;
	
	public CareerCenterStaff() {};
	public CareerCenterStaff(String userID, String name, String role, String department, String email, String password) {
		super(userID, name, email, password);
		this.role = role;
		this.department = department;
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
//			System.out.println(updatedContents);
			FileIOHandler.writeFileContents(Constants.INTERNSHIP_APPLICATIONS_FILE, updatedContents);
		} 
//		else if (status==WithdrawalStatus.UNSUCCESSFUL) {
//			
//		}
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
		while (choice != 4) {
			System.out.println("=====================================================");
			System.out.println("Choose an option: ");
			System.out.println("1. Approve/Reject Company Representative");
			System.out.println("2. Approve/Reject Withdrawal Request");
			System.out.println("2. Change Password");
			System.out.println("3. Logout");
			System.out.println("=====================================================");
			System.out.print("Your choice: ");
			choice = sc.nextInt();
			switch (choice) {
				case 1 -> {
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
				case 3 -> {
					
				}
				case 4 -> {
					System.out.println("Bon voyage!");
				}
			}
		}
	}

	@Override
	public void viewProfile(Scanner sc) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void refreshData(Scanner sc) {
		// TODO Auto-generated method stub
		
	}
}
