package sc2002OOP.obj.internshipopportunity;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.stream.Collectors;

import sc2002OOP.main.Viewer;
import sc2002OOP.obj.company.Company;
import sc2002OOP.obj.company.CompanyManager;
import sc2002OOP.obj.companyrepresentative.CompanyRepresentativeManager;

/**
 * <h1>Internship Opportunity View Class</h1>
 * <p>
 * This class provides <b>static utility methods</b> for displaying lists of 
 * {@link sc2002OOP.obj.internshipopportunity.InternshipOpportunity InternshipOpportunity} objects in a user-friendly format on the console.
 * </p>
 * <p>
 * Unlike table views, this class iterates through the list and calls the individual 
 * {@code print()} method implemented within the {@link sc2002OOP.obj.internshipopportunity.InternshipOpportunity InternshipOpportunity} class 
 * for detailed, one-by-one listing.
 * </p>
 *
 * @author Kee Kai Wen
 * @author Kelvin Tay Wei Jie
 * @author Koay Jun Zhi
 * @author Lim Jia Wei Jerald
 * @author Teo Kai Jie
 * @version 1.0
 * @see sc2002OOP.obj.internshipopportunity.InternshipOpportunity
 * @see sc2002OOP.obj.internshipopportunity.InternshipOpportunityManager
 */
public class InternshipOpportunityView {
	
