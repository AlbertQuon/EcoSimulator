/* [EcoSimAssign.java]
 * Ecosystem simulator assignment, contains the main method to run
 * @author Albert Quon
 * 05/02/2018
 */

//importd
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;

class EcoSimAssign {
  
  /** 
   * Main method
   * @param arguments
   */
  public static void main(String[] args) { 
    
    Object map[][] = new Object[25][25];
    Scanner keyInput = new Scanner(System.in);
    Boolean extinction = false;
    int initialSheep, initialWolf, initialPlant, initialWHealth, initialSHealth; // initial values
    int day = 0, rain;
    Boolean toRain = false;
    
    System.out.println("Enter the amount of wolves (Recommended: 65, or anything < 80)");
    initialWolf = keyInput.nextInt();
    System.out.println("Enter the amount of sheep (Recommended: 60, or anything < 80)");
    initialSheep = keyInput.nextInt();
    System.out.println("Enter the amount of plants (Recommended: 50, or anything < 80)");
    initialPlant = keyInput.nextInt();
    System.out.println("Enter the initial health for wolves (Recommended: 20, or anything < 50)");
    initialWHealth = keyInput.nextInt();
    System.out.println("Enter the initial health for sheeps (Recommended: 15, or anything < 50)");
    initialSHealth = keyInput.nextInt();
    keyInput.close(); // to close input
    
    // Initialize Map
    generateMap(map, initialWolf, initialSheep, initialPlant, initialWHealth, initialSHealth);
    
    //Set up Grid Panel
    DisplayGrid grid = new DisplayGrid(map);
    
    //Display the grid on a Panel
    grid.refresh();
    
    //Small delay
    while(extinction != true) {
      try{ Thread.sleep(1500); }catch(Exception e) {};
      System.out.println("-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-");
      day++; // advance the day
      grid.setRain(false);
      toRain = false;
      Random rainChance = new Random();
      rain = rainChance.nextInt(5);
      if (rain == 1) {
        grid.setRain(true);
        toRain = true;
      }
      startNewDay(map); // change animal attributes
      extinction = advanceTime(map, toRain); // change positions
      System.out.println("Rain:" + toRain); // if it's raining or not
      grid.refresh();
      System.out.println("Day: " + day);
      System.out.println("-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-");
    }
    System.out.println("Extinction!");
  }
  
  
  /**
   * This generates objects in a given 2-D array
   * @param Object array of the map, the initial amounts of wolves, sheep, plants, wolf health, and sheep health
   */
  public static void generateMap(Object[][] map, int initWolf, int initSheep, int initPlant, int wolfHP, int sheepHP) { 
    int origWolf, origSheep, origPlant;
    do { // keeps randoming generating the map until all animals are placed, numbers greater than 80 are not recommended
      origWolf = initWolf;
      origSheep = initSheep;
      origPlant = initPlant;
      for(int i = 0; i<map[0].length;i++) {       
        for(int j = 0; j<map.length;j++) 
        { 
          int organism = 1 + ((int)(Math.random()* 23));
          if (organism < 3 && origWolf > 0) {
            map[i][j]= new Wolf(wolfHP, 2); 
            origWolf--;
          }
          else if (organism >= 3 && organism <= 7 && origSheep > 0) {
            map[i][j] = new Sheep(sheepHP, 2);
            origSheep--;
          }
          else if (organism > 7 && organism <= 11 && origPlant > 0) {
            map[i][j] = new Plant();
            origPlant--;
          } else {
            map[i][j] = null;
          }
        }
      }
    } while(origSheep != 0 || origWolf != 0 || origPlant != 0);
  }
  /** 
   * Copies an array from two 2-D arrays
   * @param the original 2-D object array, the copying 2-D array, the starting index of the orginal 2-D object array,
   * the end index of the original 2-D array of the second set of arrays, the specific map index to start for the copied array, 
   * the index to start for the second set of indexes in the copied array
   * @return Returns an object array that's copied
   */
  public static Object[] copyArray(Object[][] map, Object[][] mapCopy, int start, int end, int mapIndex, int mapCopyIndex, int startPara) {
    for (int i = start; i < end+1;i++) {
      mapCopy[mapCopyIndex][startPara] = map[mapIndex][i];
      startPara++;
    }
    return mapCopy[mapCopyIndex];
  }
  
