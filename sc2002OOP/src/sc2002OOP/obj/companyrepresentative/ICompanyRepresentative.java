package sc2002OOP.obj.companyrepresentative;

import java.util.ArrayList;
import java.util.Scanner;

import sc2002OOP.obj.internshipapplicaton.InternshipApplication;

/**
 * <h1>Company Representative Interface</h1>
 * <p>
 * This interface defines the mandatory contract for any class representing a <b>Company Representative</b> user role. It specifies all necessary methods for managing internship opportunities and handling 
 * student applications submitted to the representative's company.
 * </p>
 * @apiNote This contract requires methods for <b>internship lifecycle management</b> (creation, visibility toggle), 
 * <b>applicant approval</b>, and <b>reporting</b>. Implementations must handle all business logic related to 
 * the representative's administrative duties for their associated company.
 * @author Kee Kai Wen
 * @author Kelvin Tay Wei Jie
 * @author Koay Jun Zhi
 * @author Lim Jia Wei Jerald
 * @author Teo Kai Jie
 * @version 1.0
 * @see sc2002OOP.obj.companyrepresentative.CompanyRepresentative
 */
public interface ICompanyRepresentative {
		void createInternship(Scanner sc);
		void toggleInternshipOpportunity(Scanner sc);
		void exportApplicationsReport(Scanner sc);
		void approveRejectApplicant(Scanner sc);
		
		void displayHome(Scanner sc);
		void viewProfile(Scanner sc);
		void changePassword(Scanner sc);
		
		void print();
//		void printInternshipOpps();
		void printInternshipApps(ArrayList<InternshipApplication> iApps);
		
		String getCompanyID();
		void setCompanyID(String companyID);
		String getDepartment();
		void setDepartment(String department);
		String getPosition();
		void setPosition(String position);
		String getEmail();
		void setEmail(String email);
		CompanyRepresentativeStatus getStatus();
		void setStatus(CompanyRepresentativeStatus status);
}
