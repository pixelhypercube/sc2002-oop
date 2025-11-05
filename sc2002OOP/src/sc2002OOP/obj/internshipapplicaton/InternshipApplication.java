package sc2002OOP.obj.internshipapplicaton;

import java.io.Serializable;

/**
 * <h1>Internship Application Data Entity</h1>
 * <p>
 * This class represents a **concrete data entity** for an internship application submitted by a student. 
 * It contains all essential details necessary to uniquely identify and track the application's lifecycle, 
 * including the student, the target internship, and the current status.
 * </p>
 * @apiNote This class is a **Plain Old Java Object (POJO)** used primarily for data encapsulation. 
 * It implements the <code>IApplication</code> contract and <code>Serializable</code> to allow its state 
 * to be <b>persisted</b> (saved) by the <code>InternshipApplicationManager</code> for data storage. 
 * The **status** field dictates the outcome of the application (e.g., PENDING, SUCCESSFUL).
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

	public String getStudentID() {
		return studentID;
	}

	public void setStudentID(String studentID) {
		this.studentID = studentID;
	}

	public String getInternshipID() {
		return internshipID;
	}

	public void setInternshipID(String internshipID) {
		this.internshipID = internshipID;
	}

	public InternshipApplicationStatus getStatus() {
		return status;
	}

	public void setStatus(InternshipApplicationStatus status) {
		this.status = status;
	}

	public String getApplicationID() {
		return applicationID;
	}

	public void setApplicationID(String applicationID) {
		this.applicationID = applicationID;
	}
	
}
