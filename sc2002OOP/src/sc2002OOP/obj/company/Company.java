package sc2002OOP.obj.company;

import java.io.Serializable;

/**
 * <h1>Company Data Entity</h1>
 * <p>
 * This class represents a **basic data entity** for a company within the IPMS. 
 * It stores fundamental attributes such as the company's unique ID and its registered name.
 * </p>
 * @apiNote This class is a **Plain Old Java Object (POJO)** that acts as a simple container for company details. 
 * It implements <code>Serializable</code> to allow its data state to be <b>persisted</b> 
 * (saved) to file storage by the <code>CompanyManager</code>. The <code>ICompany</code> interface 
 * ensures adherence to any required contract methods.
 * @author Kee Kai Wen
 * @author Kelvin Tay Wei Jie
 * @author Koay Jun Zhi
 * @author Lim Jia Wei Jerald
 * @author Teo Kai Jie
 * @version 1.0
 * @see sc2002OOP.obj.company.CompanyManager
 * @see sc2002OOP.obj.company.ICompany
 */
public class Company implements Serializable, ICompany {
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
