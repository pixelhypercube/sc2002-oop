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
 * {@code Student} data. It handles the retrieval of associated internship application
 * information to present a comprehensive view of the student's profile and activities.
 * </p>
 * @apiNote This class utilizes the {@code Viewer} utility for consistent table formatting 
 * and interacts with the {@code InternshipApplicationManager} to fetch application data.
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
