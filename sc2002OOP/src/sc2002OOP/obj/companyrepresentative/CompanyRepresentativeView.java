package sc2002OOP.obj.companyrepresentative;

import java.util.ArrayList;

import sc2002OOP.main.Viewer;
import sc2002OOP.obj.company.CompanyManager;
import sc2002OOP.obj.internshipapplicaton.InternshipApplication;
import sc2002OOP.obj.internshipapplicaton.InternshipApplicationManager;

/**
 * <h1>Company Representative View Class</h1>
 * <p>
 * This class provides **static utility methods** for displaying information related to
 * {@code CompanyRepresentative} users in a structured, readable format, typically a table.
 * </p>
 * <p>
 * It interacts with {@code CompanyRepresentativeManager} to retrieve user data and 
 * {@code CompanyManager} to resolve the representative's company name based on their ID.
 * It uses the {@code Viewer} class for final table rendering.
 * </p>
 *
 * @author (Assumed Authors from Project)
 * @version 1.0
 * @see sc2002OOP.main.Viewer
 * @see sc2002OOP.obj.companyrepresentative.CompanyRepresentative
 * @see sc2002OOP.obj.companyrepresentative.CompanyRepresentativeManager
 * @see sc2002OOP.obj.company.CompanyManager
 */
public class CompanyRepresentativeView {
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
	public static void printCompanyRepTable() {
		ArrayList<String> headers = new ArrayList<>();
		headers.add("Email");
		headers.add("Name");
		headers.add("Company Name");
		headers.add("Department");
		headers.add("Position");
		headers.add("Status");

		ArrayList<ArrayList<String>> data = new ArrayList<>();
		for (CompanyRepresentative companyRep : CompanyRepresentativeManager.getCompanyReps()) {
			ArrayList<String> rec = new ArrayList<String>();
			rec.add(companyRep.getEmail());
			rec.add(CompanyManager.getCompanyByID(companyRep.getCompanyID()).getCompanyName());
			rec.add(companyRep.getName());
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
	public static void printCompanyRepTable(ArrayList<CompanyRepresentative> companyReps) {
		ArrayList<String> headers = new ArrayList<>();
		headers.add("Email");
		headers.add("Name");
		headers.add("Company Name");
		headers.add("Department");
		headers.add("Position");
		headers.add("Status");

		ArrayList<ArrayList<String>> data = new ArrayList<>();
		for (CompanyRepresentative companyRep : companyReps) {
			ArrayList<String> rec = new ArrayList<String>();
			rec.add(companyRep.getEmail());
			rec.add(CompanyManager.getCompanyByID(companyRep.getCompanyID()).getCompanyName());
			rec.add(companyRep.getName());
			rec.add(companyRep.getDepartment());
			rec.add(companyRep.getPosition());
			rec.add(companyRep.getStatus().toString());
			data.add(rec);
		}
		
		Viewer.printTable(headers, data);
		
	}
}
