package sc2002OOP.obj;

public class Student extends User {
	private String studentId,major;
	private int year;
	
	public Student(String studentId, String name, String major, int year, String email, String password) {
		super(name,email,password);
		this.studentId = studentId;
		this.major = major;
		this.year = year;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}
	
	
}
