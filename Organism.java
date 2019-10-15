/* [Organism.java]
 * Organism class to act as a super class for all organisms in eco system
 * @author Albert Quon
 * 05/06/2018
 */

abstract class Organism {
  private Boolean disease;
  private int health;
  
  /**
   * Default constructor for a new organism
   */
  Organism() {
    this.health = ((int)(Math.random()*10)+10);
    disease = false;
  }
  
  /**
   * Constructor for organism to set health
   * @para int value for initial health
   */
  Organism(int health) {
    this.health = health;
    disease = false;
  }
  
  /**
   * This method returns the value of private variable health
   * @return int value of health
   */
  public int getHealth(){
    return this.health;
  }
  
  /**
   * This method sets the private variable for health
   * @para int value of health
   */
  public void setHealth(int amount) {
    this.health = amount;
  }
  
  /**
   * This method returns the boolean value of disease, checking to see if animal is infected or not
   * @return Boolean value
   */
  public Boolean getDisease() {
    return this.disease;
  }
  
  /**
   * This method sets the private variable disease, making the animal diseased or not
   * @para Boolean value of the disease
   */
  public void setDisease(Boolean diseased) {
    this.disease = diseased;
  }
  
}