  /** 
   * This method creates a 3x3 array that's copied from a larger array, where the original object would be in the middle
   * @param the original 2-D array map, the 3x3 array that's copying from the original, starting index of first set
   * of arrays, starting index of the second set of arrays
   * @return the finished object vision 2-D array that is 3x3
   */
  public static Object[][] visionArray(Object[][] origMap, Object[][] visionMap, int index, int indexB) {
    
    if (index == 0) { // if at the upper border
      
      if (indexB == 0){ // if at the left border
        visionMap[1] = copyArray(origMap, visionMap, indexB, indexB+1, index, 1, 1);
        visionMap[2] = copyArray(origMap, visionMap, indexB, indexB+1, index+1, 2, 1);
      }
      else if (indexB >= origMap[0].length-1) { // if at the right border
        visionMap[1] = copyArray(origMap, visionMap, indexB-1, indexB, index, 1, 0);
        visionMap[2] = copyArray(origMap, visionMap, indexB-1, indexB, index+1, 2, 0);
      }else {
        visionMap[1] = copyArray(origMap, visionMap, indexB-1, indexB+1, index, 1, 0);
        visionMap[2] = copyArray(origMap, visionMap, indexB-1, indexB+1, index+1, 2, 0);
      }
    }
    else if (index >= origMap[0].length-1) { // if at the bottom border
      
      if (indexB == 0){ // if at the left border
        visionMap[0] = copyArray(origMap, visionMap, indexB, indexB+1, index-1, 0, 1);
        visionMap[1] = copyArray(origMap, visionMap, indexB, indexB+1, index, 1, 1);
      }
      else if (indexB >= origMap[0].length-1) { // if at the right border
        visionMap[0] = copyArray(origMap, visionMap, indexB-1, indexB, index-1, 0, 0);
        visionMap[1] = copyArray(origMap, visionMap, indexB-1, indexB, index, 1, 0);
      }else {
        visionMap[0] = copyArray(origMap, visionMap, indexB-1, indexB+1, index-1, 0, 0);
        visionMap[1] = copyArray(origMap, visionMap, indexB-1, indexB+1, index, 1, 0);
      }
      
    }else {
      
      if (indexB == 0){ // if at the left border
        visionMap[0] = copyArray(origMap, visionMap, indexB, indexB+1, index-1, 0, 1);
        visionMap[1] = copyArray(origMap, visionMap, indexB, indexB+1, index, 1, 1);
        visionMap[2] = copyArray(origMap, visionMap, indexB, indexB+1, index+1, 2, 1);
      }
      else if (indexB >= origMap[0].length-1) { // if at the right border
        visionMap[0] = copyArray(origMap, visionMap, indexB-1, indexB, index-1, 0, 0);
        visionMap[1] = copyArray(origMap, visionMap, indexB-1, indexB, index, 1, 0);
        visionMap[2] = copyArray(origMap, visionMap, indexB-1, indexB, index+1, 2, 0);
      }else { // fully copy the map
        visionMap[0] = copyArray(origMap, visionMap, indexB-1, indexB+1, index-1, 0, 0);
        visionMap[1] = copyArray(origMap, visionMap, indexB-1, indexB+1, index, 1, 0);
        visionMap[2] = copyArray(origMap, visionMap, indexB-1, indexB+1, index+1, 2, 0);
      }
    }
    return visionMap;
  }
  
