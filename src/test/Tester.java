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
      for(short i = Short.MIN_VALUE; i <= Short.MAX_VALUE; i++){
         byte[] bytes = shortToBytes(i);
         final short con = bytesToShort(bytes, (byte)0);
         if(con != i)
            System.out.println(i + " " + con);
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
}