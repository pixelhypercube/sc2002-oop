package sc2002OOP.obj.companyrepresentative;

import java.util.ArrayList;
import java.util.Scanner;

import sc2002OOP.obj.internshipapplicaton.InternshipApplication;

public interface ICompanyRepresentative {
		void createInternship(Scanner sc);
		void toggleInternshipOpportunity(Scanner sc);
		void exportApplicationsReport();
		void approveRejectApplicant(Scanner sc);
		
		void displayHome(Scanner sc);
		void viewProfile(Scanner sc);
		void changePassword(Scanner sc);
		
		void print();
		void printInternshipOpps();
		void printInternshipApps(ArrayList<InternshipApplication> iApps);
		
		String getCompanyName();
		void setCompanyName(String companyName);
		String getDepartment();
		void setDepartment(String department);
		String getPosition();
		void setPosition(String position);
		String getEmail();
		void setEmail(String email);
		CompanyRepresentativeStatus getStatus();
		void setStatus(CompanyRepresentativeStatus status);
}
