package sc2002OOP.obj.withdrawalrequest;

import java.util.ArrayList;

import sc2002OOP.main.Viewer;
import sc2002OOP.obj.company.Company;
import sc2002OOP.obj.company.CompanyManager;
import sc2002OOP.obj.internshipapplicaton.InternshipApplication;
import sc2002OOP.obj.internshipapplicaton.InternshipApplicationManager;
import sc2002OOP.obj.internshipopportunity.InternshipOpportunity;
import sc2002OOP.obj.internshipopportunity.InternshipOpportunityManager;
import sc2002OOP.obj.student.Student;
import sc2002OOP.obj.student.StudentManager;

/**
 * <h1>Withdrawal Request View Class</h1>
 * <p>
 * This class provides <b>static utility methods</b> for displaying information regarding 
 * {@link sc2002OOP.obj.withdrawalrequest.WithdrawalRequest WithdrawalRequest} objects in a structured, table format for the user interface.
 * </p>
 * <p>
 * It works with {@link sc2002OOP.obj.withdrawalrequest.WithdrawalRequestManager WithdrawalRequestManager} to fetch the necessary data and 
 * relies on the {@link sc2002OOP.main.Viewer Viewer} class to handle the formatting and printing of the tables.
 * </p>
 *
 * @author Kee Kai Wen
 * @author Kelvin Tay Wei Jie
 * @author Koay Jun Zhi
 * @author Lim Jia Wei Jerald
 * @author Teo Kai Jie
 * @version 1.0
 * @see sc2002OOP.main.Viewer
 * @see sc2002OOP.obj.withdrawalrequest.WithdrawalRequest
 * @see sc2002OOP.obj.withdrawalrequest.WithdrawalRequestManager
 */
public class WithdrawalRequestView {
	public static void print(WithdrawalRequest wr) {
		System.out.println("Application ID:    " + wr.getApplicationID());
		System.out.println("Withdrawal Status: " + wr.getStatus());
	}
	
	/**
     * Prints a formatted table listing **all Withdrawal Requests** currently managed by 
     * the {@code WithdrawalRequestManager}.
     * <p>
     * The table includes the following columns: **Application ID** (referencing the internship application) 
     * and the request's <b>Status</b>.
     * </p>
     * <p>
     * Data is retrieved using {@code WithdrawalRequestManager.getWithdrawalReqs()} and
     * displayed via {@code Viewer.printTable()}.
     * </p>
     *
     * @see #printWithdrawalReqTable(ArrayList)
     */
	public static void printTable() {
		ArrayList<String> headers = new ArrayList<>();
		headers.add("Application ID");
		headers.add("Student Name");
		headers.add("Internship Title/Company");
		headers.add("Current App. Status");
		headers.add("Withdrawal Req. Status");
		
		ArrayList<ArrayList<String>> data = new ArrayList<>();
		for (WithdrawalRequest wReq : WithdrawalRequestManager.getWithdrawalReqs()) {
			ArrayList<String> rec = new ArrayList<String>();
			
			InternshipApplication app = InternshipApplicationManager.getInternshipAppByID(wReq.getApplicationID());
			
			if (app == null) continue;
			
			// 2. Get Student Details
	        Student student = StudentManager.getStudentByID(app.getStudentID());
	        String studentName = (student != null) ? student.getName() : "Unknown Student";

	        // internship details
	        InternshipOpportunity iOpp = InternshipOpportunityManager.getInternshipOppByID(app.getInternshipID());
	        String internshipTitleAndCompany = "Unknown Internship"; // Default value

	        if (iOpp != null) {
	            // Get Company Name (Assumes CompanyManager.getCompanyByID and Company.getCompanyName exist)
	            Company company = CompanyManager.getCompanyByID(iOpp.getCompanyID());
	            String companyName = (company != null) ? company.getCompanyName() : "Unknown Company";
	            
	            // Format the combined string for display
	            internshipTitleAndCompany = iOpp.getTitle() + " @ " + companyName;
	        }
					
	        rec.add(wReq.getApplicationID());
	        rec.add(studentName);
	        rec.add(internshipTitleAndCompany);
			rec.add(app.getStatus().toString());
			rec.add(wReq.getStatus().toString());
			data.add(rec);
		}
		Viewer.printTable(headers, data);
	}
	
	/**
     * Prints a formatted table listing a <b>specific subset of Withdrawal Requests</b> provided 
     * in an {@code ArrayList}.
     * <p>
     * The table includes the following columns: <b>Application ID</b> and the request's <b>Status</b>.
     * </p>
     *
     * @param wReqs An {@code ArrayList} of {@code WithdrawalRequest} objects to be displayed.
     * @see #printWithdrawalReqTable()
     */
	public static void printTable(ArrayList<WithdrawalRequest> wReqs) {
		ArrayList<String> headers = new ArrayList<>();
		headers.add("Application ID");
		headers.add("Student Name");
		headers.add("Internship Title/Company");
		headers.add("Current App. Status");
		headers.add("Withdrawal Req. Status");
		
		ArrayList<ArrayList<String>> data = new ArrayList<>();
		for (WithdrawalRequest wReq : wReqs) {
			ArrayList<String> rec = new ArrayList<String>();
			
			InternshipApplication app = InternshipApplicationManager.getInternshipAppByID(wReq.getApplicationID());
			
			if (app == null) continue;
			
			// 2. Get Student Details
	        Student student = StudentManager.getStudentByID(app.getStudentID());
	        String studentName = (student != null) ? student.getName() : "Unknown Student";

	        // internship details
	        InternshipOpportunity iOpp = InternshipOpportunityManager.getInternshipOppByID(app.getInternshipID());
	        String internshipTitleAndCompany = "Unknown Internship"; // Default value

	        if (iOpp != null) {
	            // Get Company Name (Assumes CompanyManager.getCompanyByID and Company.getCompanyName exist)
	            Company company = CompanyManager.getCompanyByID(iOpp.getCompanyID());
	            String companyName = (company != null) ? company.getCompanyName() : "Unknown Company";
	            
	            // Format the combined string for display
	            internshipTitleAndCompany = iOpp.getTitle() + " @ " + companyName;
	        }
					
	        rec.add(wReq.getApplicationID());
	        rec.add(studentName);
	        rec.add(internshipTitleAndCompany);
			rec.add(app.getStatus().toString());
			rec.add(wReq.getStatus().toString());
			data.add(rec);
		}
		Viewer.printTable(headers, data);
	}
}
