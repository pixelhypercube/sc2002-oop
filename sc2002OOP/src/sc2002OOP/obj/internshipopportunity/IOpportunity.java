package sc2002OOP.obj.internshipopportunity;

import java.time.LocalDate;
import java.util.ArrayList;

import sc2002OOP.obj.companyrepresentative.CompanyRepresentative;

/**
 * <h1>Internship Opportunity Contract Interface</h1>
 * <p>
 * This interface defines the complete mandatory contract for an **Internship Opportunity** entity. 
 * It ensures all opportunity objects possess the core attributes, administrative flags, 
 * date information, and utility methods required for management and student viewing.
 * </p>
 * @apiNote This contract requires methods for all key attributes, including **ID**, **dates**, 
 * **slot count**, **level**, **status**, and **visibility**. The interface mandates two distinct 
 * display methods: one for staff/reps (<code>print()</code>) and one restricted view for students 
 * (<code>printForStudent()</code>).
 * @author Kee Kai Wen
 * @author Kelvin Tay Wei Jie
 * @author Koay Jun Zhi
 * @author Lim Jia Wei Jerald
 * @author Teo Kai Jie
 * @version 1.0
 * @see sc2002OOP.obj.internshipopportunity.InternshipOpportunity
 * @see sc2002OOP.obj.internshipopportunity.InternshipOpportunityStatus
 */
public interface IOpportunity {
    
    /**
     * Prints the full administrative details of the internship opportunity to the console, 
     * including status, dates, and internal flags.
     */
    void print();
    
    /**
     * Prints the details of the internship opportunity in a restricted format suitable for 
     * student viewing, excluding administrative details like status and internal IDs.
     */
    void printForStudent();
    
    // --- Getter and Setter Methods for Core Attributes ---
    
    /**
     * Retrieves the unique identifier for this internship opportunity.
     * @return The internship ID as a <code>String</code>.
     */
    String getInternshipID();
    
    /**
     * Sets the unique identifier for this internship opportunity.
     * @param internshipID The new internship ID to set as a <code>String</code>.
     */
    void setInternshipID(String internshipID);
    
    /**
     * Retrieves the title of the internship opportunity.
     * @return The title as a <code>String</code>.
     */
    String getTitle();
    
    /**
     * Sets the title of the internship opportunity.
     * @param title The new title to set as a <code>String</code>.
     */
    void setTitle(String title);
    
    /**
     * Retrieves the detailed description of the internship opportunity.
     * @return The description as a <code>String</code>.
     */
    String getDescription();
    
    /**
     * Sets the detailed description of the internship opportunity.
     * @param description The new description to set as a <code>String</code>.
     */
    void setDescription(String description);
    
    /**
     * Retrieves the unique identifier of the company offering the internship.
     * @return The company ID as a <code>String</code>.
     */
    String getCompanyID();
    
    /**
     * Sets the unique identifier of the company offering the internship.
     * @param companyID The new company ID to set as a <code>String</code>.
     */
    void setCompanyID(String companyID);
    
    /**
     * Retrieves the preferred major or field of study for applicants.
     * @return The preferred major as a <code>String</code>.
     */
    String getPreferredMajor();
    
    /**
     * Sets the preferred major or field of study for applicants.
     * @param preferredMajor The new preferred major to set as a <code>String</code>.
     */
    void setPreferredMajor(String preferredMajor);
    
    /**
     * Retrieves the list of Company Representatives responsible for this opportunity.
     * @return An <code>ArrayList</code> of <code>CompanyRepresentative</code> objects.
     */
    ArrayList<CompanyRepresentative> getCompanyRepsInCharge();
    
    /**
     * Sets the list of Company Representatives responsible for this opportunity.
     * @param companyRepInCharge The new list of responsible representatives.
     */
    void setCompanyRepsInCharge(ArrayList<CompanyRepresentative> companyRepInCharge);
    
    /**
     * Retrieves the date when applications for the internship open.
     * @return The opening date as a <code>LocalDate</code>.
     */
    LocalDate getOpeningDate();
    
    /**
     * Sets the date when applications for the internship open.
     * @param openingDate The new opening date to set as a <code>LocalDate</code>.
     */
    void setOpeningDate(LocalDate openingDate);
    
    /**
     * Retrieves the deadline date for submitting applications.
     * @return The closing date as a <code>LocalDate</code>.
     */
    LocalDate getClosingDate();
    
    /**
     * Sets the deadline date for submitting applications.
     * @param closingDate The new closing date to set as a <code>LocalDate</code>.
     */
    void setClosingDate(LocalDate closingDate);
    
    /**
     * Retrieves the maximum number of slots/openings available for this internship.
     * @return The number of slots as an <code>int</code>.
     */
    int getNumSlots();
    
    /**
     * Sets the maximum number of slots/openings available for this internship.
     * @param numSlots The new number of slots to set as an <code>int</code>.
     */
    void setNumSlots(int numSlots);
    
    /**
     * Retrieves the designated difficulty or seniority level of the internship.
     * @return The level as an <code>InternshipOpportunityLevel</code> enum.
     */
    InternshipOpportunityLevel getLevel();
    
    /**
     * Sets the designated difficulty or seniority level of the internship.
     * @param level The new level (<code>InternshipOpportunityLevel</code>) to set.
     */
    void setLevel(InternshipOpportunityLevel level);
    
    /**
     * Retrieves the administrative approval status of the opportunity (e.g., PENDING, APPROVED).
     * @return The status as an <code>InternshipOpportunityStatus</code> enum.
     */
    InternshipOpportunityStatus getStatus();
    
    /**
     * Sets the administrative approval status of the opportunity.
     * @param status The new status (<code>InternshipOpportunityStatus</code>) to set.
     */
    void setStatus(InternshipOpportunityStatus status);
    
    /**
     * Checks if the opportunity is currently visible to students.
     * @return <code>true</code> if visible, <code>false</code> otherwise.
     */
    boolean isVisibility();
    
    /**
     * Sets the visibility status of the opportunity to students.
     * @param visibility The new visibility status to set as a <code>boolean</code>.
     */
    void setVisibility(boolean visibility);
}