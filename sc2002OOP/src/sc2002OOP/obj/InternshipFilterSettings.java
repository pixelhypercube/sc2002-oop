package sc2002OOP.obj;

import java.io.Serializable;
import java.time.LocalDate;

import sc2002OOP.obj.internshipopportunity.InternshipOpportunityLevel;
import sc2002OOP.obj.internshipopportunity.InternshipOpportunityStatus;

public class InternshipFilterSettings implements Serializable {
	private static final long serialVersionUID = -5163111704314269118L;
	private String internshipID;
	private String title;
	private String description;
	private String companyID;
	private String preferredMajors;
	private InternshipOpportunityLevel level;
	private InternshipOpportunityStatus status;
	private Boolean visibility;
	private LocalDate openingDateFrom;
	private LocalDate openingDateTo;
	private LocalDate closingDateFrom;
	private LocalDate closingDateTo;
	
	public InternshipFilterSettings() {}
	
	public InternshipFilterSettings(String internshipID,
			String title,
			String description,
			String companyID,
			String preferredMajors,
			InternshipOpportunityLevel level,
			InternshipOpportunityStatus status,
			Boolean visibility,
			LocalDate openingDateFrom,
	        LocalDate openingDateTo,
	        LocalDate closingDateFrom,
	        LocalDate closingDateTo) {
		this.title = title;
		this.description = description;
		this.companyID = companyID;
		this.preferredMajors = preferredMajors;
		this.level = level;
		this.status = status;
		this.visibility = visibility;
		this.openingDateFrom = openingDateFrom;
		this.openingDateTo = openingDateTo;
		this.closingDateFrom = closingDateFrom;
		this.closingDateTo = closingDateTo;
	}
	
	public String getInternshipID() {
		return internshipID;
	}

	public void setInternshipID(String internshipID) {
		this.internshipID = internshipID;
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

	public String getPreferredMajors() {
		return preferredMajors;
	}

	public void setPreferredMajors(String preferredMajors) {
		this.preferredMajors = preferredMajors;
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

	public Boolean getVisibility() {
		return visibility;
	}

	public void setVisibility(Boolean visibility) {
		this.visibility = visibility;
	}

	public LocalDate getOpeningDateFrom() {
		return openingDateFrom;
	}

	public void setOpeningDateFrom(LocalDate openingDateFrom) {
		this.openingDateFrom = openingDateFrom;
	}

	public LocalDate getOpeningDateTo() {
		return openingDateTo;
	}

	public void setOpeningDateTo(LocalDate openingDateTo) {
		this.openingDateTo = openingDateTo;
	}

	public LocalDate getClosingDateFrom() {
		return closingDateFrom;
	}

	public void setClosingDateFrom(LocalDate closingDateFrom) {
		this.closingDateFrom = closingDateFrom;
	}

	public LocalDate getClosingDateTo() {
		return closingDateTo;
	}

	public void setClosingDateTo(LocalDate closingDateTo) {
		this.closingDateTo = closingDateTo;
	}
}
