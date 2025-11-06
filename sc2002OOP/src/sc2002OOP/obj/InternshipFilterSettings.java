package sc2002OOP.obj;

import java.io.Serializable;
import java.time.LocalDate;

import sc2002OOP.obj.internshipopportunity.InternshipOpportunityLevel;
import sc2002OOP.obj.internshipopportunity.InternshipOpportunityStatus;


/**
 * <h1>Internship Filter Settings (Data Structure)</h1>
 * <p>
 * This class serves as a container for all <b>potential search criteria</b> applicable to internship opportunities. 
 * It is primarily used to store the filter settings provided by {@link sc2002OOP.obj.User users} when they view their available internships.
 * </p>
 * @author Kee Kai Wen
 * @author Kelvin Tay Wei Jie
 * @author Koay Jun Zhi
 * @author Lim Jia Wei Jerald
 * @author Teo Kai Jie
 * @version 1.0
 */
public class InternshipFilterSettings implements Serializable {
	
private static final long serialVersionUID = -5163111704314269118L;
    
    /**
     * Filter criteria based on the unique Internship ID (usually partial string matching).
     */
	private String internshipID;
    
    /**
     * Filter criteria based on the internship title (usually partial string matching).
     */
	private String title;
    
    /**
     * Filter criteria based on the internship description (usually partial string matching).
     */
	private String description;
    
    /**
     * Filter criteria based on the unique Company ID (usually partial string matching).
     */
	private String companyID;
    
    /**
     * Filter criteria based on the preferred majors/fields of study listed in the opportunity.
     */
	private String preferredMajors;
    
    /**
     * Filter criteria based on the required skill level of the opportunity.
     */
	private InternshipOpportunityLevel level;
    
    /**
     * Filter criteria based on the administrative status of the opportunity (e.g., APPROVED, PENDING).
     */
	private InternshipOpportunityStatus status;
    
    /**
     * Filter criteria based on whether the opportunity is currently visible to students.
     */
	private Boolean visibility;
    
    /**
     * Filter criteria: Only show opportunities with an opening date on or after this date.
     */
	private LocalDate openingDateFrom;
    
    /**
     * Filter criteria: Only show opportunities with an opening date on or before this date.
     */
	private LocalDate openingDateTo;
    
    /**
     * Filter criteria: Only show opportunities with a closing date on or after this date.
     */
	private LocalDate closingDateFrom;
    
    /**
     * Filter criteria: Only show opportunities with a closing date on or before this date.
     */
	private LocalDate closingDateTo;
	
	/**
	 * Default constructor, initializes all fields to their default values (<code>null</code> for objects).
	 */
	public InternshipFilterSettings() {}
	
	/**
     * Constructs a filter settings object with all possible criteria specified.
     *
     * @param internshipID The internship ID filter string.
     * @param title The title filter string.
     * @param description The description filter string.
     * @param companyID The company ID filter string.
     * @param preferredMajors The preferred majors filter string.
     * @param level The required level filter enum.
     * @param status The status filter enum.
     * @param visibility The visibility filter boolean.
     * @param openingDateFrom The minimum opening date.
     * @param openingDateTo The maximum opening date.
     * @param closingDateFrom The minimum closing date.
     * @param closingDateTo The maximum closing date.
     */
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
		this.internshipID = internshipID;
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
	
	/**
     * Retrieves the Internship ID filter criteria.
     * @return The filter string for Internship ID.
     */
	public String getInternshipID() {
		return internshipID;
	}

    /**
     * Sets the Internship ID filter criteria.
     * @param internshipID The filter string for Internship ID.
     */
	public void setInternshipID(String internshipID) {
		this.internshipID = internshipID;
	}

    /**
     * Retrieves the Title filter criteria.
     * @return The filter string for Title.
     */
	public String getTitle() {
		return title;
	}

    /**
     * Sets the Title filter criteria.
     * @param title The filter string for Title.
     */
	public void setTitle(String title) {
		this.title = title;
	}

    /**
     * Retrieves the Description filter criteria.
     * @return The filter string for Description.
     */
	public String getDescription() {
		return description;
	}

