package sc2002OOP.obj.careercenterstaff;

import java.util.ArrayList;
import java.util.Scanner;

import sc2002OOP.obj.companyrepresentative.CompanyRepresentative;
import sc2002OOP.obj.internshipopportunity.InternshipOpportunity;
import sc2002OOP.obj.withdrawalrequest.WithdrawalRequest;
import sc2002OOP.obj.withdrawalrequest.WithdrawalRequestStatus;

/**
 * <h1>Career Center Staff Interface</h1>
 * <p>
 * This interface defines the mandatory <b>administrative contract</b> for any class that represents a 
 * Career Center Staff user within the IPMS. It specifies the core business logic methods related 
 * to system oversight and entity approval management.
 * </p>
 * @apiNote This contract requires methods for <b>state management</b> (<code>approveReject...</code>) 
 * for key entities and basic user functions inherited from the base user model. Implementations 
 * must provide the logic for handling pending items like <b>Company Representative registrations</b>, 
 * <b>Internship Opportunities</b>, and <b>Withdrawal Requests</b>.
 * @author Kee Kai Wen
 * @author Kelvin Tay Wei Jie
 * @author Koay Jun Zhi
 * @author Lim Jia Wei Jerald
 * @author Teo Kai Jie
 * @version 1.0
 * @see sc2002OOP.obj.careercenterstaff.CareerCenterStaff
 */
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
