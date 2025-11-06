package sc2002OOP.obj.careercenterstaff;

import java.util.ArrayList;

import sc2002OOP.main.Viewer;
import sc2002OOP.obj.company.CompanyManager;
import sc2002OOP.obj.companyrepresentative.CompanyRepresentative;
import sc2002OOP.obj.companyrepresentative.CompanyRepresentativeManager;

/**
 * <h1>Career Center Staff View</h1>
 * <p>
 * This class provides **static utility methods** that format and print information related to
 * the {@link sc2002OOP.obj.careercenterstaff.CareerCenterStaff CareerCenterStaff} role,
 * either as a single detailed view or as a list in a formatted table.
 * </p>
 * @author Kee Kai Wen
 * @author Kelvin Tay Wei Jie
 * @author Koay Jun Zhi
 * @author Lim Jia Wei Jerald
 * @author Teo Kai Jie
 * @version 1.0
 * @see sc2002OOP.obj.careercenterstaff.CareerCenterStaff
 * @see sc2002OOP.main.Constants
 * @see sc2002OOP.main.Viewer
 */
public class CareerCenterStaffView {
	
	/**
     * Prints the detailed personal information of a single Career Center Staff member to the console.
     * The output includes Staff ID, Name, Email, Role, and Department, each on a new line.
     *
     * @param staff The {@code CareerCenterStaff} object whose details are to be printed.
     */
	public static void print(CareerCenterStaff staff) {
		System.out.println("Staff ID:      "+staff.getUserID());
		System.out.println("Name:          "+staff.getName());
		System.out.println("Email:         "+staff.getEmail());
		System.out.println("Role:          "+staff.getRole());
		System.out.println("Department:    "+staff.getDepartment());
	}
	
	/**
     * Retrieves all registered Career Center Staff members via {@code CareerCenterStaffManager}
     * and prints their details in a formatted table using the {@link sc2002OOP.main.Viewer Viewer} class.
     *
     * The table includes columns for Staff ID, Name, Email, Role, and Department.
     * This method is useful for viewing the entire list of staff members.
     */
	public static void printTable() {
		ArrayList<String> headers = new ArrayList<>();
		headers.add("Staff ID");
		headers.add("Name");
		headers.add("Email");
		headers.add("Role");
		headers.add("Department");
		
		ArrayList<ArrayList<String>> data = new ArrayList<>();
		for (CareerCenterStaff staff : CareerCenterStaffManager.getStaff()) {
			ArrayList<String> rec = new ArrayList<String>();
			rec.add(staff.getUserID());
			rec.add(staff.getName());
			rec.add(staff.getEmail());
			rec.add(staff.getRole());
			rec.add(staff.getDepartment());
			data.add(rec);
		}
		
		Viewer.printTable(headers, data);
	}
	
	/**
     * Prints the details of a specific list of Career Center Staff members in a formatted table.
     *
     * @param careerCenterStaffList The {@code ArrayList} of {@code CareerCenterStaff} objects to be displayed.
     */
	public static void printTable(ArrayList<CareerCenterStaff> careerCenterStaffList) {
		ArrayList<String> headers = new ArrayList<>();
		headers.add("Staff ID");
		headers.add("Name");
		headers.add("Email");
		headers.add("Role");
		headers.add("Department");
		
		ArrayList<ArrayList<String>> data = new ArrayList<>();
		for (CareerCenterStaff staff : careerCenterStaffList) {
			ArrayList<String> rec = new ArrayList<String>();
			rec.add(staff.getUserID());
			rec.add(staff.getName());
			rec.add(staff.getEmail());
			rec.add(staff.getRole());
			rec.add(staff.getDepartment());
			data.add(rec);
		}
		
		Viewer.printTable(headers, data);
	}
}
