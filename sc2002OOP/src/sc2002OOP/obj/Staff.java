package sc2002OOP.obj;

public class Staff extends User {
	private String staffID,role,department;
	
	public Staff(String staffID, String name, String role, String department, String email) {
		super(name,email);
		this.staffID = staffID;
		this.role = role;
		this.department = department;
	}

	public String getStaffID() {
		return staffID;
	}

	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}
}
