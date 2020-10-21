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

  private String playerName;
  private String teamName;
  private Integer playerPoints;
  private Player player;
  private RBTExtension<Player> table;


  @BeforeEach
  public void initialiseTable() {
    this.table = new RBTExtension<Player>();
    this.playerName = "HarryKane";
    this.teamName = "TOT";
    this.playerPoints = 215;
    this.player = new Player(this.playerName, this.teamName, this.playerPoints);
  }

  /**
   * Tests if a player was successfully deleted from the RBT.
   */
  @Test
  public void testDelete() {

    // Adding a player to test with
    this.table.insert(this.player);

    this.playerName = "VedantSagar";
    this.teamName = "MUN";
    this.playerPoints = 150;
    this.player = new Player(this.playerName, this.teamName, this.playerPoints);
    this.table.insert(this.player);

    System.out.println(table.toString());

    // Test 1 - Removing an existing player
    Player expectedOutput = player;
    Player actualOutput = table.delete(this.player).data;
    assertEquals(expectedOutput, actualOutput);
    System.out.println(table.toString());

    // Test 2 - Removing a player that doesn't exist;
    assertEquals(null, table.delete(this.player));

    // Test 3 - Removing a player from an empty tree
    try {
      initialiseTable();
      table.delete(this.player);
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

      // Adding players to test with
      {
        this.table.insert(this.player);

        this.playerName = "VedantSagar";
        this.teamName = "MUN";
        this.playerPoints = 150;
        this.player = new Player(this.playerName, this.teamName, this.playerPoints);
        this.table.insert(this.player);
      }

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
    Player expectedOutput = null;
    Player actualOutput = null;
    assertEquals(null, table.search(table.root, this.player));

    // Test 2 - Searching for player that exists in the tree
    this.table.insert(this.player);

    this.playerName = "VedantSagar";
    this.teamName = "MUN";
    this.playerPoints = 150;
    this.player = new Player(this.playerName, this.teamName, this.playerPoints);
    this.table.insert(this.player);
    expectedOutput = player;
    actualOutput = table.search(table.root, this.player).data;
    assertEquals(expectedOutput, actualOutput);

    // Test 3 - Searching for player that doesn't exists in the tree
    Player player2 = new Player("HarryMaguire", "HUL", 420);
    assertEquals(null, table.search(table.root, player2));
  }
}