  /**
   * This method interacts with the objects in the 2-D array, however it does not modify their positions/indexes, 
   * only their attributes
   * @para the 2-D array containing the objects
   */
  public static void startNewDay(Object[][] map) {
    // variables for possible decomposition
    int grassChance;
    Random rand = new Random(); 
    
    for (int i = 0; i < map[0].length; i++) { // loop through the entire map to interact
      for (int j = 0; j < map[0].length; j++) {
        if (map[i][j] instanceof Plant) {
          if (((Organism)map[i][j]).getDisease()) { // plants with disease get worse over time
            ((Organism)map[i][j]).setHealth(((Organism)map[i][j]).getHealth() - 3); // bad plants are bad for animals
          }
        }
        if (map[i][j] instanceof Animal) {
          
          ((Animal)map[i][j]).setAge(((Animal)map[i][j]).getAge()+1); //they get older
          if (map[i][j] instanceof Wolf) {
            ((Animal)map[i][j]).setStamina(((Animal)map[i][j]).getStamina()+1); //wolves can move more in a day
          }
          if (((Animal)map[i][j]).getDisease()) {
            ((Animal)map[i][j]).setHealth(((Animal)map[i][j]).getHealth()-4); // infected animals lose more health
          }
          
          if ((((Animal)map[i][j]).getAge() > 16) && (((Animal)map[i][j]).getAge() <= 25)) {
            ((Animal)map[i][j]).setStamina(((Animal)map[i][j]).getStamina()+1); // as they get older, they move less
            ((Animal)map[i][j]).setHealth(((Animal)map[i][j]).getHealth()-12); // health problems when getting older
          }
          else if (((Animal)map[i][j]).getAge() > 25) { // animals will die of old age
            map[i][j] = null;
            
            // decomposition, chance of new plant
            grassChance = rand.nextInt(100);
            if (grassChance == 1) { 
              map[i][j] = new Plant();
              grassChance = rand.nextInt(8); 
              if (grassChance == 4) {
                ((Organism)map[i][j]).setDisease(true);
              }
            }
            
          } else {
            ((Animal)map[i][j]).setStamina(((Animal)map[i][j]).getStamina()+2); //animals can move twice or breed once
          }
        }
      }
    }
  }
  
