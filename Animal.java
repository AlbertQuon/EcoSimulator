/* [Animal.java]
 * Abstract class that creates basic information for all Animals
 * Albert Quon
 * 05/06/2018
 */

//random import
import java.util.Random;

abstract class Animal extends Organism{
  Random rand = new Random();
  private boolean male;
  private int age, stamina;
  
  /**
   * This method acts as a constructor for the Animal class, taking in a set health and stamina
   * @para int value of health and int value of stamina
   */
  Animal(int health, int stamina) {
    super(health);
    male = rand.nextBoolean();
    age = 0;
    this.stamina = stamina;
  }
  
  /**
   * This method acts as the default constructor for the Animal class
   */
  Animal() {
    super();
    age = 0;
    male = rand.nextBoolean();
    stamina = 2;
  }
  
  /**
   * This method acts as a getter for the age of the animal
   * @return the age of the object
   */
  public int getAge() {
    return this.age;
  }
  /**
   * This method acts as a setter for the object for the private age of the animal
   * @para the age to be set for the object
   */
  public void setAge(int age) {
    this.age = age;
  }
   
  /**
   * This method is a getter for the animal class to be used to return the value of its stamina
   * @return
   */
  public int getStamina() {
    return this.stamina;
  }
  
  /**
   * This method sets the private stamina variable of the animal class
   * @para the value for stamina to be set at
   * @return
   */
  public void setStamina(int amount) {
    this.stamina = amount;
  }
  
  /**
   * This method returns the value of the current gender variable of the animal class
   * @return boolean value of being male
   */
  boolean getGender() {
    return male;
  }
  /** 
   * This method is an abstract method that acts as a move method for the animal class
   * @para The 3x3 2-D vision array
   */
  abstract int move(Object[][] vision);
 
  /**
   * This method is an abstract method that would be used to interact with other organisms; specifically deleting them
   * @para Object that it would interact with
   */
  abstract void eat(Object organism);
  
  /**
   * This method is an abstract method that would be used to interact with other organisms; specifically creating new 
   * objects
   * @para Object that the class would interact with
   */
  abstract void breed(Object organism);
}