package sc2002OOP.obj;

public class Staff extends User {
	private String role,department;
	
	public Staff() {}
	
	public Staff(String userID, String name, String role, String department, String email,String password) {
		super(userID,name,email,password);
		this.role = role;
		this.department = department;
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
