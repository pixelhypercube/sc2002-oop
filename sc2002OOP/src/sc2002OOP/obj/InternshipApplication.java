package sc2002OOP.obj;

import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

import sc2002OOP.main.Constants;
import sc2002OOP.main.FileIOHandler;

public class InternshipApplication {
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
	
	public static ArrayList<InternshipApplication> getAllInternshipApplications() {
		String contents = FileIOHandler.getFileContents(Constants.INTERNSHIP_APPLICATIONS_FILE);
		
		ArrayList<String> headers = new ArrayList<>();
		int index = 0;
		
		ArrayList<InternshipApplication> internshipApplications = new ArrayList<>();
		for (String line : contents.split(Constants.NEW_LINE)) {
			if (line.trim().isEmpty()) continue;
			
			InternshipApplication internshipApp = new InternshipApplication();
			
			String[] data = line.split(Constants.DELIMITER);
			
			for (int i = 0;i<data.length;i++) {
				String cell = data[i];
				
				if (index==0) headers.add(cell);
				else {
					if (headers.get(i).equals("ApplicationID"))
						internshipApp.setApplicationID(cell);
					else if (headers.get(i).equals("StudentID"))
						internshipApp.setStudentID(cell);
					else if (headers.get(i).equals("InternshipID"))
						internshipApp.setInternshipID(cell);
					else if (headers.get(i).equals("Status")) {
						if (cell.toLowerCase().equals("pending"))
							internshipApp.setStatus(InternshipApplicationStatus.PENDING);
						else if (cell.toLowerCase().equals("accepted"))
							internshipApp.setStatus(InternshipApplicationStatus.ACCEPTED);
						else if (cell.toLowerCase().equals("rejected"))
							internshipApp.setStatus(InternshipApplicationStatus.REJECTED);
						else if (cell.toLowerCase().equals("filled"))
							internshipApp.setStatus(InternshipApplicationStatus.FILLED);
					}
				}
			}
			if (index++>0) internshipApplications.add(internshipApp);
		}
		return internshipApplications;
	}
	
	public static ArrayList<InternshipApplication> getFilteredInternshipApplications(
		String applicationID, String studentID, String internshipID, InternshipApplicationStatus status
	) {
		return (ArrayList<InternshipApplication>) getAllInternshipApplications()
				.stream()
				.filter(obj -> (
						(applicationID.isEmpty() || obj.getApplicationID().equals(applicationID)) &&
						(studentID.isEmpty() || Objects.equals(obj.getStudentID(), studentID)) &&
						(internshipID.isEmpty() || Objects.equals(obj.getInternshipID(), internshipID)) &&
						(status==null || Objects.equals(obj.getStatus(), status))
					))
				.collect(Collectors.toList());
		
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
