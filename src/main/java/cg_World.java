/**
 * Outlander - Multiplayer Space Game | cg_World
 * by Kelvin Peng
 * W.T.Woodson H.S.
 * 2017
 * 
 * Client-side version of world. Handle client-only actions.
 */

import java.awt.*;
import java.util.*;
import java.awt.image.*;
import java.awt.geom.*;

public class cg_World extends bg_World{
   
   /**
    * Most recent gamestate received from server.
    */
   private HashMap<Short, byte[]> gamestate;
   
   private final BufferedImage[] imageCache = new BufferedImage[]{
      util_Utilities.loadImage("panel/ball.png"),
      util_Utilities.loadImage("panel/panel.png")
   };
   
   /**
    * Constructor.
    */
   public cg_World(){
      super();
      
      //Initialize stuff
      gamestate = new HashMap<Short, byte[]>();
   }
   
   /**
    * Update visual components of world
    */
   public void think(){
      super.think();
      
      //Add particles
   }
   
   /**
    * Render world contents.
    * 
    * @param g2               Graphics object to render into.
    */
   public void render(Graphics2D g2){
      //Graphics defaults
      g2.setColor(Color.BLACK);
      g2.fillRect(0, 0, cg_Client.SCREEN_WIDTH, cg_Client.SCREEN_HEIGHT);
      g2.setClip(0, 0, cg_Client.SCREEN_WIDTH, cg_Client.SCREEN_HEIGHT);
      final AffineTransform orig = g2.getTransform();
      
      //Figure out who we are
      bg_Player us = getPlayer(cg_Panel.connection.getClientID());
      
      //Check if world has been initialized yet
      if(us == null)
         return;
      
      //Draw all entities
      for(Short key : entities.keySet()){
         bg_Entity ent = entities.get(key);
         
         final short relX = (short)(ent.getPosition().getX() - us.getPosition().getX()),
                     relY = (short)(ent.getPosition().getY() - us.getPosition().getY());
         
         //Check if in view area
         if(Math.abs(relX) < VIEW_WIDTH && Math.abs(relY) < VIEW_HEIGHT){
            ent.render(g2, relX, relY);
         }
      }
      
      //Draw navball
      AffineTransform rotate = new AffineTransform();
      rotate.setToRotation(
         Math.toRadians(-us.getPosition().getRot()),
         cg_Client.SCREEN_WIDTH / 2,
         cg_Client.SCREEN_HEIGHT - 100
      );
      
      g2.setTransform(rotate);
      
      g2.drawImage(
         imageCache[0],
         cg_Client.SCREEN_WIDTH / 2 - imageCache[0].getWidth() / 2,
         cg_Client.SCREEN_HEIGHT - 175,
         null
      );
      
      g2.setTransform(orig);
      
      //Draw center console panel
      g2.drawImage(
         imageCache[1],
         cg_Client.SCREEN_WIDTH / 2 - imageCache[1].getWidth() / 2,
         cg_Client.SCREEN_HEIGHT - imageCache[1].getHeight(),
         null
      );
      
      //Draw fuel level
      g2.setColor(Color.GREEN);
      short barHeight = (short)(140 * (1.0 * us.getFuel() / Short.MAX_VALUE));
      g2.fillRect(
         cg_Client.SCREEN_WIDTH / 2 - 168,
         cg_Client.SCREEN_HEIGHT - 6 - barHeight,
         28,
         barHeight
      );
      
      //Draw throttle level
      barHeight = (short)(140 * (1.0 * us.getThrottle() / Byte.MAX_VALUE));
      g2.fillRect(
         cg_Client.SCREEN_WIDTH / 2 + 138,
         cg_Client.SCREEN_HEIGHT - 6 - barHeight,
         28,
         barHeight
      );
   }
   
   /**
    * Set data of particular entity.
    * 
    * @param delta            Change in data to execute.
    */
   public void setData(byte[] delta){
      //Retreive entity's ID from bytes
      short ID = bytesToShort(delta, (byte)0);
      
      //Check if we need to spawn a new entity
      if(entities.get(ID) == null){
         bg_Entity spawn = null;
         byte entType = delta[2];
         
         if(entType == PLAYER)
            spawn = new cg_Player();
         
         entities.put(ID, spawn);
      }
      
      //Clip off ID and entity type info
      byte[] clipped = new byte[delta.length - 3];
      for(byte i = 0; i < clipped.length; i++)
         clipped[i] = delta[i + 3];
      
      //Expand compressed data
      delta = expand(clipped);
      
      //Find last official info on entity
      byte[] data;
      LinkedList<Object> entObj = entities.get(ID).getData(new LinkedList<Object>());
      
      if(gamestate.containsKey(ID))
         data = gamestate.get(ID);
      else
         data = dataToBytes(entObj);
      
      //Add delta to current data
      for(byte i = 0; i < data.length; i++)
         data[i] += delta[i];
      
      //Set entity's data to new byte data
      entities.get(ID).setData(bytesToData(
         data, entObj
      ));
      
      gamestate.put(ID, data);
   }
}