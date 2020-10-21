// --== CS400 File Header Information ==--
// Name: Vedant Sagar
// Email: vsagar@wisc.edu
// Team: KF
// Role: Test Engineer
// TA: Sid Mohan
// Lecturer: Gary Dahl
// Notes to Grader: Namaste Brother

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * This class tests the functionality of our code.
 * 
 * @author Vedant Sagar
 */
class TestPointsTree {
	private RBTExtension<Integer> table;

	@BeforeEach
	public void initialiseTable() {
		this.table = new RBTExtension<Integer>();
	}

	/**
	 * Tests if a player was successfully deleted from the RBT.
	 */
	@Test
	public void testDelete() {

		// Adding a player to test with
		this.table.insert(215, "Harry Kane", "LAL");
		this.table.insert(150, "Vedant Sagar", "BOS");

		System.out.println(table.toString());

		// Test 1 - Removing an existing player
		RedBlackTree.Node<Integer> node = table.delete("Vedant Sagar");
		assertEquals(node.name, "Vedant Sagar");
		assertEquals(node.team, "BOS");
		assertEquals(node.data, 150);
		System.out.println(table.toString());

		// Test 2 - Removing a player that doesn't exist;
		assertEquals(null, table.delete("LeBron James"));

		// Test 3 - Removing a player from an empty tree
		try {
			initialiseTable();
			table.delete("Vedant Sagar");
			fail("Delete method did not throw NullPointerException");
		} catch (NullPointerException npe) {
			if (!npe.getMessage().equals("This RedBlackTree is empty."))
				fail("Null pointer exception does not have appropriate error message");
		} catch (Exception e) {
			fail("Delete method does not throw right error message");
		}

		// Test 4 - Passing a null value into delete
		try {
			initialiseTable();
			this.table.insert(215, "Harry Kane", "LAL"); // Adding players to test with
			this.table.insert(150, "Vedant Sagar", "BOS");
			table.delete(null);
			fail("Delete method did not throw NullPointerException");
		} catch (NullPointerException npe) {
			if (!npe.getMessage().equals("This RedBlackTree cannot remove null references."))
				fail("Null pointer exception does not have appropriate error message");
		} catch (Exception e) {
			fail("Delete method does not throw right error message");
		}
	}

	/**
	 * Tests if the minimum() works correctly.
	 */
	@Test
	public void testSearch() {

		// Test 1 - Searching for player in an empty tree
		assertEquals(null, table.search(table.root, "Dont Exist"));

		// Test 2 - Searching for player that exists in the tree
		this.table.insert(215, "Harry Kane", "LAL"); // Adding players to test with
		this.table.insert(150, "Vedant Sagar", "BOS");
		RedBlackTree.Node<Integer> node = table.search(table.root, "Vedant Sagar");
		RedBlackTree.Node<Integer> node2 = table.search(table.root, "Harry Kane");
		
		// Test first insert
		assertEquals(node.name, "Vedant Sagar");
		assertEquals(node.team, "BOS");
		assertEquals(node.data, 150);
		
		// Test second insert
		assertEquals(node2.name, "Harry Kane");
		assertEquals(node2.team, "LAL");
		assertEquals(node2.data, 215);

		// Test 3 - Searching for player that doesn't exists in the tree
		assertEquals(null, table.search(table.root, "Doesnt Exist"));
	}
}
