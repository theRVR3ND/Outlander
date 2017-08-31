/**
 * Outlander - Multiplayer Space Game | bg_Planet
 * by Kelvin Peng
 * W.T.Woodson H.S.
 * 2017
 * 
 * Planet entity in game world.
 */

import java.awt.*;

public class bg_Planet extends bg_Entity{
   
   /**
    * Radius of planet, in kilometers.
    */
   private final short radius;
   
   /**
    * Mass of planet, in 10^18 kilograms.
    */
   private final short mass;
   
   /**
    * Color of planet.
    */
   private final Color color;
   
   /**
    * Constructor.
    */
   public bg_Planet(short x, short y, short rot, short radius, short mass, Color color){
      super(x, y, rot);
      this.radius = radius;
      this.mass = mass;
      this.color = color;
   }
   
   public void think(final short deltaTime){
      super.think(deltaTime);
   }
   
   public void render(Graphics2D g2, short relX, short relY){
   
   }
   
   public short getRadius(){
      return radius;
   }
   
   public short getMass(){
      return mass;
   }
   
   public Color getColor(){
      return color;
   }
}