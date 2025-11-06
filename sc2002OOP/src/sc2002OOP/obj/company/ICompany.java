package sc2002OOP.obj.company;

import java.util.ArrayList;
import java.util.Scanner;

import sc2002OOP.obj.companyrepresentative.CompanyRepresentative;
import sc2002OOP.obj.internshipopportunity.InternshipOpportunity;
import sc2002OOP.obj.withdrawalrequest.WithdrawalRequest;

/**
 * <h1>Company Contract Interface</h1>
 * <p>
 * This interface defines the mandatory contract for a <b>Company</b> entity within the IPMS. 
 * It ensures all company objects have the necessary attributes and basic utility methods, 
 * like printing details and accessing core properties.
 * </p>
 * @author Kee Kai Wen
 * @author Kelvin Tay Wei Jie
 * @author Koay Jun Zhi
 * @author Lim Jia Wei Jerald
 * @author Teo Kai Jie
 * @version 1.0
 * @see sc2002OOP.obj.company.Company
 * @see sc2002OOP.obj.company.CompanyManager
 */
public interface ICompany {
    /**
     * Retrieves the unique identifier for the company.
     * @return The company's ID as a <code>String</code>.
     */
    String getCompanyID();

    /**
     * Sets the unique identifier for the company.
     * @param companyID The new company ID to set as a <code>String</code>.
     */
    void setCompanyID(String companyID);

    /**
     * Retrieves the official name of the company.
     * @return The company's name as a <code>String</code>.
     */
    String getCompanyName();

    /**
     * Sets the official name of the company.
     * @param companyName The new company name to set as a <code>String</code>.
     */
    void setCompanyName(String companyName);
}