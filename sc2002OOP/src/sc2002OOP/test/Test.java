package sc2002OOP.test;

import java.time.LocalDate;
import java.util.ArrayList;

import sc2002OOP.main.Constants;
import sc2002OOP.main.FileIOHandler;
import sc2002OOP.main.Viewer;
import sc2002OOP.obj.careercenterstaff.CareerCenterStaff;
import sc2002OOP.obj.careercenterstaff.CareerCenterStaffManager;
import sc2002OOP.obj.careercenterstaff.CareerCenterStaffView;
import sc2002OOP.obj.company.Company;
import sc2002OOP.obj.company.CompanyManager;
import sc2002OOP.obj.company.CompanyView;
import sc2002OOP.obj.companyrepresentative.CompanyRepresentative;
import sc2002OOP.obj.companyrepresentative.CompanyRepresentativeManager;
import sc2002OOP.obj.companyrepresentative.CompanyRepresentativeStatus;
import sc2002OOP.obj.companyrepresentative.CompanyRepresentativeView;
import sc2002OOP.obj.internshipapplicaton.InternshipApplication;
import sc2002OOP.obj.internshipapplicaton.InternshipApplicationManager;
import sc2002OOP.obj.internshipapplicaton.InternshipApplicationStatus;
import sc2002OOP.obj.internshipapplicaton.InternshipApplicationView;
import sc2002OOP.obj.internshipopportunity.InternshipOpportunity;
import sc2002OOP.obj.internshipopportunity.InternshipOpportunityLevel;
import sc2002OOP.obj.internshipopportunity.InternshipOpportunityManager;
import sc2002OOP.obj.internshipopportunity.InternshipOpportunityStatus;
import sc2002OOP.obj.internshipopportunity.InternshipOpportunityView;
import sc2002OOP.obj.student.Student;
import sc2002OOP.obj.student.StudentManager;
import sc2002OOP.obj.student.StudentView;
import sc2002OOP.obj.withdrawalrequest.WithdrawalRequest;
import sc2002OOP.obj.withdrawalrequest.WithdrawalRequestManager;
import sc2002OOP.obj.withdrawalrequest.WithdrawalRequestStatus;
import sc2002OOP.obj.withdrawalrequest.WithdrawalRequestView;


/*
 * TEST & DEBUGGING METHODS ONLY
 * 
 * */
public class Test {
	
	// printing out directly from csv files
	public static void printCsvData() {
		ArrayList<Student> students = retrieveStudentsFromCSV();
		ArrayList<CareerCenterStaff> staff = retrieveStaffFromCSV();
		ArrayList<CompanyRepresentative> companyReps = retrieveCompanyRepsFromCSV();
		ArrayList<InternshipApplication> iApps = retrieveInternshipAppsFromCSV();
		ArrayList<InternshipOpportunity> iOpps = retrieveInternshipOppsFromCSV();
		ArrayList<WithdrawalRequest> wReqs = retrieveWithdrawalReqsFromCSV();
		
		System.out.println("====== CAREER CENTER STAFF TABLE ======");
		CareerCenterStaffView.printTable(staff);
		System.out.println("====== COMPANY REPRESENTATIVES TABLE ======");
		CompanyRepresentativeView.printTable(companyReps);
		System.out.println("====== INTERNSHIP APPLICATIONS TABLE ======");
		InternshipApplicationView.printTable(iApps);
		System.out.println("====== INTERNSHIP OPPORTUNITIES TABLE ======");
		InternshipOpportunityView.printList(iOpps);
		System.out.println("====== STUDENTS TABLE ======");
		StudentView.printTable(students);
		System.out.println("====== WITHDRAWAL REQUESTS TABLE ======");
		WithdrawalRequestView.printTable(wReqs);
	}
	
