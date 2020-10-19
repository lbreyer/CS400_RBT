// --== CS400 File Header Information ==--
// Name: Matthew Thompson Soto
// Email: mathompson23@wisc.edu
// Team: KF
// Role: Front End Developer
// TA: Siddharth Mohan
// Lecturer: Gary Dahl
// Notes to Grader: N/A

import java.util.Scanner;

/**
 * This class is the driver/UI for Group KF's Project 2: Basketball Points
 * Organizer.
 * 
 * @author Matthew Thompson Soto
 */
public class PointsTreeUI {
	@SuppressWarnings("rawtypes")
	private static RedBlackTree tree;

	/**
	 * Private helper method that reads input and returns a character.
	 * 
	 * @param scnr The scanner to read input.
	 * @return a character that serves as a possible command.
	 */
	private static char readChar(Scanner scnr) {
		String temp = scnr.nextLine();

		if (temp == null || temp.equals("")) {
			return '0';
		}

		temp = temp.trim().toLowerCase();

		if (temp.length() == 1) {
			return temp.charAt(0);
		}

		return '0';
	}

	/**
	 * Displays all the players in the Red-Black Tree, after a user inputs the
	 * command (D). Makes use of the tree's toString() method.
	 */
	public static void displayPlayers() {
		for (int i = 0; i < 80; i++) {
			System.out.print("-");
		}

		try {
			System.out.println("\nDisplaying all Players...");
			tree.toString();
		} catch (NullPointerException e) {
			System.out.println("There are no Players in the database.");
			return;
		}
	}

	/**
	 * Gets a Players information using a Player's name. The tree's search() method
	 * is called to retrieve the information.
	 * 
	 * @param scnr The scanner to read input.
	 */
	@SuppressWarnings("unchecked")
	public static void getPlayerInfo(Scanner scnr) {
		boolean pass = false;
		String name = "";

		for (int i = 0; i < 80; i++) {
			System.out.print("-");
		}

		System.out.println("\n---==+ Get Player Information +==---");

		// Prompts the user for Player name
		while (!pass) {
			System.out.println("Please enter the desired Player's name to be looked up: ");
			name = scnr.nextLine();
			if (name.length() > 0) { // Valid name entered
				pass = true;
				continue;
			}
			System.out.println("Invalid name.");
		}

		System.out.println("Looking up " + name + "'s information...");

		if (tree.search(tree.root, name) == null) {
			System.out.println("Player " + name + " could not be found.");
		} else {
			System.out.println("Here is the information:");
			System.out.println("Name: " + tree.search(tree.root, name).name);
			System.out.println("Team: " + tree.search(tree.root, name).team);
			System.out.println("Total Points Scored: " + tree.search(tree.root, name).data);
		}
	}

	/**
	 * Inserts a new Player node into the tree, prompting the user for a valid name,
	 * team name and points scored. The points are utilized as the data for the
	 * tree. This function makes use of the tree's insert() method.
	 * 
	 * @param scnr The scanner to read input.
	 */
	@SuppressWarnings("unchecked")
	private static void insertPlayer(Scanner scnr) {
		boolean pass = false;
		String name = "";
		String team = "";
		int points = 0;

		for (int i = 0; i < 80; i++) {
			System.out.print("-");
		}

		System.out.println("\n---==+ Insert a Player +==---");

		// Prompts the user for Player name
		while (!pass) {
			System.out.println("Please enter the Player's name: ");
			name = scnr.nextLine();
			if (name.length() > 0) { // Valid name entered
				pass = true;
				continue;
			}
			System.out.println("Invalid name.");
		}

		pass = false;
		System.out.println("What team does " + name + " play for?");

		// Prompts user for Player team
		while (!pass) {
			System.out.println("Please enter " + name + "'s team: ");
			team = scnr.nextLine();
			if (team.length() > 0) { // Valid team entered
				pass = true;
				continue;
			}
			System.out.println("Invalid team name.");
		}

		pass = false;
		System.out.println("How many points has " + name + " (" + team + ") scored?");

		// Prompts the user for Player points scored.
		while (!pass) {
			System.out.println("Please enter the amount of points " + name + " (" + team + ") has scored: ");

			if (scnr.hasNextInt()) {
				points = scnr.nextInt();
				if (points >= 0) { // A valid number of points, positive number
					pass = true;
					continue;
				}
			}
			scnr.nextLine();
			System.out.println("Invalid number of points.");
		}

		tree.insert(points, name, team); // Adds Player to the tree.
		System.out
				.println("Player " + name + " (" + team + ") has been added. They have scored " + points + " points.");
		scnr.nextLine();
	}

