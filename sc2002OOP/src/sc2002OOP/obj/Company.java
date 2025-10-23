package sc2002OOP.obj;

public class Company {
	private String companyRepID, name, companyName, department, position, email,status;

	public Company(String companyRepID, String name, String companyName, String department, String position, String email, String status) {
		this.companyRepID = companyRepID;
		this.name = name;
		this.companyName = companyName;
		this.department = department;
		this.position = position;
		this.email = email;
		this.status = status;
	}
	
	public String getCompanyRepID() {
		return companyRepID;
	}

	public void setCompanyRepID(String companyRepID) {
		this.companyRepID = companyRepID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