	// helper function to print out test tables
	public static void printTestTables() {
		ArrayList<String> headers = new ArrayList<>();
	    headers.add("A");
	    headers.add("This Header Is Intentionally Much Longer Than Twenty-Five Characters");
	    headers.add("Perfect Fit 25 Characters");
	    
	    ArrayList<ArrayList<String>> data = new ArrayList<>();
	    
	    ArrayList<String> row1 = new ArrayList<>();
	    row1.add("");
	    row1.add("1");
	    row1.add("XX");
	    data.add(row1);

	    ArrayList<String> row2 = new ArrayList<>();
	    row2.add("1234567890");
	    row2.add("This data is exactly 26 long.");
	    row2.add("This cell data is too long to fit in the column width.");
	    data.add(row2);

	    ArrayList<String> row3 = new ArrayList<>();
	    row3.add("Max Col1");
	    row3.add("This String Is Exactly 25");
	    row3.add("This content is 22 ch");
	    data.add(row3);
	    
	    ArrayList<String> row4 = new ArrayList<>();
	    row4.add("End");
	    row4.add("Short");
	    row4.add("Medium Length Test");
	    data.add(row4);
	    Viewer.printTable(headers, data);
//		ArrayList<String> headers = new ArrayList<>();
//		headers.add("Header1");
//		headers.add("Headerrrrrrrrrrrrrfrrrrrr2");
//		headers.add("Header3");
//		
//		ArrayList<ArrayList<String>> data = new ArrayList<>();
//		for (int i = 0;i<3;i++) {
//			ArrayList<String> cells = new ArrayList<>();
//			for (int j = 0;j<3;j++) {
//				cells.add("Dataaaaaaaaaaaaaaaa"+(j+1));
//			}
//			data.add(cells);
//		}
//		Viewer.printTable(headers,data);
	}
	// retrieves from csv files, then update into the .dat files
	// CAUTION: this will AMEND the .dat files!
	public static void getFromCSVFilesAndUpdate() {
		ArrayList<Student> students = retrieveStudentsFromCSV();
		ArrayList<CareerCenterStaff> staff = retrieveStaffFromCSV();
		ArrayList<CompanyRepresentative> companyReps = retrieveCompanyRepsFromCSV();
		ArrayList<InternshipApplication> iApps = retrieveInternshipAppsFromCSV();
		ArrayList<InternshipOpportunity> iOpps = retrieveInternshipOppsFromCSV();
		ArrayList<WithdrawalRequest> wReqs = retrieveWithdrawalReqsFromCSV();
		
		ArrayList<Company> companies = new ArrayList<>();
		companies.add(new Company("C1","Test Pte. Ltd."));
		companies.add(new Company("C2","Robot Pte. Ltd."));
		companies.add(new Company("C3","Components Pte. Ltd."));
		companies.add(new Company("C4","Testing Inc."));
		companies.add(new Company("C5","Choco Ltd."));
		
		StudentManager.saveStudents(students);
		CompanyRepresentativeManager.saveCompanyReps(companyReps);
		CareerCenterStaffManager.saveStaffFiles(staff);
		InternshipApplicationManager.saveInternshipApps(iApps);
		InternshipOpportunityManager.saveInternshipOpps(iOpps);
		WithdrawalRequestManager.saveWithdrawalRequests(wReqs);
		CompanyManager.saveCompanies(companies);
	}
	
	// prints out all records from .dat files
	public static void printAllRecords() {
//		ArrayList<Student> students = StudentManager.retrieveStudents();
//		ArrayList<CareerCenterStaff> staff = CareerCenterStaffManager.retrieveStaff();
//		ArrayList<CompanyRepresentative> companyReps = CompanyRepresentativeManager.retrieveCompanyReps();
//		ArrayList<InternshipApplication> iApps = InternshipApplicationManager.retrieveInternshipApps();
//		ArrayList<InternshipOpportunity> iOpps = InternshipOpportunityManager.retrieveInternshipOpps();
//		ArrayList<WithdrawalRequest> wReqs = WithdrawalRequestManager.retrieveWithdrawalReqs();
//		ArrayList<Company> companies = CompanyManager.retrieveCompanies();
		
		System.out.println("====== CAREER CENTER STAFF TABLE ======");
		CareerCenterStaffView.printTable();
//		for (CareerCenterStaff st : staff) {
//			st.
//			System.out.println("-".repeat(40));
//		}
		
		System.out.println("====== COMPANY TABLE ======");
		CompanyView.printTable();
//		for (Company company : companies) {
//			company.print();
//			System.out.println("-".repeat(40));
//		}
		
		System.out.println("====== COMPANY REPRESENTATIVES TABLE ======");
		CompanyRepresentativeView.printTable();
		
//		for (CompanyRepresentative companyRep : companyReps) {
//			CompanyRepresentativeView.print(companyRep);
//			System.out.println("-".repeat(40));
//		}
		
		System.out.println("====== INTERNSHIP APPLICATIONS TABLE ======");
		InternshipApplicationView.printTable();
//		for (InternshipApplication iApp : iApps) {
//			iApp.print();
//			System.out.println("-".repeat(40));
//		}
		
		System.out.println("====== INTERNSHIP OPPORTUNITIES TABLE ======");
		InternshipOpportunityView.printList();
//		for (InternshipOpportunity iOpp : iOpps) {
//			iOpp.print();
//			System.out.println("-".repeat(40));
//		}
		
		System.out.println("====== STUDENTS TABLE ======");
		StudentView.printTable();
//		for (Student student : students) {
//			student.print();
//			System.out.println("-".repeat(40));
//		}
		
		System.out.println("===== WITHDRAWAL REQUESTS TABLE ======");
		WithdrawalRequestView.printTable();
//		for (WithdrawalRequest wr : wReqs) {
//			wr.print();
//			System.out.println("-".repeat(40));
//		}
	}
	
//	INDIV CSV METHODS
	
