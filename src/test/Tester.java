public class Tester{
   
   public static void main(String[] args){
      /**********************************************************
      final byte[] s = new byte[] {-25, 15, 125, -125};
      final byte[] e = new byte[] {107, -5, -125, 125};
      final byte[] d = findDelta(s, e);
      
      byte[] res = new byte[s.length];
      for(byte i = 0; i < s.length; i++){
         res[i] = (byte)(s[i] + d[i]);
      }
      
      for(byte i = 0; i < s.length; i++)
         System.out.println(e[i] + "< " + d[i] + " >" + res[i]);
      //**********************************************************/
      
      //**********************************************************
      for(float i = Float.MIN_VALUE; i <= Float.MAX_VALUE; i++){
         byte[] bytes = floatToBytes(i);
         final float con = bytesToFloat(bytes, (byte)0);
         //int bits = Float.floatToIntBits(i);
         //   float crap = Float.intBitsToFloat(bits);
            
         if(con != i){
            System.out.println(i + " " + con);
            //System.exit(0);
            
         }
         //else
            //System.out.println("Sky Jesus");
      }
      //**********************************************************/
   }
   
   public static byte[] findDelta(byte[] start, byte[] end){
      byte[] res = new byte[end.length];
      
      for(byte i = 0; i < end.length; i++){
         if(Math.abs(end[i] - start[i]) < Byte.MAX_VALUE)
            res[i] = (byte)(end[i] - start[i]);
         //Deal with byte overflow
         else
            res[i] = (byte)((end[i] - start[i]) - (Byte.MAX_VALUE - Byte.MIN_VALUE) - 1);
      }
      
      return res;
   }
   
   public static byte[] shortToBytes(short val){
      return new byte[] {
         (byte)(val >> 8),
         (byte)(val & 0xFF)
      };
   }
   
   public static short bytesToShort(byte[] bytes, byte start){
      return (short)(bytes[start] << 8 |
                     bytes[start + 1] & 0xFF);
   }
   
   public static byte[] floatToBytes(float val){
      int bits = Float.floatToIntBits(val);
      return new byte[] {
         (byte)((bits >> 24) & 0xFF),
         (byte)((bits >> 16) & 0xFF),
         (byte)((bits >> 8) & 0xFF),
         (byte)(bits & 0xFF)
      };
   }
   
   public static float bytesToFloat(byte[] bytes, byte start){
      int bits = (int)((int)(bytes[start++]) << 24 |
                       (int)(bytes[start++]) << 16 |
                       (int)(bytes[start++]) << 8 |
                       (int)(bytes[start]) & 0xFF);
      return Float.intBitsToFloat(bits);
   }
}