	/**
	 * Removes a player from the tree. This is done by using the tree's delete()
	 * method, which only requires the Player's name to do so (since two or more
	 * players can have the same amount of points).
	 * 
	 * @param scnr The scanner to read input
	 */
	@SuppressWarnings("unchecked")
	public static void removePlayer(Scanner scnr) {
		boolean pass = false;
		String name = "";

		for (int i = 0; i < 80; i++) {
			System.out.print("-");
		}

		System.out.println("\n---==+ Remove a Player +==---");

		// Prompts the user for Player name
		while (!pass) {
			System.out.println("Please enter the Player's name to be removed: ");
			name = scnr.nextLine();
			if (name.length() > 0) { // Valid name entered
				pass = true;
				continue;
			}
			System.out.println("Invalid name.");
		}

		System.out.println("Looking to delete " + name + "...");
		if (tree.search(tree.root, name) == null) {
			System.out.println("Player " + name + " could not be found.");
		} else {
			tree.delete(name);
			System.out.println("Player " + name + " was succesfully deleted.");
		}

	}

	/**
	 * This method prints out the contents for the (H) Help command, illustrating
	 * all the commands and their functions.
	 */
	public static void help() {
		for (int i = 0; i < 80; i++) {
			System.out.print("-");
		}

		System.out.println("\n---==+ HELP SECTION +==---");
		System.out.println("(I) Insert a player:\n - This command allows you to insert a Player into the database.\n - "
				+ "The program will prompt you for a Player's name, team initials, and points scored.");
		System.out.println("\n(R) Remove a player:\n - This command allows you to delete a Player from the database."
				+ "\n - When prompted, just specify a Players name and it will look to see if they are in database before removing.");
		System.out.println(
				"\n(D) Display all players:\n - This command allows you to display all the Players in the database."
						+ "\n - It will return all the Players names, teams, and points.");
		System.out
				.println("\n(G) Get a player:\n - This command allows you to retrieve information of a specific Player."
						+ "\n - When prompted, just specify a Players name and it will look up their information.");
		System.out.println("\n(H) Help section:\n - Shows all commands and information you are currently looking at.");
		System.out.println(
				"\n(?) Print menu:\n - If you forget a command, it brings up the main menu with the list of commands.");
		System.out.println("\n(E) Exit:\n - Gracefully exits the program.");
	}

	/**
	 * Main method, contains the driver and UI of the Basketball Points Organizer
	 * program.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		tree = new RedBlackTree<>();
		Scanner scnr = new Scanner(System.in);
		boolean quit = false;
		boolean printMenu = true;

		for (int i = 0; i < 80; i++) {
			System.out.print("-");
		}

		System.out.println("\nWelcome to the Basketball Points Organizer.\n"
				+ "How can I assist you today?\n(Select from the following options)\n");

		while (!quit) {
			if (printMenu) {
				System.out.println("(I) Insert a player\n(R) Remove a player\n(G) Get player information"
						+ "\n(D) Display all players\n(H) Help\n(?) Redisplay main menu\n(E) Exit");

				for (int i = 0; i < 80; i++) {
					System.out.print("-");
				}

				printMenu = false;
			}

			System.out.println("\n(Enter (?) for full menu, or (H) for help section)\nPlease enter your choice: ");
			char choice = readChar(scnr);

			switch (choice) {
			case ('i'):
				insertPlayer(scnr);
				break;
			case ('r'):
				removePlayer(scnr); // Double check
				break;
			case ('g'):
				getPlayerInfo(scnr);
				break;
			case ('d'):
				displayPlayers(); // Does not work currently
				break;
			case ('h'):
				help();
				break;
			case ('e'):
				quit = true;
				break;
			case ('?'):
				printMenu = true;
				break;
			default:
				System.out.println("\nInput not valid. Please enter a valid choice.");
				break;
			}

		}

		for (int i = 0; i < 80; i++) {
			System.out.print("-");
		}

		System.out.println("\nThank you using the Basketball Points Organizer. Goodbye.");
		System.exit(0);
	}
}
