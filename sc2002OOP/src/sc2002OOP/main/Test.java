package sc2002OOP.main;

import java.util.ArrayList;

import sc2002OOP.obj.careercenterstaff.CareerCenterStaff;
import sc2002OOP.obj.careercenterstaff.CareerCenterStaffManager;
import sc2002OOP.obj.companyrepresentative.CompanyRepresentative;
import sc2002OOP.obj.companyrepresentative.CompanyRepresentativeManager;
import sc2002OOP.obj.internshipapplicaton.InternshipApplication;
import sc2002OOP.obj.internshipapplicaton.InternshipApplicationManager;
import sc2002OOP.obj.internshipopportunity.InternshipOpportunity;
import sc2002OOP.obj.internshipopportunity.InternshipOpportunityManager;
import sc2002OOP.obj.student.Student;
import sc2002OOP.obj.student.StudentManager;
import sc2002OOP.obj.withdrawalrequest.WithdrawalRequest;
import sc2002OOP.obj.withdrawalrequest.WithdrawalRequestManager;

public class Test {
	public static void printAllRecords() {
		ArrayList<Student> students = StudentManager.retrieveStudents();
		ArrayList<CareerCenterStaff> staff = CareerCenterStaffManager.retrieveStaff();
		ArrayList<CompanyRepresentative> companyReps = CompanyRepresentativeManager.retrieveCompanyReps();
		ArrayList<InternshipApplication> iApps = InternshipApplicationManager.retrieveInternshipApps();
		ArrayList<InternshipOpportunity> iOpps = InternshipOpportunityManager.retrieveInternshipOpps();
		ArrayList<WithdrawalRequest> wReqs = WithdrawalRequestManager.retrieveWithdrawalReqs();
		
		System.out.println("====== CAREER CENTER STAFF TABLE ======");
		for (CareerCenterStaff st : staff) {
			st.print();
			System.out.println("-".repeat(40));
		}
		
		System.out.println("====== COMPANY REPRESENTATIVES TABLE ======");
		for (CompanyRepresentative companyRep : companyReps) {
			companyRep.print();
			System.out.println("-".repeat(40));
		}
		
		System.out.println("====== INTERNSHIP APPLICATIONS TABLE ======");
		for (InternshipApplication iApp : iApps) {
			iApp.print();
			System.out.println("-".repeat(40));
		}
		
		System.out.println("====== INTERNSHIP OPPORTUNITIES TABLE ======");
		for (InternshipOpportunity iOpp : iOpps) {
			iOpp.print();
			System.out.println("-".repeat(40));
		}
		
		System.out.println("====== STUDENTS TABLE ======");
		for (Student student : students) {
			student.print();
			System.out.println("-".repeat(40));
		}
		
		System.out.println("===== WITHDRAWAL REQUESTS TABLE ======");
		for (WithdrawalRequest wr : wReqs) {
			wr.print();
			System.out.println("-".repeat(40));
		}
	}
}
