package sc2002OOP.obj.careercenterstaff;

import java.util.ArrayList;

import sc2002OOP.main.Viewer;
import sc2002OOP.obj.company.CompanyManager;
import sc2002OOP.obj.companyrepresentative.CompanyRepresentative;
import sc2002OOP.obj.companyrepresentative.CompanyRepresentativeManager;

public class CareerCenterStaffView {
	public static void print(CareerCenterStaff staff) {
		System.out.println("Staff ID:      "+staff.getUserID());
		System.out.println("Name:          "+staff.getName());
		System.out.println("Email:         "+staff.getEmail());
		System.out.println("Role:          "+staff.getRole());
		System.out.println("Department:    "+staff.getDepartment());
	}
	
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
