package sc2002OOP.main;

/**
 * <h1>System Constants List</h1>
 * <p>
 * This class serves as a central repository for all <b>hardcoded system constants</b> used throughout the entire application,
 * which primarily defines file paths, resource locations, and data delimiters.
 * </p>
 * @author Kee Kai Wen
 * @author Kelvin Tay Wei Jie
 * @author Koay Jun Zhi
 * @author Lim Jia Wei Jerald
 * @author Teo Kai Jie
 * @version 1.0
 */
public final class Constants {
	
	/**
     * Flag to toggle the application between IDE debugging mode (<code>true</code>) and production/JAR execution mode (<code>false</code>).
     * This affects the construction of the {@link #FS_DATA_FOLDER}.
     */
	public static final boolean IS_DEBUGGING = false;
	
	/**
	 * Defines the base directory path for files that require <b>read/write access</b> (e.g., serialized DAT files, exported reports).
	 * <p><b>If {@link #IS_DEBUGGING} is {@code true} (IDE Mode):</b></p>
	 * <p>This resolves to the absolute path: {@code [Project Root]/src/data/}</p>
	 * <p><b>If {@link #IS_DEBUGGING} is {@code false} (Production Mode):</b></p>
	 * <p>This resolves to the relative path: {@code data/} (relative to the executable JAR).</p>
	 * <h3>Production Execution Steps (for JAR):</h3>
	 * <ol>
	 * <li>Ensure {@link #IS_DEBUGGING} is set to {@code false}.</li>
	 * <li>Export the project to an executable JAR file (e.g., {@code IPMS.jar}). This JAR file should be placed in the project's <b>{@code bin}</b> folder (i.e. {@code ...\bin}).</li>
	 * <li><b>If it doesn't already exist, manually create an empty folder named {@code data} inside the {@code bin} folder.</b> (i.e., {@code ...\bin\data}).</li>
	 * <li>Open Command Prompt, navigate to the <b>{@code bin}</b> folder (e.g., {@code cd [Project Root]/bin}), and execute: {@code java -jar IPMS.jar}</li>
	 * <li>The application will now read/write files to the <b>{@code data} folder</b> located next to the JAR file.</li>
	 * </ol>
	 */
	public static final String FS_DATA_FOLDER = (IS_DEBUGGING 
	        ? System.getProperty("user.dir") + java.io.File.separator + "src" + java.io.File.separator + "data" + java.io.File.separator
	        : "data" + java.io.File.separator);
	
	/**
     * Defines the root path for **internal resources** (e.g., initial CSV files) that are bundled within the JAR file.
     * This path is used exclusively with {@code Class.getResourceAsStream()}.
     */
	public static final String RESOURCE_DATA_FOLDER = "/data/";
	
    /**
     * The full file system path to the directory used for storing serialized (DAT) objects for data persistence.
     * This path is constructed using the dynamically set {@link #FS_DATA_FOLDER}.
     */
	public static final String FILE_SERIALIZED_DIR = FS_DATA_FOLDER + "serialized/";;
    
    /**
     * The relative path to the directory where all generated reports (e.g., CSV exports) are saved.
     * This path is relative to the application's current working directory.
     */
	public static final String EXPORT_REPORTS_PATH = "exports/";
	
	// --- Initial Data File Names (CSV Files) ---
    
    /**
     * Filename for the CSV file containing sample or initial internship opportunity data.
     */
	public static final String INTERNSHIP_OPPORTUNITIES_FILE = "internship_opportunities.csv";
    
    /**
     * Filename for the CSV file containing sample or initial internship application data.
     */
	public static final String INTERNSHIP_APPLICATIONS_FILE = "internship_applications.csv";
    
    /**
     * Filename for the CSV file containing sample or initial Company Representative account data.
     */
	public static final String COMPANY_REPS_FILE = "sample_company_representative_list.csv";
    
    /**
     * Filename for the CSV file containing sample or initial Career Center Staff account data.
     */
	public static final String STAFF_FILE = "sample_staff_list.csv";
    
    /**
     * Filename for the CSV file containing sample or initial Student account data.
     */
	public static final String STUDENT_FILE = "sample_student_list.csv";
    
    /**
     * Filename for the CSV file containing sample or initial withdrawal request data.
     */
	public static final String WITHDRAWAL_REQS_FILE = "withdrawal_requests.csv";
    
//    /**
//     * Default filename for the CSV report generated when exporting internship application data.
//     */
//	public static final String EXPORTED_INTERNSHIP_APP_FILE = "applications.csv";
	
	// --- Persistent Data File Names (DAT Files) ---
    
    /**
     * Filename for the serialized DAT file storing persistent <code>InternshipOpportunity</code> objects.
     */
	public static final String INTERNSHIP_OPPORTUNITIES_DATA_FILE = "internship_opportunities.dat";
    
    /**
     * Filename for the serialized DAT file storing persistent <code>InternshipApplication</code> objects.
     */
	public static final String INTERNSHIP_APPLICATIONS_DATA_FILE = "internship_applications.dat";
    
    /**
     * Filename for the serialized DAT file storing persistent <code>CompanyRepresentative</code> objects.
     */
	public static final String COMPANY_REPS_DATA_FILE = "company_reps.dat";
    
    /**
     * Filename for the serialized DAT file storing persistent <code>CareerCenterStaff</code> objects.
     */
	public static final String STAFF_DATA_FILE = "staff.dat";
    
    /**
     * Filename for the serialized DAT file storing persistent <code>Student</code> objects.
     */
	public static final String STUDENT_DATA_FILE = "students.dat";
    
    /**
     * Filename for the serialized DAT file storing persistent <code>WithdrawalRequest</code> objects.
     */
	public static final String WITHDRAWAL_REQS_DATA_FILE = "withdrawal_reqs.dat";
    
    /**
     * Filename for the serialized DAT file storing persistent <code>Company</code> objects.
     */
	public static final String COMPANY_DATA_FILE = "companies.dat";
	
	// --- Data Delimiters ---
    
    /**
     * The primary delimiter used for separating fields within CSV and data files (e.g., for basic attribute separation).
     */
	public static final String DELIMITER = ",";
    
    /**
     * A secondary delimiter used for separating multi-valued attributes within a single field (e.g., separating multiple majors).
     */
	public static final String SECOND_DELIMITER = ";";
    
    /**
     * Represents the standard newline character, used for formatting files and reports.
     */
	public static final String NEW_LINE = "\n";
}