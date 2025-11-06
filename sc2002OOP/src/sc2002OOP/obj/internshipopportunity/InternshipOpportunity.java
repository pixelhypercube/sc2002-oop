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

/**
 * <h1>Internship Opportunity Data Entity</h1>
 * <p>
 * This class represents a <b>concrete data entity</b> for an internship opportunity posted by a company. 
 * It encapsulates all the details of the opening, including its unique ID, title, description, 
 * requirements (level, preferred major), slot availability, and administrative status.
 * </p>
 * @author Kee Kai Wen
 * @author Kelvin Tay Wei Jie
 * @author Koay Jun Zhi
 * @author Lim Jia Wei Jerald
 * @author Teo Kai Jie
 * @version 1.0
 * @see sc2002OOP.obj.internshipopportunity.IOpportunity
 * @see sc2002OOP.obj.internshipopportunity.InternshipOpportunityManager
 * @see sc2002OOP.obj.internshipopportunity.InternshipOpportunityStatus
 */
public class InternshipOpportunity implements IOpportunity, Serializable  {
	private static final long serialVersionUID = 7909012667627397752L;
	private String internshipID, title, description, companyID, preferredMajor;
	private ArrayList<CompanyRepresentative> companyRepsInCharge;
	private LocalDate openingDate, closingDate;
	private int numSlots;
	private InternshipOpportunityLevel level;
	private InternshipOpportunityStatus status;
	private boolean visibility;
	
	/**
     * Constructs a full Internship Opportunity entity, including the list of Company Representatives in charge.
     *
     * @param internshipID The unique identifier for this opportunity.
     * @param title The title of the internship.
     * @param description A detailed description of the role.
     * @param companyID The ID of the company offering the internship.
     * @param preferredMajor The preferred academic major for applicants.
     * @param companyRepsInCharge The list of representatives responsible for this internship.
     * @param numSlots The total number of available slots.
     * @param level The difficulty/experience level required.
     * @param status The administrative approval status (e.g., APPROVED, PENDING).
     * @param openingDate The date applications open.
     * @param closingDate The date applications close.
     * @param visibility True if the opportunity is currently visible to students, false otherwise.
     */
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
	
	/**
     * Constructs an Internship Opportunity entity without specifying the list of Company Representatives initially.
     *
     * @param internshipID The unique identifier for this opportunity.
     * @param title The title of the internship.
     * @param description A detailed description of the role.
     * @param companyID The ID of the company offering the internship.
     * @param preferredMajor The preferred academic major for applicants.
     * @param numSlots The total number of available slots.
     * @param level The difficulty/experience level required.
     * @param status The administrative approval status (e.g., APPROVED, PENDING).
     * @param openingDate The date applications open.
     * @param closingDate The date applications close.
     * @param visibility True if the opportunity is currently visible to students, false otherwise.
     */
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

	/**
     * Default constructor for the Internship Opportunity.
     */
	public InternshipOpportunity() {}

	/**
     * Retrieves the title of the internship opportunity.
     * @return The title as a String.
     */
	public String getTitle() {
		return title;
	}

	/**
     * Sets the title of the internship opportunity.
     * @param title The new title.
     */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
     * Retrieves the description of the internship opportunity.
     * @return The description as a String.
     */
	public String getDescription() {
		return description;
	}

	/**
     * Sets the description of the internship opportunity.
     * @param description The new description.
     */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
     * Retrieves the ID of the company offering the internship.
     * @return The company ID as a String.
     */
	public String getCompanyID() {
		return companyID;
	}

	/**
     * Sets the ID of the company offering the internship.
     * @param companyID The new company ID.
     */
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	/**
     * Retrieves the preferred academic major for applicants.
     * @return The preferred major as a String.
     */
	public String getPreferredMajor() {
		return preferredMajor;
	}

	/**
     * Sets the preferred academic major for applicants.
     * @param preferredMajor The new preferred major.
     */
	public void setPreferredMajor(String preferredMajor) {
		this.preferredMajor = preferredMajor;
	}

	/**
     * Retrieves the list of Company Representatives assigned to this opportunity.
     * @return An {@code ArrayList} of {@code CompanyRepresentative} objects.
     */
	public ArrayList<CompanyRepresentative> getCompanyRepsInCharge() {
		return companyRepsInCharge;
	}

	/**
     * Sets the list of Company Representatives assigned to this opportunity.
     * @param companyRepInCharge The new {@code ArrayList} of {@code CompanyRepresentative} objects.
     */
	public void setCompanyRepsInCharge(ArrayList<CompanyRepresentative> companyRepInCharge) {
		this.companyRepsInCharge = companyRepInCharge;
	}

	/**
     * Retrieves the total number of available slots for this internship.
     * @return The number of slots as an integer.
     */
	public int getNumSlots() {
		return numSlots;
	}

	/**
     * Sets the total number of available slots for this internship.
     * @param numSlots The new number of slots.
     */
	public void setNumSlots(int numSlots) {
		this.numSlots = numSlots;
	}

	/**
     * Retrieves the difficulty or experience level required for the internship.
     * @return The {@code InternshipOpportunityLevel}.
     */
	public InternshipOpportunityLevel getLevel() {
		return level;
	}

	/**
     * Sets the difficulty or experience level required for the internship.
     * @param level The new {@code InternshipOpportunityLevel}.
     */
	public void setLevel(InternshipOpportunityLevel level) {
		this.level = level;
	}

	/**
     * Retrieves the administrative approval status of the opportunity.
     * @return The {@code InternshipOpportunityStatus}.
     */
	public InternshipOpportunityStatus getStatus() {
		return status;
	}

	/**
     * Sets the administrative approval status of the opportunity.
     * @param status The new {@code InternshipOpportunityStatus}.
     */
	public void setStatus(InternshipOpportunityStatus status) {
		this.status = status;
	}

	/**
     * Retrieves the unique identifier for this internship opportunity.
     * @return The internship ID as a String.
     */
	public String getInternshipID() {
		return internshipID;
	}

	/**
     * Sets the unique identifier for this internship opportunity.
     * @param internshipID The new internship ID.
     */
	public void setInternshipID(String internshipID) {
		this.internshipID = internshipID;
	}

	/**
     * Checks if the internship opportunity is currently visible to students.
     * @return True if visible, false otherwise.
     */
	public boolean isVisibility() {
		return visibility;
	}

	/**
     * Sets the visibility status of the internship opportunity to students.
     * @param visibility The new visibility status (true for visible, false for hidden).
     */
	public void setVisibility(boolean visibility) {
		this.visibility = visibility;
	}

	/**
     * Retrieves the application opening date.
     * @return The opening date as a {@code LocalDate}.
     */
	public LocalDate getOpeningDate() {
		return openingDate;
	}

	/**
     * Sets the application opening date.
     * @param openingDate The new opening date.
     */
	public void setOpeningDate(LocalDate openingDate) {
		this.openingDate = openingDate;
	}

	/**
     * Retrieves the application closing date.
     * @return The closing date as a {@code LocalDate}.
     */
	public LocalDate getClosingDate() {
		return closingDate;
	}

	/**
     * Sets the application closing date.
     * @param closingDate The new closing date.
     */
	public void setClosingDate(LocalDate closingDate) {
		this.closingDate = closingDate;
	}
}
