package sc2002OOP.obj.company;

import java.io.Serializable;

public class Company implements Serializable, ICompany{
	private static final long serialVersionUID = -4028494579721857457L;
	private String companyID, companyName;
	
	public Company() {}
	public Company(String companyID, String companyName) {
		this.companyID = companyID;
		this.companyName = companyName;
	}
	
	public void print() {
		System.out.println("Company ID: "+companyID);
		System.out.println("Company Name: "+companyName);
	}
	
	// GETTERS AND SETTERS
	public String getCompanyID() {
		return companyID;
	}
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
}
