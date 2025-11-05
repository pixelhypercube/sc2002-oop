package sc2002OOP.obj.internshipopportunity;

import java.util.ArrayList;

/**
 * <h1>Internship Opportunity View Class</h1>
 * <p>
 * This class provides **static utility methods** for displaying lists of 
 * {@code InternshipOpportunity} objects in a user-friendly format on the console.
 * </p>
 * <p>
 * Unlike table views, this class iterates through the list and calls the individual 
 * {@code print()} method implemented within the {@code InternshipOpportunity} class 
 * for detailed, one-by-one listing.
 * </p>
 *
 * @author (Assumed Authors from Project)
 * @version 1.0
 * @see sc2002OOP.obj.internshipopportunity.InternshipOpportunity
 * @see sc2002OOP.obj.internshipopportunity.InternshipOpportunityManager
 */
public class InternshipOpportunityView {
	/**
     * Prints a detailed list of **all Internship Opportunities** currently managed by 
     * the {@code InternshipOpportunityManager}.
     * <p>
     * It retrieves the full list using {@code InternshipOpportunityManager.getInternshipOpps()} 
     * and calls the {@code print()} method on each opportunity for output.
     * </p>
     *
     * @see #printInternshipOppList(ArrayList)
     */
	public static void printInternshipOppList() {
		for (InternshipOpportunity internshipOpp : InternshipOpportunityManager.getInternshipOpps()) {
			internshipOpp.print();
			System.out.println("-".repeat(40));
		}
	}
	
	/**
     * Prints a detailed list of a **specific subset of Internship Opportunities** provided 
     * in an {@code ArrayList}.
     * <p>
     * It iterates through the provided list and calls the {@code print()} method on each 
     * opportunity for detailed output, separating each entry with a dashed line.
     * </p>
     *
     * @param internshipOpps An {@code ArrayList} of {@code InternshipOpportunity} objects to be displayed.
     * @see #printInternshipOppList()
     */
	public static void printInternshipOppList(ArrayList<InternshipOpportunity> internshipOpps) {
		for (InternshipOpportunity internshipOpp : internshipOpps) {
			internshipOpp.print();
			System.out.println("-".repeat(40));
		}
	}
}
