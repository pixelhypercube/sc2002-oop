package sc2002OOP.main;


/**
 * <h1>System Constants List</h1>
 * <p>
 * This class serves as a central repository for all **hardcoded system constants** used throughout the IPMS application, 
 * primarily defining file paths, resource locations, and data delimiters. Paths are dynamically constructed based on the 
 * {@link #IS_DEBUGGING} flag to ensure data persistence works correctly in both the IDE environment and the compiled JAR.
 * </p>
 * @apiNote This class is declared <code>public final</code> and contains only <code>public static final</code> fields. 
 * It adheres to the standard pattern for a **constants utility class** to ensure **data integrity** and easy global access to configuration values.
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
     * Defines the base directory path for files that require **read/write access** (e.g., serialized DAT files, exported reports).
     * <p>In debug mode, this resolves to the absolute path: <code>[Project Root]/src/data/</code>.</p>
     * <p>In production mode, this resolves to the relative path: <code>data/</code> (relative to the executable JAR).</p>
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
    
    /**
     * Default filename for the CSV report generated when exporting internship application data.
     */
	public static final String EXPORTED_INTERNSHIP_APP_FILE = "applications.csv";
	
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