	public static ArrayList<CareerCenterStaff> retrieveStaffFromCSV() {
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
	
	public static ArrayList<CompanyRepresentative> retrieveCompanyRepsFromCSV() {
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
					if (headers.get(i).equals("Email"))
						newCompRep.setUserID(field);
					else if (headers.get(i).equals("Name"))
						newCompRep.setName(field);
					else if (headers.get(i).equals("CompanyID"))
						newCompRep.setCompanyID(field);
					else if (headers.get(i).equals("Department"))
						newCompRep.setDepartment(field);
					else if (headers.get(i).equals("Position"))
						newCompRep.setPosition(field);
					else if (headers.get(i).equals("Status")) {
						if (field.equals("APPROVED"))
							newCompRep.setStatus(CompanyRepresentativeStatus.APPROVED);
						else if (field.equals("PENDING"))
							newCompRep.setStatus(CompanyRepresentativeStatus.PENDING);
						else if (field.equals("REJECTED"))
							newCompRep.setStatus(CompanyRepresentativeStatus.REJECTED);
					}
					else if (headers.get(i).equals("Password"))
						newCompRep.setPassword(field);
				}
			}
			if (index++>0) companyReps.add(newCompRep);
		}
		
		return companyReps;
	}
	
	public static ArrayList<InternshipApplication> retrieveInternshipAppsFromCSV() {
		String contents = FileIOHandler.getFileContents(Constants.INTERNSHIP_APPLICATIONS_FILE);
		
		ArrayList<String> headers = new ArrayList<>();
		int index = 0;
		
		ArrayList<InternshipApplication> internshipApplications = new ArrayList<>();
		for (String line : contents.split(Constants.NEW_LINE)) {
			if (line.trim().isEmpty()) continue;
			
			InternshipApplication internshipApp = new InternshipApplication();
			
			String[] data = line.split(Constants.DELIMITER);
			
			for (int i = 0;i<data.length;i++) {
				String cell = data[i];
				
				if (index==0) headers.add(cell);
				else {
					if (headers.get(i).equals("ApplicationID"))
						internshipApp.setApplicationID(cell);
					else if (headers.get(i).equals("StudentID"))
						internshipApp.setStudentID(cell);
					else if (headers.get(i).equals("InternshipID"))
						internshipApp.setInternshipID(cell);
					else if (headers.get(i).equals("Status")) {
						if (cell.toLowerCase().equals("pending"))
							internshipApp.setStatus(InternshipApplicationStatus.PENDING);
						else if (cell.toLowerCase().equals("accepted"))
							internshipApp.setStatus(InternshipApplicationStatus.ACCEPTED);
						else if (cell.toLowerCase().equals("rejected"))
							internshipApp.setStatus(InternshipApplicationStatus.REJECTED);
//						else if (cell.toLowerCase().equals("filled"))
//							internshipApp.setStatus(InternshipApplicationStatus.FILLED);
					}
				}
			}
			if (index++>0) internshipApplications.add(internshipApp);
		}
		return internshipApplications;
	}
	
	public static ArrayList<InternshipOpportunity> retrieveInternshipOppsFromCSV() {
		
		String contents = FileIOHandler.getFileContents(Constants.INTERNSHIP_OPPORTUNITIES_FILE);
		ArrayList<InternshipOpportunity> internships = new ArrayList<>();
		
		int index = 0;
		ArrayList<String> headers = new ArrayList<>();
		
		for (String line : contents.split(Constants.NEW_LINE)) {
			if (line.trim().isEmpty()) continue;
			
			InternshipOpportunity newInternship = new InternshipOpportunity();
			String[] data = line.trim().split(Constants.DELIMITER);
			
			for (int i = 0;i<data.length;i++) {
				if (index==0) headers.add(data[i]);
				else {
					if (headers.get(i).equals("InternshipID"))
						newInternship.setInternshipID(data[i]);
					else if (headers.get(i).equals("Title"))
						newInternship.setTitle(data[i]);
					else if (headers.get(i).equals("Description"))
						newInternship.setDescription(data[i]);
					else if (headers.get(i).equals("CompanyID")) {
						newInternship.setCompanyID(data[i]);
					}
					else if (headers.get(i).equals("PreferredMajors"))
						newInternship.setPreferredMajor(data[i]);
					else if (headers.get(i).equals("NumSlots"))
						newInternship.setNumSlots(Integer.parseInt(data[i]));
					else if (headers.get(i).equals("Level")) {
						if (data[i].equals("Basic"))
							newInternship.setLevel(InternshipOpportunityLevel.BASIC);
						if (data[i].equals("Intermediate"))
							newInternship.setLevel(InternshipOpportunityLevel.INTERMEDIATE);
						if (data[i].equals("Advanced"))
							newInternship.setLevel(InternshipOpportunityLevel.ADVANCED);
					}
					else if (headers.get(i).equals("Status")) {
						if (data[i].equals("Pending"))
							newInternship.setStatus(InternshipOpportunityStatus.PENDING);
						else if (data[i].equals("Approved"))
							newInternship.setStatus(InternshipOpportunityStatus.APPROVED);
						else if (data[i].equals("Rejected"))
							newInternship.setStatus(InternshipOpportunityStatus.REJECTED);
						else if (data[i].equals("Filled"))
							newInternship.setStatus(InternshipOpportunityStatus.FILLED);
					}
					else if (headers.get(i).equals("Visibility"))
						newInternship.setVisibility(data[i].equals("on"));
					else if (headers.get(i).equals("OpeningDate"))
						newInternship.setOpeningDate(LocalDate.parse(data[i]));
					else if (headers.get(i).equals("ClosingDate"))
						newInternship.setClosingDate(LocalDate.parse(data[i]));
				}
			}
			if (index++ > 0) internships.add(newInternship);
		}
		return internships;
	}
	
	public static ArrayList<Student> retrieveStudentsFromCSV() {
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
	
	public static ArrayList<WithdrawalRequest> retrieveWithdrawalReqsFromCSV() {
		ArrayList<WithdrawalRequest> withdrawalReqs = new ArrayList<>();
		
		int index = 0;
		ArrayList<String> headers = new ArrayList<>();
		for (String line : FileIOHandler.getFileContents(Constants.WITHDRAWAL_REQS_FILE).split(Constants.NEW_LINE)) {
			System.out.println(line);
			if (line.trim().isEmpty()) continue;
			
			WithdrawalRequest withdrawalReq = new WithdrawalRequest();
			String[] data = line.split(Constants.DELIMITER);
			for (int i = 0;i<data.length;i++) {
				String cell = data[i];
				if (index==0) headers.add(cell);
				else {
					if (headers.get(i).equals("ApplicationID"))
						withdrawalReq.setApplicationID(cell);
					else if (headers.get(i).equals("Status")) {
						if (cell.equals("PENDING"))
							withdrawalReq.setStatus(WithdrawalRequestStatus.PENDING);
						else if (cell.equals("SUCCESSFUL"))
							withdrawalReq.setStatus(WithdrawalRequestStatus.SUCCESSFUL);
						else if (cell.equals("UNSUCCESSFUL"))
							withdrawalReq.setStatus(WithdrawalRequestStatus.UNSUCCESSFUL);
					}
				}
			}
			
			if (index++>0) withdrawalReqs.add(withdrawalReq);
		}
		return withdrawalReqs;
	}
}
