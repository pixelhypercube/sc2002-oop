package sc2002OOP.obj.internshipapplicaton;

import java.util.ArrayList;

import sc2002OOP.main.Viewer;


/**
 * <h1>Internship Application View Class</h1>
 * <p>
 * This class provides **static utility methods** for displaying information about 
 * {@code InternshipApplication} objects in a structured, user-friendly format, typically a table.
 * </p>
 * <p>
 * It works in conjunction with {@code InternshipApplicationManager} to retrieve data
 * and utilizes the {@code Viewer} class to handle the formatting and printing of tables.
 * </p>
 *
 * @author Kee Kai Wen
 * @author Kelvin Tay Wei Jie
 * @author Koay Jun Zhi
 * @author Lim Jia Wei Jerald
 * @author Teo Kai Jie
 * @version 1.0
 * @see sc2002OOP.main.Viewer
 * @see sc2002OOP.obj.internshipapplicaton.InternshipApplication
 * @see sc2002OOP.obj.internshipapplicaton.InternshipApplicationManager
 */
public class InternshipApplicationView {
	public void print(InternshipApplication iApp) {
		System.out.println("Application ID: " + iApp.getApplicationID());
		System.out.println("Student ID:     " + iApp.getStudentID());
		System.out.println("Internship ID:  " + iApp.getInternshipID());
		System.out.println("Status:         " + iApp.getStatus());
	}
	
	/**
     * Prints a formatted table listing **all Internship Applications** currently managed by 
     * the {@code InternshipApplicationManager}.
     * <p>
     * The table includes the following columns: **Application ID, Internship ID, and Status.**
     * </p>
     * <p>
     * Data is retrieved using {@code InternshipApplicationManager.getInternshipApps()} and
     * displayed via {@code Viewer.printTable()}.
     * </p>
     *
     * @see #printInternshipAppTable(ArrayList)
     */
	public static void printTable() {
		ArrayList<String> headers = new ArrayList<>();
		headers.add("Application ID");
//		headers.add("Student ID");
		headers.add("Internship ID");
		headers.add("Status: ");
		
		ArrayList<ArrayList<String>> data = new ArrayList<>();
		for (InternshipApplication iApp : InternshipApplicationManager.getInternshipApps()) {
			ArrayList<String> rec = new ArrayList<String>();
			rec.add(iApp.getApplicationID());
//			rec.add(iApp.getStudentID());
			rec.add(iApp.getInternshipID());
			rec.add(iApp.getStatus().toString());
			data.add(rec);
		}
		Viewer.printTable(headers, data);
	}
	
	/**
     * Prints a formatted table listing a **specific subset of Internship Applications** provided 
     * in an {@code ArrayList}.
     * <p>
     * The table includes the following columns: **Application ID, Internship ID, and Status.**
     * </p>
     *
     * @param iApps An {@code ArrayList} of {@code InternshipApplication} objects to be displayed.
     * @see #printInternshipAppTable()
     */
	public static void printTable(ArrayList<InternshipApplication> iApps) {
		ArrayList<String> headers = new ArrayList<>();
		headers.add("Application ID");
//		headers.add("Student ID");
		headers.add("Internship ID");
		headers.add("Status: ");
		
		ArrayList<ArrayList<String>> data = new ArrayList<>();
		for (InternshipApplication iApp : iApps) {
			ArrayList<String> rec = new ArrayList<String>();
			rec.add(iApp.getApplicationID());
//			rec.add(iApp.getStudentID());
			rec.add(iApp.getInternshipID());
			rec.add(iApp.getStatus().toString());
			data.add(rec);
		}
		Viewer.printTable(headers, data);
	}
}
