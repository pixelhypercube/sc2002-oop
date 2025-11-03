package sc2002OOP.obj.internshipopportunity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import sc2002OOP.main.Constants;
import sc2002OOP.main.FileIOHandler;
import sc2002OOP.obj.company.CompanyManager;
import sc2002OOP.obj.companyrepresentative.*;

public class InternshipOpportunity implements IOpportunity, Serializable  {
	private static final long serialVersionUID = 7909012667627397752L;
	private String internshipID, title, description, companyID, preferredMajor;
	private ArrayList<CompanyRepresentative> companyRepsInCharge;
	private LocalDate openingDate, closingDate;
	private int numSlots;
	private InternshipOpportunityLevel level;
	private InternshipOpportunityStatus status;
	private boolean visibility;
	
	public InternshipOpportunity(
			String internshipID,
			String title, 
			String description, 
			String companyID,
			String preferredMajor, 
			ArrayList<CompanyRepresentative> companyRepsInCharge,
			int numSlots,
			InternshipOpportunityLevel level,
			InternshipOpportunityStatus status,
			LocalDate openingDate,
			LocalDate closingDate,
			boolean visibility
	) {
		this.internshipID = internshipID;
		this.title = title;
		this.description = description;
		this.companyID = companyID;
		this.preferredMajor = preferredMajor;
		this.companyRepsInCharge = companyRepsInCharge;
		this.numSlots = numSlots;
		this.level = level;
		this.status = status;
		this.openingDate = openingDate;
		this.closingDate = closingDate;
		this.visibility = visibility;
	}
	
	public InternshipOpportunity(
			String internshipID,
			String title, 
			String description, 
			String companyID,
			String preferredMajor, 
			int numSlots,
			InternshipOpportunityLevel level,
			InternshipOpportunityStatus status,
			LocalDate openingDate,
			LocalDate closingDate,
			boolean visibility
	) {
		this.internshipID = internshipID;
		this.title = title;
		this.description = description;
		this.companyID = companyID;
		this.preferredMajor = preferredMajor;
		this.numSlots = numSlots;
		this.level = level;
		this.status = status;
		this.openingDate = openingDate;
		this.closingDate = closingDate;
		this.visibility = visibility;
	}

	public InternshipOpportunity() {}
	
	public void print() {
		System.out.println("Internship ID:            "+internshipID);
		System.out.println("Title:                    "+title);
		System.out.println("Description:              "+description);
		System.out.println("Company Name:             "+CompanyManager.getCompanyByID(companyID).getCompanyName());
		System.out.println("Preferred Major(s):       "+preferredMajor);
		System.out.println("Company Rep(s) in Charge: "+CompanyRepresentativeManager
				.getCompanyReps(null, companyID, null, null, null, null)
				.stream()
				.map(rep->rep.getName() + " ("+rep.getUserID()+")")
				.collect(Collectors.joining(", ")));
//		System.out.println("Company Rep(s) in Charge: "+companyRepsInCharge.stream().map(rep->rep.getName() + " (" + rep.getUserID() +")").collect(Collectors.joining(", ")));
		System.out.println("Number of Slots:          "+numSlots);
		String levelStr = "";
		switch (level) {
			case BASIC -> levelStr = "Basic";
			case INTERMEDIATE -> levelStr = "Intermediate";
			case ADVANCED -> levelStr = "Advanced";
		}
		System.out.println("Level:                    "+levelStr);
		System.out.println("Opening Date:             "+openingDate.format(DateTimeFormatter.ISO_LOCAL_DATE));
		System.out.println("Closing Date:             "+closingDate.format(DateTimeFormatter.ISO_LOCAL_DATE));
		System.out.println("Visible to students:      "+(visibility ? "on" : "off"));
	}
	
	public void printForStudent() {
		if (visibility) {
			System.out.println("Internship ID:            "+internshipID);
			System.out.println("Title:                    "+title);
			System.out.println("Description:              "+description);
			System.out.println("Company Name:             "+CompanyManager.getCompanyByID(companyID).getCompanyName());
			System.out.println("Preferred Major(s):       "+preferredMajor);
//			System.out.println("Company Rep(s) in Charge: "+companyRepsInCharge.stream().map(rep->rep.getName() + " (" + rep.getUserID() +")").collect(Collectors.joining(", ")));
			System.out.println("Number of Slots:          "+numSlots);
			String levelStr = "";
			switch (level) {
				case BASIC -> levelStr = "Basic";
				case INTERMEDIATE -> levelStr = "Intermediate";
				case ADVANCED -> levelStr = "Advanced";
			}
			System.out.println("Level:                    "+levelStr);
			System.out.println("Opening Date:             "+openingDate.format(DateTimeFormatter.ISO_LOCAL_DATE));
			System.out.println("Closing Date:             "+closingDate.format(DateTimeFormatter.ISO_LOCAL_DATE));
		}
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

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public String getPreferredMajor() {
		return preferredMajor;
	}

	public void setPreferredMajor(String preferredMajor) {
		this.preferredMajor = preferredMajor;
	}

	public ArrayList<CompanyRepresentative> getCompanyRepsInCharge() {
		return companyRepsInCharge;
	}

	public void setCompanyRepsInCharge(ArrayList<CompanyRepresentative> companyRepInCharge) {
		this.companyRepsInCharge = companyRepInCharge;
	}

	public int getNumSlots() {
		return numSlots;
	}

	public void setNumSlots(int numSlots) {
		this.numSlots = numSlots;
	}

	public InternshipOpportunityLevel getLevel() {
		return level;
	}

	public void setLevel(InternshipOpportunityLevel level) {
		this.level = level;
	}

	public InternshipOpportunityStatus getStatus() {
		return status;
	}

	public void setStatus(InternshipOpportunityStatus status) {
		this.status = status;
	}

	public String getInternshipID() {
		return internshipID;
	}

	public void setInternshipID(String internshipID) {
		this.internshipID = internshipID;
	}

	public boolean isVisibility() {
		return visibility;
	}

	public void setVisibility(boolean visibility) {
		this.visibility = visibility;
	}

	public LocalDate getOpeningDate() {
		return openingDate;
	}

	public void setOpeningDate(LocalDate openingDate) {
		this.openingDate = openingDate;
	}

	public LocalDate getClosingDate() {
		return closingDate;
	}

	public void setClosingDate(LocalDate closingDate) {
		this.closingDate = closingDate;
	}
}
