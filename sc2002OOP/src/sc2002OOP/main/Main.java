package sc2002OOP.main;

import java.util.ArrayList;
import java.util.Scanner;

import sc2002OOP.obj.CareerCenterStaff;
import sc2002OOP.obj.CompanyRepresentative;
import sc2002OOP.obj.Student;
import sc2002OOP.obj.User;

public class Main {
	public static void main(String[] args) {
		System.out.println(
				  "   @@@@@@@@@@@@@  @@@@@@@@@@@@@@  @@@@      @@@@  @@@@@@@@@@@@@@   \r\n"
				+ "  ******@@@@***  *@@@@     *@@@@ *@@@@@@  @@@@@@ *@@@@             \r\n"
				+ "       *@@@@     *@@@@     *@@@@ *@@@@*@@@@*@@@@ *@@@@             \r\n"
				+ "       *@@@@     *@@@@@@@@@@@@@@ *@@@@**** *@@@@ *@@@@@@@@@@@@@@   \r\n"
				+ "       *@@@@     *@@@@*********  *@@@@     *@@@@ ***********@@@@   \r\n"
				+ "       *@@@@     *@@@@           *@@@@     *@@@@           *@@@@   \r\n"
				+ "       *@@@@     *@@@@           *@@@@     *@@@@           *@@@@   \r\n"
				+ "  *@@@@@@@@@@@@@ *@@@@           *@@@@     *@@@@ *@@@@@@@@@@@@@@   \r\n"
				+ "  *************  ****            ****      ****  **************     ");
		System.out.println();
		System.out.println("Welcome to the Internship Placement Management System!");
		System.out.println();
		
		ArrayList<User> users = new ArrayList<>();
		
		// load all users from all files
		for (Student student : Student.getStudents())
			users.add(student);
		for (CareerCenterStaff st : CareerCenterStaff.getAllCareerCenterStaff())
			users.add(st);
		for (CompanyRepresentative companyRep : CompanyRepresentative.getCompanyReps())
			users.add(companyRep);
		try (
				Scanner sc = new Scanner(System.in);
			) {
			int choice = 0;
			while (choice != 3) {
				System.out.println("========================================================");
				System.out.println("Please select a choice to continue:");
				System.out.println("1. Login");
				System.out.println("2. Register (Company Representative)");
				System.out.println("3. Forgot Password");
				System.out.println("4. Exit");
				System.out.println("========================================================");
				System.out.print("Your Choice: ");
				if (sc.hasNextInt()) {
					choice = sc.nextInt();
					if (choice==1) {
						User user = User.login(sc,users);
						if (user != null) user.displayHome(sc);
					} else if (choice==2) {
						CompanyRepresentative.register(sc);
					} else if (choice==3) {
//						user.forgotPassword();
						
					} else if (choice==4) {
						System.out.println("Bon Voyage");
						break;
					}
				} else {
					System.out.println("Invalid choice. Please enter 1, 2, or 3.");
					sc.next();
				}
			}
		} catch (Exception e) {
			System.err.println("An unexpected error occurred: " + e.getMessage());
		}
		
	}
}
