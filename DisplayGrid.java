/* [DisplayGrid.java]
 * A Small program for Display a 2D String Array graphically
 * @author Mangat
 */

// Graphics Imports
import javax.swing.*;
import java.awt.*;

class DisplayGrid { 

  private JFrame frame;
  private int maxX,maxY, GridToScreenRatio;
  private Object[][] world;
  private Boolean rainChance = false;

  DisplayGrid(Object[][] w) { 
    this.world = w;
    
    maxX = Toolkit.getDefaultToolkit().getScreenSize().width;
    maxY = Toolkit.getDefaultToolkit().getScreenSize().height;
    GridToScreenRatio = maxY / (world.length+1);  //ratio to fit in screen as square map
    
    System.out.println("Map size: "+world.length+" by "+world[0].length + "\nScreen size: "+ maxX +"x"+maxY+ " Ratio: " + GridToScreenRatio);
    
    this.frame = new JFrame("Map of World");
    
    GridAreaPanel worldPanel = new GridAreaPanel();
    
    frame.getContentPane().add(BorderLayout.CENTER, worldPanel);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
    frame.setVisible(true);
  }
  
  public void setRain(Boolean raining) {
    rainChance = raining; 
  }
  public void refresh() { 
    frame.repaint();
  }
  

  class GridAreaPanel extends JPanel {
   
    
    Image sheep, badSheep, oldSheep, badOldSheep;
    Image wolf, badWolf, oldWolf, badOldWolf;
    Image grass, badGrass;
    Image plain, rain, rainB;
   
    public GridAreaPanel() { 
      
      sheep = Toolkit.getDefaultToolkit().getImage("sheep.png");
      badSheep = Toolkit.getDefaultToolkit().getImage("badSheep.png");
      wolf = Toolkit.getDefaultToolkit().getImage("wolf.png");
      badWolf = Toolkit.getDefaultToolkit().getImage("badWolf.png");
      grass = Toolkit.getDefaultToolkit().getImage("grass.png");
      badGrass = Toolkit.getDefaultToolkit().getImage("badGrass.png");
      plain = Toolkit.getDefaultToolkit().getImage("plain.png");
      oldSheep = Toolkit.getDefaultToolkit().getImage("oldSheep.png");
      badOldSheep = Toolkit.getDefaultToolkit().getImage("badOldSheep.png");
      oldWolf = Toolkit.getDefaultToolkit().getImage("oldWolf.png");
      badOldWolf = Toolkit.getDefaultToolkit().getImage("badOldWolf.png");
      //rain = Toolkit.getDefaultToolkit().getImage("rain.png");
      rainB = Toolkit.getDefaultToolkit().getImage("rainB.png");
    }
    
