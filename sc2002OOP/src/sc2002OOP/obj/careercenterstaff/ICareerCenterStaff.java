package sc2002OOP.obj.careercenterstaff;

import java.util.ArrayList;
import java.util.Scanner;

import sc2002OOP.obj.companyrepresentative.CompanyRepresentative;
import sc2002OOP.obj.internshipopportunity.InternshipOpportunity;
import sc2002OOP.obj.withdrawalrequest.WithdrawalRequest;
import sc2002OOP.obj.withdrawalrequest.WithdrawalRequestStatus;

public interface ICareerCenterStaff {
		void approveRejectCompRep(Scanner sc);
		void approveRejectWithdrawalRequest(Scanner sc);
		void approveRejectInternshipOpp(Scanner sc);
		
		void displayHome(Scanner sc);
		void viewProfile(Scanner sc);
		void changePassword(Scanner sc);

		void print();
		void printCompanyReps(ArrayList<CompanyRepresentative> companyReps);
		void printInternshipOpps(ArrayList<InternshipOpportunity> internshipOpps);
		void printWithdrawalReqs(ArrayList<WithdrawalRequest> withdrawalReqs);
		
		String getRole();
		void setRole(String role);
		String getDepartment();
		void setDepartment(String department);
}
