package sc2002OOP.obj.internshipapplicaton;

public interface IApplication {
		void print();
		
		String getApplicationID();
		void setApplicationID(String applicationID);
		
		String getStudentID();
		void setStudentID(String studentID);
		
		String getInternshipID();
		void setInternshipID(String internshipID);
		
		InternshipApplicationStatus getStatus();
		void setStatus(InternshipApplicationStatus status);
}
