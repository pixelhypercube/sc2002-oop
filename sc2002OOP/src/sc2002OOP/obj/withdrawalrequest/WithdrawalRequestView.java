package sc2002OOP.obj.withdrawalrequest;

import java.util.ArrayList;

import sc2002OOP.main.Viewer;

/**
 * <h1>Withdrawal Request View Class</h1>
 * <p>
 * This class provides **static utility methods** for displaying information regarding 
 * {@code WithdrawalRequest} objects in a structured, table format for the user interface.
 * </p>
 * <p>
 * It works with {@code WithdrawalRequestManager} to fetch the necessary data and 
 * relies on the {@code Viewer} class to handle the formatting and printing of the tables.
 * </p>
 *
 * @author (Assumed Authors from Project)
 * @version 1.0
 * @see sc2002OOP.main.Viewer
 * @see sc2002OOP.obj.withdrawalrequest.WithdrawalRequest
 * @see sc2002OOP.obj.withdrawalrequest.WithdrawalRequestManager
 */
public class WithdrawalRequestView {
	/**
     * Prints a formatted table listing **all Withdrawal Requests** currently managed by 
     * the {@code WithdrawalRequestManager}.
     * <p>
     * The table includes the following columns: **Application ID** (referencing the internship application) 
     * and the request's **Status**.
     * </p>
     * <p>
     * Data is retrieved using {@code WithdrawalRequestManager.getWithdrawalReqs()} and
     * displayed via {@code Viewer.printTable()}.
     * </p>
     *
     * @see #printWithdrawalReqTable(ArrayList)
     */
	public static void printWithdrawalReqTable() {
		ArrayList<String> headers = new ArrayList<>();
		headers.add("Application ID");
		headers.add("Status: ");
		
		ArrayList<ArrayList<String>> data = new ArrayList<>();
		for (WithdrawalRequest wReq : WithdrawalRequestManager.getWithdrawalReqs()) {
			ArrayList<String> rec = new ArrayList<String>();
			rec.add(wReq.getApplicationID());
			rec.add(wReq.getStatus().toString());
			data.add(rec);
		}
		Viewer.printTable(headers, data);
	}
	
	/**
     * Prints a formatted table listing a ** specific subset of Withdrawal Requests** provided 
     * in an {@code ArrayList}.
     * <p>
     * The table includes the following columns: **Application ID** and the request's **Status**.
     * </p>
     *
     * @param wReqs An {@code ArrayList} of {@code WithdrawalRequest} objects to be displayed.
     * @see #printWithdrawalReqTable()
     */
	public static void printWithdrawalReqTable(ArrayList<WithdrawalRequest> wReqs) {
		ArrayList<String> headers = new ArrayList<>();
		headers.add("Application ID");
		headers.add("Status: ");
		
		ArrayList<ArrayList<String>> data = new ArrayList<>();
		for (WithdrawalRequest wReq : wReqs) {
			ArrayList<String> rec = new ArrayList<String>();
			rec.add(wReq.getApplicationID());
			rec.add(wReq.getStatus().toString());
			data.add(rec);
		}
		Viewer.printTable(headers, data);
	}
}