  /**
   * This method interacts with all the objects by calling on their innate methods, either modifying position, or
   * rendering them null
   * @para The 2-D array of the objects, boolean value to determine the weather for the day
   * @return boolean value that determines if all animals are extinct
   */
  public static Boolean advanceTime(Object[][] map, Boolean raining) { 
    int decision, wolfCount = 0, sheepCount = 0, grassChance;
    Random rand = new Random();
    Object[][] moveMap = new Object[3][3];
    ArrayList<Object> wolves = new ArrayList<Object>(); 
    ArrayList<Object> sheeps = new ArrayList<Object>();
    
    for (int i = 0; i < map[0].length; i++) {
      for (int j = 0; j < map[0].length; j++) {
        
        if (map[i][j] == null) {
          if (raining = true) { // if it's raining, the chance for grass to spawn will be higher
            grassChance = rand.nextInt(250);
          } else {
            grassChance = rand.nextInt(1000); // or else, grass will randomly spawn
          }
          if (grassChance == 1) { //spawn the new grass
            map[i][j] = new Plant();
            grassChance = rand.nextInt(8); 
            if (grassChance == 4) { // chance for the grass to come with a disease
              ((Organism)map[i][j]).setDisease(true);
            }
          }
          
        }
        else if (map[i][j] instanceof Animal) {
          
          // arraylists to keep track of current species, and check if extinction occurs
          if ((map[i][j] instanceof Wolf) && (!wolves.contains(map[i][j])) && (((Animal)map[i][j]).getAge() > 0)) {
            wolves.add(map[i][j]); //add new wolves that are not new born and have not been recorded already
          }
          else if ((map[i][j] instanceof Sheep) && (!sheeps.contains(map[i][j])) && (((Animal)map[i][j]).getAge() > 0)) {
            sheeps.add(map[i][j]); //add new sheep that are not new born and have not been recorded already
          }
          
          //create a 3x3 array for vision of the animal at the current position
          visionArray(map, moveMap, i, j);
          
          if (((Animal)map[i][j]).getHealth() <= 0) { // animals with no health, die
            map[i][j] = null;
            
            // decomposition, chance of new plant
            grassChance = rand.nextInt(150);
            if (grassChance == 1) { 
              map[i][j] = new Plant();
              grassChance = rand.nextInt(5); 
              if (grassChance > 3) {
                ((Organism)map[i][j]).setDisease(true);
              }
            }
          }
          else if (((Animal)map[i][j]).getStamina() > 0) { // animals have satmina
            
            // determine the animal's decision based on the 3x3 vision around it
            decision = ((Animal)map[i][j]).move(moveMap); //insert array into move method

            //for decisions that are randomized but do not randomly let animals replace each other
            if ((decision == 1) && (i < map[0].length-1) && ((map[i+1][j] instanceof Plant) || (map[i+1][j] == null))) { 
              map[i+1][j] = map[i][j]; 
              map[i][j] = null;
            }
            else if ((decision == 2) && (i > 0)  && ((map[i-1][j] instanceof Plant) || (map[i-1][j] == null))) {
              map[i-1][j] = map[i][j];
              map[i][j] = null;
            }
            else if ((decision == 3) && (j < map[0].length-1) && ((map[i][j+1] instanceof Plant) || (map[i][j+1] == null))) {
              map[i][j+1] = map[i][j]; 
              map[i][j] = null;
            }
            else if ((decision == 4) && (j > 0) && ((map[i][j-1] instanceof Plant) || (map[i][j-1] == null))) {
              map[i][j-1] = map[i][j]; 
              map[i][j] = null;
            }
            
            // for specific decisions after interaction, allowing animals to be on top of other animals
            else if ((decision == 5) && (i < map[0].length-1)) {
              map[i+1][j] = map[i][j]; 
              map[i][j] = null;
            }
            else if ((decision == 6) && (i > 0)) {
              map[i-1][j] = map[i][j]; 
              map[i][j] = null;
            }
            else if ((decision == 7) && (j < map[0].length-1)) {
              map[i][j+1] = map[i][j]; 
              map[i][j] = null;
            }
            else if ((decision == 8) && (j > 0)) {
              map[i][j-1] = map[i][j]; 
              map[i][j] = null;
            }
            
            // for breeding purposes
            else if (decision == 9) {
              map = breedingGround(map, true);
              sheepCount++;
            }
            
            else if (decision == 10) {
              map = breedingGround(map, false);
              wolfCount++;
            }
          }
        } 
      }
    }
    
    wolfCount = wolves.size(); // current number of wolves
    sheepCount = sheeps.size(); // current number of sheep
    
    System.out.println("Sheep Count: " + sheepCount);
    System.out.println("Wolf Count: " + wolfCount);
    
    if ((sheepCount == 0) || (wolfCount == 0)) {
      return true; // extinction occured in a species
    } else {
      return false; // continue simulation
    }
  }
  /**
   * This method is for creating a new object of an animal when breeding has occured through random number
   * generation for random spawning
   * @para the 2-D map, the boolean value to determine the animal 
   * @return the modified object map with the new born
   */
  public static Object[][] breedingGround(Object[][] currentMap, Boolean isSheep) {
    Random rand = new Random();
    int randX, randY;
    Boolean spawn = false;
    
    do {
      randX = rand.nextInt(currentMap[0].length); // generate random coordinates
      randY = rand.nextInt(currentMap[0].length); // generate random coordinates
      if (currentMap[randX][randY] == null) { // spawn on an empty space
        spawn = true; //prevent spawning from occuring more than once
        if (isSheep) {
          currentMap[randX][randY] = new Sheep();
        } 
        else if (isSheep == false){ 
          currentMap[randX][randY] = new Wolf();
        }
      }
    } while(currentMap[randX][randY] != null && spawn == false); 
    
    return currentMap;
  }
}
