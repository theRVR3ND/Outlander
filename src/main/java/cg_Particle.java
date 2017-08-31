/**
 * Outlander - Multiplayer Space Game | bg_Planet
 * by Kelvin Peng
 * W.T.Woodson H.S.
 * 2017
 * 
 * Planet entity in game world.
 */

import java.awt.*;

public class cg_Particle extends bg_Entity{
   
   private final byte size;
   
   private final Color color;
   
   public cg_Particle(short x, short y, short vX, short vY, byte size, Color color){
      super(x, y, (short)0);
      
      velocity = new bg_Vector(vX, vY, (short)0);
      this.size = size;
      this.color = color;
   }
   
   @Override
   public void think(final short deltaTime){
      super.think(deltaTime);
   }
   
   @Override
   public void render(Graphics2D g2, short relX, short relY){
      g2.setColor(color);
      g2.fillRect(
         cg_Client.SCREEN_WIDTH / 2 + relX,
         cg_Client.SCREEN_HEIGHT / 2 + relY,
         size,
         size
      );
   }
}