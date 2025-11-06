package sc2002OOP.obj.companyrepresentative;

import java.util.ArrayList;

import sc2002OOP.main.Viewer;
import sc2002OOP.obj.company.Company;
import sc2002OOP.obj.company.CompanyManager;
import sc2002OOP.obj.internshipapplicaton.InternshipApplication;
import sc2002OOP.obj.internshipapplicaton.InternshipApplicationManager;

/**
 * <h1>Company Representative View Class</h1>
 * <p>
 * This class provides <b>static utility methods</b> for displaying information related to
 * {@link sc2002OOP.obj.companyrepresentative.CompanyRepresentative CompanyRepresentative} users in a structured, readable format, typically a table.
 * </p>
 * <p>
 * It interacts with {@link sc2002OOP.obj.companyrepresentative.CompanyRepresentativeManager CompanyRepresentativeManager} to retrieve user data and 
 * {@link sc2002OOP.obj.company.CompanyManager CompanyManager} to resolve the representative's company name based on their ID.
 * It uses the {@link sc2002OOP.main.Viewer Viewer} class for final table rendering.
 * </p>
 *
 * @author Kee Kai Wen
 * @author Kelvin Tay Wei Jie
 * @author Koay Jun Zhi
 * @author Lim Jia Wei Jerald
 * @author Teo Kai Jie
 * @version 1.0
 * @see sc2002OOP.main.Viewer
 * @see sc2002OOP.obj.companyrepresentative.CompanyRepresentative
 * @see sc2002OOP.obj.companyrepresentative.CompanyRepresentativeManager
 * @see sc2002OOP.obj.company.CompanyManager
 */
public class CompanyRepresentativeView {
	public static void print(CompanyRepresentative cr) {
		System.out.println("Email:          "+cr.getUserID());
		System.out.println("Name:           "+cr.getName());
		System.out.println("Company Name:   "+CompanyManager.getCompanyByID(cr.getCompanyID()).getCompanyName());
		System.out.println("Department:     "+cr.getDepartment());
		System.out.println("Position:       "+cr.getPosition());
		System.out.println("Status:         "+cr.getStatus());
	}
	
	/**
     * Prints a formatted table listing **all Company Representatives** currently managed by 
     * the {@code CompanyRepresentativeManager}.
     * <p>
     * The table includes: **Email, Name, Company Name, Department, Position, and Status.**
     * </p>
     * <p>
     * It retrieves the company name for each representative by using their {@code CompanyID} 
     * to query the {@code CompanyManager}.
     * </p>
     *
     * @see #printCompanyRepTable(ArrayList)
     */
	public static void printTable() {
		ArrayList<String> headers = new ArrayList<>();
		headers.add("Email");
		headers.add("Name");
		headers.add("Company Name");
		headers.add("Department");
		headers.add("Position");
		headers.add("Status");

		ArrayList<ArrayList<String>> data = new ArrayList<>();
		for (CompanyRepresentative companyRep : CompanyRepresentativeManager.getCompanyReps()) {
			ArrayList<String> rec = new ArrayList<>();
			rec.add(companyRep.getUserID());
			rec.add(companyRep.getName());
			String companyName;
	        Company company = CompanyManager.getCompanyByID(companyRep.getCompanyID());
	        if (company != null) {
	            companyName = company.getCompanyName() != null ? company.getCompanyName() : "N/A";
	        } else {
	            companyName = "Company Not Found";
	        }
            rec.add(companyName);
			rec.add(companyRep.getDepartment());
			rec.add(companyRep.getPosition());
			rec.add(companyRep.getStatus().toString());
			data.add(rec);
		}
		
		Viewer.printTable(headers, data);
	}
	
	/**
     * Prints a formatted table listing a **specific subset of Company Representatives** provided 
     * in an {@code ArrayList}.
     * <p>
     * The table includes: **Email, Name, Company Name, Department, Position, and Status.**
     * </p>
     * <p>
     * It retrieves the company name for each representative by using their {@code CompanyID} 
     * to query the {@code CompanyManager}.
     * </p>
     *
     * @param companyReps An {@code ArrayList} of {@code CompanyRepresentative} objects to be displayed.
     * @see #printCompanyRepTable()
     */
	public static void printTable(ArrayList<CompanyRepresentative> companyReps) {
		ArrayList<String> headers = new ArrayList<>();
		headers.add("Email");
		headers.add("Name");
		headers.add("Company Name");
		headers.add("Department");
		headers.add("Position");
		headers.add("Status");

		ArrayList<ArrayList<String>> data = new ArrayList<>();
		for (CompanyRepresentative companyRep : companyReps) {
			ArrayList<String> rec = new ArrayList<>();
			rec.add(companyRep.getUserID());
			rec.add(companyRep.getName());
			String companyName;
	        Company company = CompanyManager.getCompanyByID(companyRep.getCompanyID());
	        if (company != null) {
	            companyName = company.getCompanyName() != null ? company.getCompanyName() : "N/A";
	        } else {
	            companyName = "Company Not Found";
	        }
            rec.add(companyName);
			rec.add(companyRep.getDepartment());
			rec.add(companyRep.getPosition());
			rec.add(companyRep.getStatus().toString());
			data.add(rec);
		}
		Viewer.printTable(headers, data);
	}
}
