package sc2002OOP.main;

import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;


/**
 * <h1>Password Manager Utility</h1>
 * <p>
 * This class serves as a utility for managing user passwords securely. It provides methods 
 * to convert plain text passwords into a cryptographic hash using the <b>SHA-256<b> algorithm, 
 * as well as verifying if a provided password matches a stored hash (for user login authentication).
 * </p>
 * <p>
 * This utility also helps abstract the complexity of cryptographic operations from the main application logic.
 * </p>
 * @author Kee Kai Wen
 * @author Kelvin Tay Wei Jie
 * @author Koay Jun Zhi
 * @author Lim Jia Wei Jerald
 * @author Teo Kai Jie
 * @version 1.0
 */ 
public class PasswordManager {
	/**
     * Generates a secure, one-way hash of a given plain text password using the SHA-256 algorithm.
     * The hash is returned as a hexadecimal string.
     * * @param password The plain text password <code>String</code> to be hashed.
     * @return The <code>String</code> representing the 256-bit hash in hexadecimal format.
     * @throws RuntimeException if the SHA-256 algorithm is not available in the environment.
     */
	public static String hashPassword(String password) {
		try {
	        MessageDigest digest = MessageDigest.getInstance("SHA-256");
	        byte[] encodedhash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
	        
	        StringBuilder hexString = new StringBuilder(2*encodedhash.length);
	        for (byte b : encodedhash) {
	            String hex = Integer.toHexString(0xff & b);
	            if (hex.length() == 1) {
	                hexString.append('0');
	            }
	            hexString.append(hex);
	        }
	        return hexString.toString();
	    } catch (Exception e) {
	        throw new RuntimeException("Hashing failed",e);
	    }
	}
	/**
     * Verifies a user-provided password against a stored hashed password.
     * The input password is first hashed using SHA-256, and the resulting hash is compared 
     * against the stored hash for a match.
     * * @param passwordInput The plain text password entered by the user.
     * @param hashedPassword The stored password hash to compare against.
     * @return <code>true</code> if the input password matches the stored hash (i.e., authentication is successful), 
     * <code>false</code> otherwise.
     */
	public static boolean verifyPassword(String passwordInput, String hashedPassword) {
		String hashedInput = hashPassword(passwordInput);
		return hashedInput.equals(hashedPassword);
	}
}
