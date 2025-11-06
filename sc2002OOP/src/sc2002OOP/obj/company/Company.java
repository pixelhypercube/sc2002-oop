package sc2002OOP.obj.company;

import java.io.Serializable;

/**
 * <h1>Company Data Entity</h1>
 * <p>
 * This class represents a <b>basic data entity</b> for a company within the IPMS. 
 * It stores fundamental attributes such as the <b>company's unique ID</b> and its <b>registered name</b>.
 * </p>
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
	
	/**
     * Default constructor for the Company entity.
     */
	public Company() {}
	
	/**
     * Constructs a Company entity with a specified ID and name.
     * * @param companyID The unique identifier for the company.
     * @param companyName The registered name of the company.
     */
	public Company(String companyID, String companyName) {
		this.companyID = companyID;
		this.companyName = companyName;
	}
	
	// GETTERS AND SETTERS
	
	/**
     * Retrieves the unique identifier of the company.
     * * @return The Company ID as a String.
     */
	public String getCompanyID() {
		return companyID;
	}
	
	/**
     * Sets the unique identifier of the company.
     * * @param companyID The new Company ID as a String.
     */
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	
	/**
     * Retrieves the registered name of the company.
     * * @return The Company Name as a String.
     */
	public String getCompanyName() {
		return companyName;
	}
	
	/**
     * Sets the registered name of the company.
     * * @param companyName The new Company Name as a String.
     */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
}
