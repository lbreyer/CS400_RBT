// --== CS400 File Header Information ==--
// Name: Vedant Sagar
// Email: vsagar@wisc.edu
// Team: KF
// Role: Test Engineer
// TA: Sid Mohan
// Lecturer: Gary Dahl
// Notes to Grader: Namaste Brother!
/**
 * 
 * @author Vedant
 *
 */
public class Player implements Comparable<Player>{

  private String name;// Name of the player.
  private String team;// Name of the team the player represents.
  private int points;

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @return the team
   */
  public String getTeam() {
    return team;
  }

  /**
   * @return the points
   */
  public int getPoints() {
    return points;
  }

  /**
   * @param name
   * @param team
   * @param points
   */
  public Player(String name, String team, int points) {
    this.name = name;
    this.team = team;
    this.points = points;
  }
  
  public String toString() {
    return "- " + this.name + " (" + this.team + "): " + this.points;
  }
  
  @Override
  public int compareTo(Player p) {
    return this.name.compareTo(p.getName());
  }
  
  @Override
  public boolean equals(Object o) {
    Player p = (Player) o;
    return this.name.equals(p.getName());
  }

}
