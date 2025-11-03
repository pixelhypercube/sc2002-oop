package sc2002OOP.obj.internshipapplicaton;

import java.io.Serializable;

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
	
	public void print() {
		System.out.println("Application ID: " + applicationID);
		System.out.println("Student ID:     " + studentID);
		System.out.println("Internship ID:  " + internshipID);
		System.out.println("Status:         " + status);
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
