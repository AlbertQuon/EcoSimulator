/* [Wolf.java]
 * Wolf class for all wolves in eco system
 * @author Albert Quon
 * 05/06/2018
 */

//random import
import java.util.Random;

public class Wolf extends Animal implements Comparable {
  Random rand = new Random(); // for random decisions
  
  /**
   * This method is the default constructor for the wolf class
   */
  Wolf() {
    super();
  }
  
  /**
   * This method acts as a constructor for the wolf class, taking in a set health and stamina
   * @para int value of health and int value of stamina
   */
  Wolf(int health, int stamina) {
    super(health, stamina);
  }
    
  /**
   * This method acts as an interaction method between a sheep class and a wolf class, resulting the sheep object to 
   * be null
   * @para The object that's considered to be a sheep
   */
  @Override
  void eat(Object animal) {
    if (((Organism)animal).getDisease()) { // if they ate something diseased, they get infected as well
      setDisease(((Organism)animal).getDisease());     
    }
    setHealth(getHealth() + ((Organism)animal).getHealth());
  } 
  
  /**
   * This method interacts with another object given from the vision array; breeding with another animal
   * @para the object to be interacted with; another wolf
   */
  @Override
  void breed(Object wolf) { 
    ((Organism)wolf).setHealth(((Organism)wolf).getHealth() - 10);
    setHealth(getHealth() - 10);
    setStamina(getStamina()-1);
  }
  
  /**
   * This method determines the animal's decision in the 2-D array, they can make random decisions to move, make specific
   * decisions to interact with other animals depending on what they are
   * @para a 2-D 3x3 array that's an area around the animal, relative to the bigger 2-D map
   * @return a integer value that determines their decision
   */
  @Override
  int move(Object[][] vision) {
    setHealth(getHealth()-1); //lose health when moving
    setStamina(getStamina()-1); //lose stamina when moving
    int moveNum; // the integer value that holds their decision
    
    vision[1][1] = null; // make the animal not interact with itself, VERY IMPORTANT
    
    for (int i = 0; i < vision[0].length; i++) {
      for (int j = 0; j < vision[0].length; j++) {
        if (((i != 2) && (j != 2)) || ((i != 0) && (j!=2)) || ((i != 0) && (j != 0)) || ((i != 2) && (j!=0))) {
          // animals must be opposite genders and fit to breed
          if ((vision[i][j] instanceof Wolf) && (((Organism)vision[i][j]).getHealth() > 20) && (getHealth() > 20)
                && (((Animal)vision[i][j]).getGender() == false) && (getGender() == true)) {
            breed(vision[i][j]); // interacting; spawning a new object
            return 10; // tells the main map to spawn a new wolf
          }
          
          if (vision[i][j] instanceof Sheep) { 
            //interaction based decision, therefore it'll return a value to move on top of the position of the 
            //interacting object
            eat(vision[i][j]); // interacting; eating the object
            vision[i][j] = null; // make it removed from vision
            if (i == 0) {
              return 6;
            }
            else if (i == 2) {
              return 5;
            }
            else if (j == 0) {
              return 8;
            }
            else if (j == 2) {
              return 7;
            }
          }
          
          if (vision[i][j] instanceof Wolf) { // wolves must fight, but as a last priority
            if (compareTo(vision[i][j]) == 1) {
              ((Organism)vision[i][j]).setHealth(((Organism)vision[i][j]).getHealth() - 10);
            }
            else if (compareTo(vision[i][j]) == -1) {
              setHealth(getHealth() - 10);
            } else {
              setHealth(getHealth() - 10);
              ((Organism)vision[i][j]).setHealth(((Organism)vision[i][j]).getHealth() - 10);
            }
            moveNum = rand.nextInt(4) + 5; // make the wolf randomly move away if possible
            return moveNum;
          }
          
          
        }
      }
    }
    // make a random decision to move anywhere, or don't move at all
    moveNum = rand.nextInt(5);
    if (moveNum == 0) {
      setStamina(getStamina()+1);
    }
    return moveNum;
  }
  
  /**
   * This method compares health variables of two objects
   * @para the object that's being compared to
   * @return value that determines the comparison
   */
  public int compareTo(Object animal) {
    int origWolf = getHealth(); //current wolf
    int secWolf = ((Organism)animal).getHealth(); //other wolf
    if (secWolf < origWolf) {
      return 1;
    }
    else if (secWolf > origWolf) {
      return -1;
    } else {
      return 0;
    }
  }
  
}