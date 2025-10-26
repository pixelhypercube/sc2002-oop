package sc2002OOP.obj;

import java.util.ArrayList;
import java.util.Date;

public class Internship {
	/*
	 * Attributes: Internship Title , Description , Level (Basic, Intermediate, Advanced) , Preferred Majors , Application dates , Status (Pending, Approved, Rejected, Filled) , Company Name , Representative in charge , Number of slots (max 10) , Visibility (on/off).
	 * */
	private String title, description, companyName;
	private ArrayList<String> preferredMajors, companyRepInCharge;
	private Date applicationOpeningDate, applicationClosingDate;
	private int numSlots;
	private enum Level {
		BASIC,
		INTERMEDIATE,
		ADVANCED
	};
	private enum Status {
		PENDING,
		APPROVED,
		REJECTED,
		FILLED
	}
	private Level level;
	private Status status;
	
	public Internship(String title, String description, String companyName) {
		this.title = title;
		this.description = description;
		this.companyName = companyName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	
}
