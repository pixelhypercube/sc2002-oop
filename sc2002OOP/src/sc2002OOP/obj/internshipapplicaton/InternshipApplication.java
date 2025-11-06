package sc2002OOP.obj.internshipapplicaton;

import java.io.Serializable;

/**
 * <h1>Internship Application Data Entity</h1>
 * <p>
 * This class represents a <b>concrete data entity</b> for an {@link sc2002OOP.obj.internshipapplicaton.InternshipApplication internship application} 
 * submitted by a student. It contains all essential details necessary to uniquely identify and track the application's lifecycle, 
 * including the student, the target internship, and the current status.
 * </p>
 * @author Kee Kai Wen
 * @author Kelvin Tay Wei Jie
 * @author Koay Jun Zhi
 * @author Lim Jia Wei Jerald
 * @author Teo Kai Jie
 * @version 1.0
 * @see sc2002OOP.obj.internshipapplicaton.IApplication
 * @see sc2002OOP.obj.internshipapplicaton.InternshipApplicationManager
 */
public class InternshipApplication implements IApplication, Serializable  {
	private static final long serialVersionUID = 626089632082163128L;
	private String applicationID, studentID, internshipID;
	private InternshipApplicationStatus status;
	
	public InternshipApplication() {}
	
	public InternshipApplication(String applicationID, String studentID, String internshipID, InternshipApplicationStatus status) {
		this.applicationID = applicationID;
		this.studentID = studentID;
		this.internshipID = internshipID;
		this.status = status;
	}

	/**
     * Retrieves the ID of the student who submitted this application.
     * @return The student's ID as a String.
     */
	public String getStudentID() {
		return studentID;
	}

	/**
     * Sets the ID of the student who submitted this application.
     * @param studentID The new student ID.
     */
	public void setStudentID(String studentID) {
		this.studentID = studentID;
	}

	/**
     * Retrieves the ID of the internship opportunity this application targets.
     * @return The internship ID as a String.
     */
	public String getInternshipID() {
		return internshipID;
	}

	/**
     * Sets the ID of the internship opportunity this application targets.
     * @param internshipID The new internship ID.
     */
	public void setInternshipID(String internshipID) {
		this.internshipID = internshipID;
	}

	/**
     * Retrieves the current processing status of the application.
     * @return The {@code InternshipApplicationStatus}.
     */
	public InternshipApplicationStatus getStatus() {
		return status;
	}

	/**
     * Sets the current processing status of the application.
     * @param status The new {@code InternshipApplicationStatus}.
     */
	public void setStatus(InternshipApplicationStatus status) {
		this.status = status;
	}

	/**
     * Sets the unique identifier for this application record.
     * @param applicationID The new application ID.
     */
	public String getApplicationID() {
		return applicationID;
	}

	public void setApplicationID(String applicationID) {
		this.applicationID = applicationID;
	}
	
}
