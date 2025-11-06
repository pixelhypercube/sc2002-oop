package sc2002OOP.obj.student;

import java.util.ArrayList;
import java.util.stream.Collectors;

import sc2002OOP.main.Viewer;
import sc2002OOP.obj.careercenterstaff.CareerCenterStaff;
import sc2002OOP.obj.careercenterstaff.CareerCenterStaffManager;
import sc2002OOP.obj.internshipapplicaton.InternshipApplication;
import sc2002OOP.obj.internshipapplicaton.InternshipApplicationManager;
import sc2002OOP.obj.internshipapplicaton.InternshipApplicationStatus;

/**
 * <h1>Student View Utility</h1>
 * <p>
 * This class provides static utility methods for displaying and formatting 
 * {@link sc2002OOP.obj.student.Student Student} data. It also handles the retrieval of associated internship application
 * information to present a comprehensive view of the student's profile and activities.
 * </p>
 * @author Kee Kai Wen
 * @author Kelvin Tay Wei Jie
 * @author Koay Jun Zhi
 * @author Lim Jia Wei Jerald
 * @author Teo Kai Jie
 * @version 1.0
 * @see sc2002OOP.obj.student.Student
 * @see sc2002OOP.main.Viewer
 */
public class StudentView {
	
	/**
     * Prints the detailed profile information for a single {@code Student} object 
     * in a line-by-line format.
     * <p>
     * Note: This method specifically lists only <b>PENDING</b> internship applications.
     * </p>
     *
     * @param student The {@code Student} object whose details are to be printed.
     */
	public static void print(Student student) {
		ArrayList<InternshipApplication> internshipApps = InternshipApplicationManager.getInternshipApps(
				"",
				student.getUserID(),
				"",
				InternshipApplicationStatus.PENDING
			);
		
		System.out.println("Student ID           : " + student.getUserID());
		System.out.println("Name                 : " + student.getName());
		System.out.println("Major                : " + student.getMajor());
		System.out.println("Year                 : " + student.getYear());
		System.out.println("Email                : " + student.getEmail());
		String internshipsApplied = internshipApps.stream().map(its->its.getInternshipID()).collect(Collectors.joining(", "));
		System.out.println("Internhip(s) Applied : " + (!internshipsApplied.isEmpty() ? internshipsApplied : "None"));
	}
	
	/**
     * Prints a formatted table listing **all students** currently managed by the 
     * {@code StudentManager}.
     * <p>
     * The table includes: Student ID, Name, Major, Year, Email, and a comma-separated list of 
     * all internship IDs the student has applied for (regardless of status).
     * </p>
     *
     * @see #printTable(ArrayList)
     */
	public static void printTable() {
		ArrayList<String> headers = new ArrayList<>();
		headers.add("Student ID");
		headers.add("Name");
		headers.add("Major");
		headers.add("Year");
		headers.add("Email");
		headers.add("Internship(s) Applied");
		
		ArrayList<ArrayList<String>> data = new ArrayList<>();
		for (Student student : StudentManager.getStudents()) {
			ArrayList<InternshipApplication> internshipApps = InternshipApplicationManager.getInternshipApps(
					"",
					student.getUserID(),
					"",
					null
				);
			ArrayList<String> rec = new ArrayList<String>();
			rec.add(student.getUserID());
			rec.add(student.getName());
			rec.add(student.getMajor());
			rec.add(Integer.toString(student.getYear()));
			rec.add(student.getEmail());
			String internshipsApplied = internshipApps.stream().map(its->its.getInternshipID()).collect(Collectors.joining(", "));
			rec.add(!internshipsApplied.isEmpty() ? internshipsApplied : "(None)");
			data.add(rec);
		}
		
		Viewer.printTable(headers, data);
	}
	
	/**
     * Prints a formatted table listing a **specific subset of students** provided in an {@code ArrayList}.
     * <p>
     * The table includes: Student ID, Name, Major, Year, Email, and a comma-separated list of 
     * all internship IDs the student has applied for (regardless of status).
     * </p>
     *
     * @param students An {@code ArrayList} of {@code Student} objects to be displayed.
     * @see #printTable()
     */
	public static void printTable(ArrayList<Student> students) {
		ArrayList<String> headers = new ArrayList<>();
		headers.add("Student ID");
		headers.add("Name");
		headers.add("Major");
		headers.add("Year");
		headers.add("Email");
		headers.add("Internship(s) Applied");
		
		ArrayList<ArrayList<String>> data = new ArrayList<>();
		for (Student student : students) {
			ArrayList<InternshipApplication> internshipApps = InternshipApplicationManager.getInternshipApps(
					"",
					student.getUserID(),
					"",
					null
				);
			ArrayList<String> rec = new ArrayList<String>();
			rec.add(student.getUserID());
			rec.add(student.getName());
			rec.add(student.getMajor());
			rec.add(Integer.toString(student.getYear()));
			rec.add(student.getEmail());
			String internshipsApplied = internshipApps.stream().map(its->its.getInternshipID()).collect(Collectors.joining(", "));
			rec.add(!internshipsApplied.isEmpty() ? internshipsApplied : "(None)");
			data.add(rec);
		}
		
		Viewer.printTable(headers, data);
	}
}
