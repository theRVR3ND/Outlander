/**
 * Kilo - Java Multiplayer Engine | ui_Settings
 * by Kelvin Peng
 * W.T.Woodson H.S.
 * 2017
 * 
 * Menu panel for changing game settings.
 */

import java.awt.*;
import java.awt.event.*;

public class ui_Settings extends ui_Menu{
   
   /**
    * Array of slider objects for modifying settings.
    */
   private ui_Slider[] sliders;
   
   /**
    * Constructor. Read previouse settings from text file.
    */
   public ui_Settings(){
      buttons = new ui_Button[] {
         new ui_Button(util_Utilities.loadImage("menu/ButtonBACK.png"),  0.5f, 0.85f)
      };
      
      sliders = new ui_Slider[] {
         new ui_Slider(0.4f, 0.3f, 0.2f, 0.02f, "Volume:", (short)0, (short)100), //Sound slider
      };
      
      //Load settings from file
      String[] settings = util_Utilities.readFromFile("menu/Settings.cfg");
      for(byte i = 0; i < settings.length; i++){
         sliders[i].setValue(Short.parseShort(settings[i]));
      }
   }
   
   /**
    * Draws panel contents.
    *
    * @param g                   Graphics object to draw into.
    */
   public void paintComponent(Graphics g){
      super.paintComponent(g);
      
      //Improve rendering quality
      Graphics2D g2 = util_Utilities.improveQuality(g);
      
      //Draw sliders
      for(byte i = 0; i < sliders.length; i++){
         sliders[i].draw(g2);
      }
      
      repaint();
   }
   
   /**
    * Process mouse click event. Write settings to file if
    * exiting menu.
    *
    * @param e                   MouseEvent to process.
    */
   public void mouseClicked(MouseEvent e){
      super.mouseClicked(e);
      
      //Redirect to other menus
      if(buttons[0].isDown()){
         cg_Client.frame.setContentPane(ui_Menu.setup);
         //Write settings to file
         String[] settings = new String[sliders.length];
         for(byte i = 0; i < sliders.length; i++){
            settings[i] = sliders[i].getValue() + "";
         }
         util_Utilities.writeToFile(settings, "menu/Settings.cfg");
      
      }else{
         return;
      }
      
      cg_Client.frame.revalidate();
   }
   
   public void mouseEntered(MouseEvent e){}
   
   public void mouseExited(MouseEvent e){}
   
   /**
    * Process mouse press event. Check if slider is pressed.
    *
    * @param e                   MouseEvent to process.
    */
   public void mousePressed(MouseEvent e){
      for(ui_Slider s : sliders)
         s.checkPress((short)e.getX(), (short)e.getY());
   }
   
   /**
    * Process mouse release event. Un-drag all sliders.
    *
    * @param e                   MouseEvent to process.
    */
   public void mouseReleased(MouseEvent e){
      for(ui_Slider s : sliders)
         s.release();
   }
   
   public void mouseMoved(MouseEvent e){
      super.mouseMoved(e);
   }
   
   /**
    * Process mouse drag event. Pass on coordinates to
    * sliders for slider movement.
    *
    * @param e                   MouseEvent to process.
    */
   public void mouseDragged(MouseEvent e){
      for(ui_Slider s : sliders)
         s.checkDrag((short)e.getX());
   }
}