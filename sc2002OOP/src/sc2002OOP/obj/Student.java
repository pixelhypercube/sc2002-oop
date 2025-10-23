package sc2002OOP.obj;

public class Student extends User {
	private String major;
	private int year;
	
	public Student(String userID, String name, String major, int year, String email, String password) {
		super(userID,name,email,password);
		this.major = major;
		this.year = year;
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
