package sc2002OOP.obj.company;

import java.util.ArrayList;

import sc2002OOP.main.Viewer;


/**
 * <h1>Company View Class</h1>
 * <p>
 * This class provides <b>static utility methods</b> for displaying company-related information to the user,
 * primarily in the form of a formatted table view.
 * </p>
 * <p>
 * It utilizes the {@link sc2002OOP.obj.company.CompanyManager CompanyManager} to retrieve company data and the {@link sc2002OOP.obj.Viewer Viewer} class to
 * handle the actual printing and formatting of the table output.
 * </p>
 *
 * @author Kee Kai Wen
 * @author Kelvin Tay Wei Jie
 * @author Koay Jun Zhi
 * @author Lim Jia Wei Jerald
 * @author Teo Kai Jie
 * @version 1.0
 * @see sc2002OOP.main.Viewer
 * @see sc2002OOP.obj.company.Company
 * @see sc2002OOP.obj.company.CompanyManager
 */
public class CompanyView {
	/**
     * Prints a formatted table listing **all companies** currently managed by the {@code CompanyManager}.
     * <p>
     * The table includes the "Company ID" and "Company Name" for each company.
     * </p>
     * <p>
     * It retrieves the data from {@code CompanyManager.getCompanies()} and uses {@code Viewer.printTable()}
     * for display.
     * </p>
     *
     * @implNote The headers are hardcoded as "Company ID" and "Company Name".
     * @see #printCompanyTable(ArrayList)
     */
	public static void printTable() {
		ArrayList<String> headers = new ArrayList<>();
		headers.add("Company ID");
		headers.add("Company Name");
		
		ArrayList<ArrayList<String>> data = new ArrayList<>();
		for (Company company : CompanyManager.getCompanies()) {
			ArrayList<String> rec = new ArrayList<>();
			rec.add(company.getCompanyID());
			rec.add(company.getCompanyName());
			data.add(rec);
		}
		Viewer.printTable(headers, data);
	}
	
	/**
     * Prints a formatted table listing a **specific subset of companies** provided in an {@code ArrayList}.
     * <p>
     * The table includes the "Company ID" and "Company Name" for each company in the list.
     * </p>
     *
     * @param companies An {@code ArrayList} of {@code Company} objects to be displayed in the table.
     * @implNote The headers are hardcoded as "Company ID" and "Company Name".
     * @see #printCompanyTable()
     */
	public static void printTable(ArrayList<Company> companies) {
		ArrayList<String> headers = new ArrayList<>();
		headers.add("Company ID");
		headers.add("Company Name");
		
		ArrayList<ArrayList<String>> data = new ArrayList<>();
		for (Company company : companies) {
			ArrayList<String> rec = new ArrayList<>();
			rec.add(company.getCompanyID());
			rec.add(company.getCompanyName());
			data.add(rec);
		}
		Viewer.printTable(headers, data);
	}
}
