package sc2002OOP.obj;

import java.util.ArrayList;

public class Student extends User {
	private String major;
	private int year;
	private ArrayList<Internship> internships;
	
	public Student() {};
	
	public Student(String userID, String name, String major, int year, String email, String password) {
		super(userID,name,email,password);
		this.major = major;
		this.year = year;
	}
	
	public void viewInternshipOpportunities() {
		System.out.println(super.getName()+"'s list of Internship Opportunities:");
		for (Internship internship : internships) {
			System.out.println("Company Name: "+internship.getCompanyName());
			System.out.println("Title: "+internship.getTitle());
			System.out.println("Description: "+internship.getDescription());
		}
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}
	
	
}
