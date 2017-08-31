/**
 * Outlander - Multiplayer Space Game | bg_Entity
 * by Kelvin Peng
 * W.T.Woodson H.S.
 * 2017
 * 
 * Entity in game world.
 */

import java.util.*;
import java.awt.*;

public abstract class bg_Entity{
   
   /**
    * Position of this in world.
    */
   protected bg_Vector position;
   
   /**
    * Velocity of entity.
    */
   protected bg_Vector velocity;
   
   /**
    * Running total of number of entities instantiated.
    */
   private static short entityCount = 0;
   
   /**
    * Constructor.
    */
   public bg_Entity(short x, short y, short rot){
      position = new bg_Vector(x, y, rot);
      velocity = new bg_Vector((short)0, (short)0, (short)0);
      entityCount++;
   }
   
   /**
    * Update entity.
    * 
    * @param deltaTime        Time (in milliseconds) since last think.
    */
   public void think(final short deltaTime){
      position.add(velocity);
   }
   
   public void render(Graphics2D g2, short relX, short relY){}
   
   /**
    * Return position of this.
    */
   public bg_Vector getPosition(){
      return position;
   }
   
   /**
    * Return velocity of this.
    */
   public bg_Vector getVelocity(){
      return velocity;
   }
   
   /**
    * Return count of number of entities initialized.
    */
   public static short getEntityCount(){
      return entityCount;
   }
   
   /**
    * Return list of essential data of this entity.
    * 
    * @param list             List to put data into.
    */
   public LinkedList<Object> getData(LinkedList<Object> list){
      list.add(position.getX());
      list.add(position.getY());
      list.add(position.getRot());
      
      list.add(velocity.getX());
      list.add(velocity.getY());
      list.add(velocity.getRot());
      
      return list;
   }
   
   /**
    * Set entity data from incomming wrapper objects.
    * 
    * @param data             Changes in data to process.
    */
   public void setData(LinkedList<Object> data){
      position.setX((Float)data.remove(0));
      position.setY((Float)data.remove(0));
      position.setRot((Float)data.remove(0));
      
      velocity.setX((Float)data.remove(0));
      velocity.setY((Float)data.remove(0));
      velocity.setRot((Float)data.remove(0));
   }
}