    /**
     * Sets the Description filter criteria.
     * @param description The filter string for Description.
     */
	public void setDescription(String description) {
		this.description = description;
	}

    /**
     * Retrieves the Company ID filter criteria.
     * @return The filter string for Company ID.
     */
	public String getCompanyID() {
		return companyID;
	}

    /**
     * Sets the Company ID filter criteria.
     * @param companyID The filter string for Company ID.
     */
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

    /**
     * Retrieves the Preferred Majors filter criteria.
     * @return The filter string for Preferred Majors.
     */
	public String getPreferredMajors() {
		return preferredMajors;
	}

    /**
     * Sets the Preferred Majors filter criteria.
     * @param preferredMajors The filter string for Preferred Majors.
     */
	public void setPreferredMajors(String preferredMajors) {
		this.preferredMajors = preferredMajors;
	}

    /**
     * Retrieves the Internship Opportunity Level filter criteria.
     * @return The <code>InternshipOpportunityLevel</code> enum value.
     */
	public InternshipOpportunityLevel getLevel() {
		return level;
	}

    /**
     * Sets the Internship Opportunity Level filter criteria.
     * @param level The <code>InternshipOpportunityLevel</code> enum value.
     */
	public void setLevel(InternshipOpportunityLevel level) {
		this.level = level;
	}

    /**
     * Retrieves the Internship Opportunity Status filter criteria.
     * @return The <code>InternshipOpportunityStatus</code> enum value.
     */
	public InternshipOpportunityStatus getStatus() {
		return status;
	}

    /**
     * Sets the Internship Opportunity Status filter criteria.
     * @param status The <code>InternshipOpportunityStatus</code> enum value.
     */
	public void setStatus(InternshipOpportunityStatus status) {
		this.status = status;
	}

    /**
     * Retrieves the Visibility filter criteria.
     * @return The filter boolean for visibility.
     */
	public Boolean getVisibility() {
		return visibility;
	}

    /**
     * Sets the Visibility filter criteria.
     * @param visibility The filter boolean for visibility.
     */
	public void setVisibility(Boolean visibility) {
		this.visibility = visibility;
	}

    /**
     * Retrieves the minimum Opening Date filter criteria (inclusive).
     * @return The minimum <code>LocalDate</code> for the opening date.
     */
	public LocalDate getOpeningDateFrom() {
		return openingDateFrom;
	}

    /**
     * Sets the minimum Opening Date filter criteria (inclusive).
     * @param openingDateFrom The minimum <code>LocalDate</code> for the opening date.
     */
	public void setOpeningDateFrom(LocalDate openingDateFrom) {
		this.openingDateFrom = openingDateFrom;
	}

    /**
     * Retrieves the maximum Opening Date filter criteria (inclusive).
     * @return The maximum <code>LocalDate</code> for the opening date.
     */
	public LocalDate getOpeningDateTo() {
		return openingDateTo;
	}

    /**
     * Sets the maximum Opening Date filter criteria (inclusive).
     * @param openingDateTo The maximum <code>LocalDate</code> for the opening date.
     */
	public void setOpeningDateTo(LocalDate openingDateTo) {
		this.openingDateTo = openingDateTo;
	}

    /**
     * Retrieves the minimum Closing Date filter criteria (inclusive).
     * @return The minimum <code>LocalDate</code> for the closing date.
     */
	public LocalDate getClosingDateFrom() {
		return closingDateFrom;
	}

    /**
     * Sets the minimum Closing Date filter criteria (inclusive).
     * @param closingDateFrom The minimum <code>LocalDate</code> for the closing date.
     */
	public void setClosingDateFrom(LocalDate closingDateFrom) {
		this.closingDateFrom = closingDateFrom;
	}

    /**
     * Retrieves the maximum Closing Date filter criteria (inclusive).
     * @return The maximum <code>LocalDate</code> for the closing date.
     */
	public LocalDate getClosingDateTo() {
		return closingDateTo;
	}

    /**
     * Sets the maximum Closing Date filter criteria (inclusive).
     * @param closingDateTo The maximum <code>LocalDate</code> for the closing date.
     */
	public void setClosingDateTo(LocalDate closingDateTo) {
		this.closingDateTo = closingDateTo;
	}
}
