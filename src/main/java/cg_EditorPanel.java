/**
 * Outlander - Multiplayer Space Game | bg_EditorPanel
 * by Kelvin Peng
 * W.T.Woodson H.S.
 * 2017
 * 
 * Spacecraft editing interface.
 */

import java.util.*;
import javax.swing.*;
import java.awt.image.*;
import java.awt.*;
import java.awt.event.*;

public class cg_EditorPanel extends cg_Panel implements MouseListener,
                                                        MouseWheelListener,
                                                        MouseMotionListener,
                                                        bg_Parts,
                                                        bg_Constants{
   
   /**
    * Layout of currently modified ship.
    */
   private byte[][] layout;
   
   /**
    * Part currently being moved/dragged.
    */
   private byte currPart;
   
   /**
    * Scrollable list for placing spacecraft parts.
    */
   private ui_Table partsList;
   
   /**
    * Textbox for part description.
    */
   private ui_Table description;
   
   /**
    * Display for craft stats (mass, thrust, etc.)
    */
   private ui_Table craftStats;
   
   /**
    * Size, in pixels, of a grid thing for ship layout
    */
   final byte GRID_SIZE = (byte)(0.1 * cg_Client.SCREEN_HEIGHT);
   
   public static final BufferedImage[] partImages = new BufferedImage[] {
      util_Utilities.loadImage("parts/probe.png"),
      util_Utilities.loadImage("parts/engine.png"),
      util_Utilities.loadImage("parts/fuel_tank.png"),
      util_Utilities.loadImage("parts/laser.png"),
      util_Utilities.loadImage("parts/radar.png"),
      util_Utilities.loadImage("parts/antenna.png"),
      util_Utilities.loadImage("parts/battery.png"),
      util_Utilities.loadImage("parts/generator.png"),
      util_Utilities.loadImage("parts/node.png")
   };
   
   /**
    * Constructor.
    */
   public cg_EditorPanel(){
      //Initialize layout
      layout = new byte[7][5];
      for(byte r = 0; r < layout.length; r++)
         for(byte c = 0; c < layout[0].length; c++){
            layout[r][c] = -1;
         }
      currPart = -1;
      
      //Initialize parts list
      partsList = new ui_Table(
         0.05f, 0.15f, 0.27f, 0.2f,
         new String[] {"Name", "Inventory"},
         new float[] {0.06f, 0.25f}
      );
      ArrayList<String[]> cont = new ArrayList<String[]>(partImages.length);
      for(byte i = 0; i < NUM_PARTS; i++){
         cont.add(new String[] {partNames[i], "Number"});
      }
      partsList.setContents(cont);
      
      //Initialize part description list
      description = new ui_Table(
         0.05f, 0.45f, 0.27f, 0.4f,
         new String[] {"Description"},
         new float[] {0.06f}
      );
      
      //Read in descriptions
      String[] file = util_Utilities.readFromFile("parts/partInfo.cfg");
      
      //Put file in format for ui_Table.
      description.setContents(
         file,
         getFontMetrics(
            new Font("Century Gothic", Font.PLAIN, util_Utilities.getFontSize(4.0 / 5))
         )
      );
      
      //Initialize craft stats list
      craftStats = new ui_Table(
         0.68f, 0.15f, 0.27f, 0.4f,
         new String[] {"Parameter", "Value"},
         new float[] {0.69f, 0.9f}
      );
      updateStats();
      
      //Add listener
      addMouseListener(this);
      addMouseWheelListener(this);
      addMouseMotionListener(this);
   }
   
   @Override
   public void paintComponent(Graphics g){
      super.paintComponent(g);
      
      //Improve rendering quality
      Graphics2D g2 = util_Utilities.improveQuality(g);
      g2.setClip(0, 0, cg_Client.SCREEN_WIDTH, cg_Client.SCREEN_HEIGHT);
      
      //Default stuff
      g2.setColor(ui_Menu.BLUE);
      g2.fillRect(0, 0, cg_Client.SCREEN_WIDTH, cg_Client.SCREEN_HEIGHT);
      g2.setFont(new Font("Century Gothic", Font.PLAIN, util_Utilities.getFontSize(4.0 / 5)));
      
      //Draw grid thing
      g2.setColor(new Color(0, 0, 0, 125));
      for(byte r = 0; r < layout.length + 1; r++){
         g2.drawLine(
            (int)(0.5 * cg_Client.SCREEN_WIDTH - (layout[0].length / 2.0) * GRID_SIZE),
            (int)(0.5 * cg_Client.SCREEN_HEIGHT - (layout.length / 2.0 - r) * GRID_SIZE),
            (int)(0.5 * cg_Client.SCREEN_WIDTH + (layout[0].length / 2.0) * GRID_SIZE),
            (int)(0.5 * cg_Client.SCREEN_HEIGHT - (layout.length / 2.0 - r) * GRID_SIZE)
         );
      }
      for(byte c = 0; c < layout[0].length + 1; c++){
         g2.drawLine(
            (int)(0.5 * cg_Client.SCREEN_WIDTH - (layout[0].length / 2.0 - c) * GRID_SIZE),
            (int)(0.5 * cg_Client.SCREEN_HEIGHT - (layout.length / 2.0) * GRID_SIZE),
            (int)(0.5 * cg_Client.SCREEN_WIDTH - (layout[0].length / 2.0 - c) * GRID_SIZE),
            (int)(0.5 * cg_Client.SCREEN_HEIGHT + (layout.length / 2.0) * GRID_SIZE)
         );
      }
      
      //Draw current ship
      for(byte r = 0; r < layout.length; r++){
         for(byte c = 0; c < layout[0].length; c++){
            if(layout[r][c] >= 0){
               g2.drawImage(
                  partImages[layout[r][c]],
                  (int)(0.5 * cg_Client.SCREEN_WIDTH - (layout[0].length / 2.0 - c) * GRID_SIZE),
                  (int)(0.5 * cg_Client.SCREEN_HEIGHT - (layout.length / 2.0 - r) * GRID_SIZE),
                  GRID_SIZE,
                  GRID_SIZE,
                  null
               );
            }
         }
      }
      
      //Draw part currently being dragged
      if(currPart != -1){
         Point mousePos = MouseInfo.getPointerInfo().getLocation();
         g2.drawImage(
            partImages[currPart],
            (int)(mousePos.getX()) - GRID_SIZE / 2,
            (int)(mousePos.getY()) - GRID_SIZE / 2,
            GRID_SIZE,
            GRID_SIZE,
            null
         );
      }
      
      //Show lists
      partsList.draw(g2);
      description.draw(g2);
      craftStats.draw(g2);
      
      //Random info
      g2.drawString("[ESC] to exit editor", 10, 30);
      
      //Show chat stuff
      drawMessages(g2);
      
      repaint();
   }
   
   /**
    * Process key press.
    * 
    * @param e                   Event to process.
    */
   @Override
   public void keyPressed(KeyEvent e){
      super.keyPressed(e);
      
      //Return to game world
      if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
         //Send craft to server
         byte minR = (byte)layout.length,
              minC = (byte)layout[0].length,
              maxR = -1,
              maxC = -1;
         for(byte r = 0; r < layout.length; r++){
            for(byte c = 0; c < layout[0].length; c++){
               if(layout[r][c] != -1){
                  minR = (byte)Math.min(minR, r);
                  minC = (byte)Math.min(minC, c);
                  maxR = (byte)Math.max(maxR, r);
                  maxC = (byte)Math.max(maxC, c);
               }
            }
         }
         
         byte[] data = new byte[(maxR - minR + 1) * (maxC - minC + 1) + 3];
         data[0] = CRAFT;
         data[1] = (byte)(maxR - minR);
         data[2] = (byte)(maxC - minC);
         byte ind = 3;
         for(; minR <= maxR; minR++){
            for(; minC <= maxC; minC++){
               data[ind++] = layout[minR][minC];
            }
         }
         
         connection.writeOut(data);
         
         //Change to game display
         cg_Client.frame.setContentPane(gamePanel);
         gamePanel.requestFocus();
         cg_Client.frame.revalidate();
      }
   }
   
   @Override
   public void keyReleased(KeyEvent e){}
   
   @Override
   public void keyTyped(KeyEvent e){}
   
   /**
    * Process mouse press.
    * 
    * @param e                   Event to process.
    */
   @Override
   public void mousePressed(MouseEvent e){
      if(partsList.getHoverRow() >= 0){
         currPart = (byte)(partsList.getHoverRow() + partsList.getScrollInd());
      }
      
      //Check if press on ship layout area
      if(currPart == -1){
         byte pressC = (byte)(
            (e.getX() - (0.5 * cg_Client.SCREEN_WIDTH - (layout[0].length / 2.0) * GRID_SIZE)) / GRID_SIZE
         );
         byte pressR = (byte)(
            (e.getY() - (0.5 * cg_Client.SCREEN_HEIGHT - (layout.length / 2.0) * GRID_SIZE)) / GRID_SIZE
         );
         if(pressR >= 0 && pressR < layout.length && pressC >= 0 && pressC < layout[0].length){
            currPart = layout[pressR][pressC];
            layout[pressR][pressC] = -1;
         }
      }
   }
   
   /**
    * Process mouse release.
    * 
    * @param e                   Event to process.
    */
   @Override
   public void mouseReleased(MouseEvent e){
      //Check if can place part into ship grid thing
      if(currPart != -1){
         //Find grid coordiantes, relative to ship layout, of release
         byte releaseC = (byte)(
            (e.getX() - (0.5 * cg_Client.SCREEN_WIDTH - (layout[0].length / 2.0) * GRID_SIZE)) / GRID_SIZE
         );
         byte releaseR = (byte)(
            (e.getY() - (0.5 * cg_Client.SCREEN_HEIGHT - (layout.length / 2.0) * GRID_SIZE)) / GRID_SIZE
         );
         
         //Place part
         try{
            if(layout[releaseR][releaseC] == -1)
               layout[releaseR][releaseC] = currPart;
         }catch(ArrayIndexOutOfBoundsException ex){}
         
         currPart = -1;
      }
      partsList.setHoverRow((byte)-1);
   }
   
   @Override
   public void mouseClicked(MouseEvent e){
      
   }
   
   @Override
   public void mouseWheelMoved(MouseWheelEvent e){
      if(partsList.getHoverRow() != -1)
         partsList.checkScroll((short)e.getX(), (short)e.getY(), (byte)(e.getWheelRotation()));
      
      else if(e.getX() > description.getX() && e.getX() < description.getX() + description.getWidth() &&
              e.getY() > description.getY() && e.getY() < description.getY() + description.getHeight())
         description.checkScroll((short)e.getX(), (short)e.getY(), (byte)(e.getWheelRotation()));
   }
   
   @Override
   public void mouseDragged(MouseEvent e){}
   
   @Override
   public void mouseMoved(MouseEvent e){
      partsList.checkHover((short)e.getX(), (short)e.getY());
   }
   
   @Override
   public void mouseEntered(MouseEvent e){}
   
   @Override
   public void mouseExited(MouseEvent e){}
   
   private boolean shipIsLegal(){
      /*
      for(byte r = 0; r < layout.length; r++){
         for(byte c = 0; c < layout[0].length; c++){
            //Check if enough surrounding parts to be legal
            if(layout[r][c] == bg_Parts.FUEL_TANK){
               if(layout[r - 1][c] == -1 && layout[r + 1][c] == -1)
                  return false;
            
            }else if(layout[r][c] == bg_Parts.ENGINE || ship[r][c] == bg_Parts.DRILL){
               if(ship[r - 1][c] == -1)
                  return false;
            
            }else if(ship[r][c] == bg_Parts.ANTENNA){
               if(ship[r + 1][c] == -1)
                  return false;
            
            }else if(ship[r][c] == bg_Parts.FUEL_TANK){
               if(ship[r - 1][c] != -1 && ship[r + 1][c] != -1)
                  return false;
            
            }else{
               if(ship[r - 1][c] == -1 && ship[r][c - 1] == -1 &&
                  ship[r + 1][c] == -1 && ship[r][c + 1] == -1)
                  return false;
            }
         }
      }
      return true;
      */
      return true;
   }
   
   private void updateStats(){
      /*
      byte[] info = new byte[4];
      for(byte r = 0; r < layout.length; r++){
         for(byte c = 0; c < layout[0].length; c++){
            //Add mass
            info[0] += mass[layout[r][c]];
            
            //Add power required
            info[1] += power[layout[r][c]];
            
            //Add other stuff
            switch(layout[r][c]){
               case(FUEL_TANK):
                  info[2] += 500;
                  break;
               
               
            }
         }
      }
      
      craftStats.getContents.add(new String[] {"Mass",           "0 kg"});
      craftStats.getContents.add(new String[] {"Power Required", "0 kW"});
      craftStats.getContents.add(new String[] {"Thrust",         "0 kN"});
      craftStats.getContents.add(new String[] {"Fuel Capacity",  "0 kg"});
      craftStats.getContents.add(new String[] {"Power Storage",  "0 kWh"});
      craftStats.setContents(stats);
      */
   }
}