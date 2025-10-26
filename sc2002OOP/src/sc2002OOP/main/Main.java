package sc2002OOP.main;

import java.util.Scanner;

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
		System.out.println("");
		System.out.println("Welcome to the Internship Placement Management System!");
		System.out.println("");
		System.out.println("========================================================");
		System.out.println("Please select1 a choice to continue:");
		System.out.println("1. Login");
		System.out.println("2. Forgot UserID");
		System.out.println("3. Exit");
		System.out.println("========================================================");
		
		try (
				Scanner sc = new Scanner(System.in);
				
			) {
			int choice;
			while (true) {
				System.out.print("Your Choice: ");
				choice = sc.nextInt();
				if (choice==1) {
					User user = new User();
					user.login();
					user.displayHome();
				} else if (choice==2) {
					User user = new User();
//					user.forgotPassword();
					
				} else if (choice==3) {
					System.out.println("Thank you, and see you later!");
					System.exit(0);
				}
			}
		}
		
//		FileIOHandler.readFile("sample_student_list.csv");
	}
}
