/**
 * Outlander - Multiplayer Space Game | bg_Player
 * by Kelvin Peng
 * W.T.Woodson H.S.
 * 2017
 * 
 * Player entity in game world.
 */

import java.awt.*;
import java.awt.geom.*;

public class cg_Player extends bg_Player{

   public void render(Graphics2D g2, short relX, short relY){
      //Find spacecraft center of mass
      final byte partSize = (byte)(0.05 * cg_Client.SCREEN_HEIGHT);
      
      byte[] centerOfMass = new byte[2];
      final short totalMass = getMass();
      
      short temp = 0;
      for(byte r = 0; r < layout.length; r++){
         for(byte c = 0; c < layout[0].length; c++){
            temp += partMass[layout[r][c]];
         }
         if(temp >= totalMass / 2){
            centerOfMass[0] = r;
            temp = 0;
            break;
         }
      }
      
      for(byte c = 0; c < layout[0].length; c++){
         for(byte r = 0; r < layout.length; r++){
            temp += partMass[layout[r][c]];
         }
         if(temp >= totalMass / 2){
            centerOfMass[1] = c;
            break;
         }
      }
      
      //Draw craft
      AffineTransform rotate = new AffineTransform();
      rotate.setToRotation(
         Math.toRadians(position.getRot()),
         cg_Client.SCREEN_WIDTH / 2,
         cg_Client.SCREEN_HEIGHT / 2
      );
      g2.setTransform(rotate);
      
      for(byte r = 0; r < layout.length; r++){
         for(byte c = 0; c < layout[0].length; c++){
            g2.drawImage(
               cg_EditorPanel.partImages[layout[r][c]],
               (int)(cg_Client.SCREEN_WIDTH / 2 + relX + (c - centerOfMass[1] - 0.5) * partSize),
               (int)(cg_Client.SCREEN_HEIGHT / 2 + relY + (r - centerOfMass[0] - 0.5) * partSize),
               partSize,
               partSize,
               null
            );
         }
      }
      
      //Add particle effects
   }
}