	/**
     * Prints the complete, detailed information for a single {@code InternshipOpportunity} 
     * in a line-by-line format. This method includes administrative details like <b>Visibility</b>.
     *
     * @param iOpp The {@code InternshipOpportunity} object to print.
     */
	public static void print(InternshipOpportunity iOpp) {
		System.out.println("Internship ID:            "+iOpp.getInternshipID());
		System.out.println("Title:                    "+iOpp.getTitle());
		System.out.println("Description:              "+iOpp.getDescription());
		String companyName;
        Company company = CompanyManager.getCompanyByID(iOpp.getCompanyID());
        if (company != null) {
            companyName = company.getCompanyName() != null ? company.getCompanyName() : "N/A";
        } else {
            companyName = "(Company Not Found)";
        }
		System.out.println("Company Name:             "+companyName);
		System.out.println("Preferred Major(s):       "+iOpp.getPreferredMajor());
		
		String companyReps = CompanyRepresentativeManager
				.getCompanyReps(null, iOpp.getCompanyID(), null, null, null, null)
				.stream()
				.map(rep->rep.getName() + " ("+rep.getUserID()+")")
				.collect(Collectors.joining(", "));
		
		System.out.println("Company Rep(s) in Charge: "+(!companyReps.isEmpty() ? companyReps : "(None)"));
		System.out.println("Number of Slots:          "+iOpp.getNumSlots());
		String levelStr = "";
		switch (iOpp.getLevel()) {
			case BASIC -> levelStr = "Basic";
			case INTERMEDIATE -> levelStr = "Intermediate";
			case ADVANCED -> levelStr = "Advanced";
		}
		System.out.println("Level:                    "+levelStr);
		System.out.println("Opening Date:             "+iOpp.getOpeningDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
		System.out.println("Closing Date:             "+iOpp.getClosingDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
		System.out.println("Status:                   "+iOpp.getStatus());
		System.out.println("Visible to students:      "+(iOpp.isVisibility() ? "on" : "off"));
	}
	
	/**
     * Prints the detailed information for a single {@code InternshipOpportunity} 
     * tailored for <b>student viewing</b>. Administrative fields like <b>Visibility</b> are omitted.
     *
     * @param iOpp The {@code InternshipOpportunity} object to print.
     */
	public static void printForStudent(InternshipOpportunity iOpp) {
		System.out.println("Internship ID:            "+iOpp.getInternshipID());
		System.out.println("Title:                    "+iOpp.getTitle());
		System.out.println("Description:              "+iOpp.getDescription());
		String companyName;
        Company company = CompanyManager.getCompanyByID(iOpp.getCompanyID());
        if (company != null) {
            companyName = company.getCompanyName() != null ? company.getCompanyName() : "N/A";
        } else {
            companyName = "(Company Not Found)";
        }
		System.out.println("Company Name:             "+companyName);
		System.out.println("Preferred Major(s):       "+iOpp.getPreferredMajor());
		String companyReps = CompanyRepresentativeManager
				.getCompanyReps(null, iOpp.getCompanyID(), null, null, null, null)
				.stream()
				.map(rep->rep.getName() + " ("+rep.getUserID()+")")
				.collect(Collectors.joining(", "));
		
		System.out.println("Company Rep(s) in Charge: "+(!companyReps.isEmpty() ? companyReps : "(None)"));
		System.out.println("Number of Slots:          "+iOpp.getNumSlots());
		String levelStr = "";
		switch (iOpp.getLevel()) {
			case BASIC -> levelStr = "Basic";
			case INTERMEDIATE -> levelStr = "Intermediate";
			case ADVANCED -> levelStr = "Advanced";
		}
		System.out.println("Level:                    "+levelStr);
		System.out.println("Opening Date:             "+iOpp.getOpeningDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
		System.out.println("Closing Date:             "+iOpp.getClosingDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
		System.out.println("Status:                   "+iOpp.getStatus());
		//		System.out.println("Visible to students:      "+(iOpp.isVisibility() ? "on" : "off"));
	}
	
	/**
     * Prints the detailed information for all internship opportunities managed in the system,
     * separated by a horizontal line. Uses the administrative {@code print()} method.
     */
	public static void printList() {
		for (InternshipOpportunity iOpp : InternshipOpportunityManager.getInternshipOpps()) {
			print(iOpp);
			System.out.println("-".repeat(40));
		}
	}
	
	/**
     * Prints the detailed information for a specific subset of internship opportunities,
     * separated by a horizontal line. Uses the administrative {@code print()} method.
     *
     * @param internshipOpps The {@code ArrayList} of opportunities to display.
     */
	public static void printList(ArrayList<InternshipOpportunity> internshipOpps) {
		for (InternshipOpportunity iOpp : internshipOpps) {
			print(iOpp);
			System.out.println("-".repeat(40));
		}
	}
	
	/**
     * Prints the detailed, student-friendly information for all internship opportunities managed 
     * in the system, separated by a horizontal line. Uses the {@code printForStudent()} method.
     */
	public static void printForStudentList() {
		for (InternshipOpportunity iOpp : InternshipOpportunityManager.getInternshipOpps()) {
			printForStudent(iOpp);
			System.out.println("-".repeat(40));
		}
	}
	
	/**
     * Prints the detailed, student-friendly information for a specific subset of internship opportunities,
     * separated by a horizontal line. Uses the {@code printForStudent()} method.
     *
     * @param internshipOpps The {@code ArrayList} of opportunities to display.
     */
	public static void printForStudentList(ArrayList<InternshipOpportunity> internshipOpps) {
		for (InternshipOpportunity iOpp : internshipOpps) {
			printForStudent(iOpp);
			System.out.println("-".repeat(40));
		}
	}
	
	// TABLES
	
	/**
     * Prints a formatted table listing all internship opportunities, focusing only on the 
     * <b>ID, Title, and Visibility Status</b>.
     */
	public static void printVisibilityTable() {
		ArrayList<String> headers = new ArrayList<>();
		headers.add("Internship ID");
		headers.add("Title");
		headers.add("Visible to Students");
		
		ArrayList<ArrayList<String>> data = new ArrayList<>();
		for (InternshipOpportunity iOpp : InternshipOpportunityManager.getInternshipOpps()) {
			ArrayList<String> rec = new ArrayList<>();
			rec.add(iOpp.getInternshipID());
			rec.add(iOpp.getTitle());
			rec.add((iOpp.isVisibility() ? "on" : "off"));
			data.add(rec);
		}
		Viewer.printTable(headers, data);
	}
	
	/**
     * Prints a formatted table listing a specific subset of internship opportunities, 
     * focusing only on the <b>ID, Title, and Visibility Status</b>.
     *
     * @param iOpps The {@code ArrayList} of opportunities to display.
     */
	public static void printVisibilityTable(ArrayList<InternshipOpportunity> iOpps) {
		ArrayList<String> headers = new ArrayList<>();
		headers.add("Internship ID");
		headers.add("Title");
		headers.add("Visible to Students");
		
		ArrayList<ArrayList<String>> data = new ArrayList<>();
		for (InternshipOpportunity iOpp : iOpps) {
			ArrayList<String> rec = new ArrayList<>();
			rec.add(iOpp.getInternshipID());
			rec.add(iOpp.getTitle());
			rec.add((iOpp.isVisibility() ? "on" : "off"));
			data.add(rec);
		}
		Viewer.printTable(headers, data);
	}
}
