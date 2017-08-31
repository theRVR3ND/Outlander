/**
 * Outlander - Multiplayer Space Game | bg_Player
 * by Kelvin Peng
 * W.T.Woodson H.S.
 * 2017
 * 
 * Player entity in game world.
 */

import java.awt.*;
import java.util.*;

public class bg_Player extends bg_Entity implements bg_Constants,
                                                    bg_Parts{
   
   /**
    * Player's name.
    */
   private String name;
   
   /**
    * Player's theme color.
    */
   private Color color;
   
   /**
    * ID of client that controlls this.
    */
   private byte controller;
   
   //OUTLANDER//
   
   /**
    * Spacecraft layout.
    */
   protected byte[][] layout;
   
   /**
    * Engine throttle level.
    */
   private byte throttle;
   
   /**
    * Spacecraft's total fuel amount.
    */
   private short fuel;
   
   /**
    * Number of parts player has.
    */
   private byte[] inventory;
   
   private static final byte[][] defaultLayout = new byte[][] {
      {0},
      {2},
      {1}
   };
   
   //*********//
   
   /**
    * Constructor.
    * 
    * @param name          Player name.
    * @param color         Player theme color.
    * @param controller    Controller's ID.
    */
   public bg_Player(String name, Color color, byte controller){
      super((short)0, (short)0, (short)0);
      this.name = name;
      this.color = color;
      this.controller = controller;
      
      layout = defaultLayout;
      throttle = 0;
      fuel = 60;
      inventory = new byte[NUM_PARTS];
   }
   
   /**
    * Constructor. Initialize to default values.
    */
   public bg_Player(){
      this(
         "",
         new Color(
            -Byte.MIN_VALUE,
            -Byte.MIN_VALUE,
            -Byte.MIN_VALUE
         ),
         (byte)0
      );
      
      layout = defaultLayout;
      throttle = 0;
      fuel = 0;
      inventory = new byte[NUM_PARTS];
   }
   
   /**
    * Update player.
    * 
    * @param deltaTime        Time (in milliseconds) since last think.
    */
   public void think(final short deltaTime){
      super.think(deltaTime);
      
      //Engine thrust acceleration
      final short force = (short)((throttle * 1.0 / Byte.MAX_VALUE) * ENGINE_THRUST);
      final short accel = (short)((deltaTime / 1000.0) * force / getMass());
      
      bg_Vector aVec = new bg_Vector(
         (float)(Math.cos(Math.toRadians(position.getRot() + 90)) * accel),
         (float)(Math.sin(Math.toRadians(position.getRot() + 90)) * accel),
         (float)0
      );
      velocity.add(aVec);
   }
      
   /**
    * Return player's name.
    */
   public String getName(){
      return name;
   }
   
   /**
    * Return player's theme color.
    */
   public Color getColor(){
      return color;
   }
   
   /**
    * Return ID of client that controls this.
    */
   public byte getController(){
      return controller;
   }
   
   /**
    * Return spacecraft layout.
    */
   public byte[][] getLayout(){
      return layout;
   }
   
   /**
    * Return current throttle level.
    */
   public byte getThrottle(){
      return throttle;
   }
   
   /**
    * Return current fuel amount.
    */
   public short getFuel(){
      return fuel;
   }
   
   public byte[] getInventory(){
      return inventory;
   }
   
   public short getMass(){
      short mass = 0;
      for(byte r = 0; r < layout.length; r++){
         for(byte c = 0; c < layout[0].length; c++){
            mass += partMass[layout[r][c]];
         }
      }
      return mass;
   }
   
   /**
    * Set player's in-game name.
    * 
    * @param name          New player name.
    */
   public void setName(String name){
      this.name = name;
   }
   
   /**
    * Set player's in-game theme color.
    * 
    * @param color         New player color.
    */
   public void setColor(Color color){
      this.color = color;
   }
   
   /**
    * Set spacecraft layout.
    * 
    * @param layout        New layout.
    */
   public void setLayout(byte[][] layout){
      this.layout = layout;
   }
   
   public void setInventory(byte[] inventory){
      this.inventory = inventory;
   }
   
   /**
    * Execute action based on action code.
    * 
    * @param action        Code of action to execute.
    */
   public void processAction(final byte action){
      //Value sensitivity
      final byte throttleSens = 2;
      
      switch(action){
         case(THROTTLE_UP):
            throttle = (byte)(Math.min(throttle + throttleSens, Byte.MAX_VALUE));
            break;
         
         case(THROTTLE_DOWN):
            throttle = (byte)(Math.max(throttle - throttleSens, 0));
            break;
         
         case(ROTATE_LEFT):
            position.setRot((short)(position.getRot() - 1));
            if(position.getRot() < -360)
               position.setRot((short)(position.getRot() + 360));
            break;
         
         case(ROTATE_RIGHT):
            position.setRot((short)(position.getRot() + 1));
            if(position.getRot() > 360)
               position.setRot((short)(position.getRot() - 360));
            break;
      }
      
      System.out.println((int)position.getRot());
   }
   
   /**
    * Return list of essential data of this player.
    * 
    * @param list          List to fill with data.
    */
   public LinkedList<Object> getData(LinkedList<Object> list){
      super.getData(list);
      
      //Add player-specific data
      list.add(name);
      list.add(color);
      list.add(controller);
      
      list.add(throttle);
      list.add(fuel);
      
      return list;
   }
   
   /**
    * Set player data from incomming wrapper objects.
    * 
    * @param data          New data to set to.
    */
   public void setData(LinkedList<Object> data){
      super.setData(data);
      
      //Retrieve player-specific data
      name = (String)(data.remove(0));
      color = (Color)(data.remove(0));
      controller = (Byte)(data.remove(0));
      
      throttle = (Byte)(data.remove(0));
      fuel = (Short)(data.remove(0));
   }
}