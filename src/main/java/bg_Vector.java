/**
 * Outlander - Multiplayer Space Game | bg_Vector
 * by Kelvin Peng
 * W.T.Woodson H.S.
 * 2017
 * 
 * Position and rotation vector.
 */

public class bg_Vector{
   
   /**
    * Position in world plane.
    */
   private float x, y;
   
   /**
    * Rotation relative to world.
    */
   private float rot;
   
   /**
    * Constructor.
    */
   public bg_Vector(float x, float y, float rot){
      this.x = x;
      this.y = y;
      this.rot = rot;
   }
   
   /**
    * Get x-component of this.
    */
   public float getX(){
      return x;
   }
   
   /**
    * Get y-component of this.
    */
   public float getY(){
      return y;
   }
   
   /**
    * Get rotation component of this.
    */
   public float getRot(){
      return rot;
   }
   
   /**
    * Set x-component of this.
    */
   public void setX(float x){
      this.x = x;
   }
   
   /**
    * Set y-component of this.
    */
   public void setY(float y){
      this.y = y;
   }
   
   /**
    * Set rotation component of this.
    */
   public void setRot(float rot){
      this.rot = rot;
   }
   
   public void add(bg_Vector add){
      x += add.getX();
      y += add.getY();
      rot += add.getRot();
   }
   
   public void scale(float scale){
      x *= scale;
      y *= scale;
      rot *= scale;
   }
}