    public void paintComponent(Graphics g) {        
      //super.repaint();
       int sheepFCount = 0, sheepMCount = 0, wolfMCount = 0, wolfFCount = 0, plantCount = 0, diseaseCount = 0;

      setDoubleBuffered(true); 
      g.setColor(Color.BLACK);
      
                    
      for(int i = 0; i<world[0].length;i=i+1)
      { 
        for(int j = 0; j<world.length;j=j+1) 
        { 
          
          if (world[i][j] instanceof Wolf) {   //This block can be changed to match character-color pairs
            if (((Animal)world[i][j]).getGender()) {
              wolfMCount++;
            } else {
              wolfFCount++;
            }
            if (((Organism)world[i][j]).getDisease()) {
              diseaseCount++;
              if (((Animal)world[i][j]).getAge() > 10) {
                g.drawImage(badOldWolf,j*GridToScreenRatio,i*GridToScreenRatio,GridToScreenRatio,GridToScreenRatio,this);
              } else {
                g.drawImage(badWolf,j*GridToScreenRatio,i*GridToScreenRatio,GridToScreenRatio,GridToScreenRatio,this);
              }
            }
            else if (((Animal)world[i][j]).getAge() > 10) {
              
              g.drawImage(oldWolf,j*GridToScreenRatio,i*GridToScreenRatio,GridToScreenRatio,GridToScreenRatio,this);
            } else {
              g.drawImage(wolf,j*GridToScreenRatio,i*GridToScreenRatio,GridToScreenRatio,GridToScreenRatio,this);
            }
            g.setColor(Color.RED);
            g.drawString(Integer.toString(((Animal)world[i][j]).getHealth()), j*GridToScreenRatio,i*GridToScreenRatio + 15);
            g.setColor(Color.GREEN);
            g.drawString(Integer.toString(((Animal)world[i][j]).getStamina()), j*GridToScreenRatio + 23,i*GridToScreenRatio+ 15);
          }
          else if (world[i][j] instanceof Sheep){
            if (((Animal)world[i][j]).getGender()) {
              sheepMCount++;
            } else {
              sheepFCount++;
            }
            if (((Organism)world[i][j]).getDisease()) {
              diseaseCount++;
              if (((Animal)world[i][j]).getAge() > 10) {
                g.drawImage(badOldSheep,j*GridToScreenRatio,i*GridToScreenRatio,GridToScreenRatio,GridToScreenRatio,this);
              } else {
                g.drawImage(badSheep,j*GridToScreenRatio,i*GridToScreenRatio,GridToScreenRatio,GridToScreenRatio,this);
              }
            }
            else if (((Animal)world[i][j]).getAge() > 10) {
              g.drawImage(oldSheep,j*GridToScreenRatio,i*GridToScreenRatio,GridToScreenRatio,GridToScreenRatio,this);
            } else {
              g.drawImage(sheep,j*GridToScreenRatio,i*GridToScreenRatio,GridToScreenRatio,GridToScreenRatio,this);
            }
            g.setColor(Color.RED);
            g.drawString(Integer.toString(((Animal)world[i][j]).getHealth()), j*GridToScreenRatio,i*GridToScreenRatio + 15);
            g.setColor(Color.GREEN);
            g.drawString(Integer.toString(((Animal)world[i][j]).getStamina()), j*GridToScreenRatio + 23,i*GridToScreenRatio + 15);
          }
          else if (world[i][j] instanceof Plant){
            plantCount++;
            if (((Organism)world[i][j]).getDisease()) {
              g.drawImage(badGrass, j*GridToScreenRatio,i*GridToScreenRatio,GridToScreenRatio,GridToScreenRatio,this);
            } else {
              g.drawImage(grass, j*GridToScreenRatio,i*GridToScreenRatio,GridToScreenRatio,GridToScreenRatio,this);
            }
            g.setColor(Color.ORANGE);
            g.drawString(Integer.toString(((Organism)world[i][j]).getHealth()), j*GridToScreenRatio + 15,i*GridToScreenRatio+ 15);
          }
          else {
            g.drawImage(plain, j*GridToScreenRatio,i*GridToScreenRatio,GridToScreenRatio,GridToScreenRatio,this);
          }
          
        }
      }
      if (rainChance == true) { // draw rain if it occurs
        g.drawImage(rainB, 0, 0, maxX /2 , maxY , this);
      }
      
      g.setColor(Color.BLACK);
      g.drawString("Male Sheep Count: " + sheepMCount, maxX / 2 + maxX / 4, GridToScreenRatio +100);
      g.drawString("Female Sheep Count: " + sheepFCount, maxX / 2 + maxX / 4, GridToScreenRatio + 120);
      g.drawString("Male Wolf Count: " + wolfMCount, maxX / 2 + maxX / 4, GridToScreenRatio + 140);
      g.drawString("Female Wolf Count: " + wolfFCount, maxX / 2 + maxX / 4, GridToScreenRatio + 160);
      g.drawString("Plant Count: " + plantCount, maxX / 2 + maxX / 4, GridToScreenRatio + 180);
      g.drawString("Diseased Animals: " + diseaseCount, maxX /2 + maxX /4, GridToScreenRatio + 200);
      
    }
    
  }//end of GridAreaPanel
  
} //end of DisplayGrid
