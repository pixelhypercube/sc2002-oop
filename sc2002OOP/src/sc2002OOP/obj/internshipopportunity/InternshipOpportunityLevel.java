package sc2002OOP.obj.internshipopportunity;

/**
 * <h1>Internship Opportunity Difficulty Level</h1>
 * <p>
 * This enumeration defines the possible **levels of difficulty or seniority** for an 
 * <code>InternshipOpportunity</code>. This helps students gauge the expected complexity 
 * and required skills for the role.
 * </p>
 * @apiNote The level is set by the <code>CompanyRepresentative</code> during internship creation 
 * and serves as a key filtering and categorization attribute for students browsing opportunities.
 * @author Kee Kai Wen
 * @author Kelvin Tay Wei Jie
 * @author Koay Jun Zhi
 * @author Lim Jia Wei Jerald
 * @author Teo Kai Jie
 * @version 1.0
 * @see sc2002OOP.obj.internshipopportunity.InternshipOpportunity
 */
public enum InternshipOpportunityLevel {
	/**
	 * Represents an entry-level internship, typically requiring fundamental skills 
	 * and offering basic exposure to the industry.
	 */
	BASIC,
	
	/**
	 * Represents a mid-level internship, expecting some prior knowledge or experience 
	 * and involving moderately complex tasks.
	 */
	INTERMEDIATE,
	
	/**
	 * Represents a senior-level internship, requiring specialized skills, prior experience, 
	 * and involving complex, critical projects.
	 */
	ADVANCED
}