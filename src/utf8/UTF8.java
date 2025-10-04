package edu.stanford.cs.utf8;

import java.io.UnsupportedEncodingException;

public class UTF8 {
   public static int[] encode(String str) {
      try {
         byte[] bytes = str.getBytes("UTF-8");
         int n = bytes.length;
         int w = (n + 4) / 4;
         int[] array = new int[w];
         int shift = 24;
         int offset = 0;

         for(int i = 0; i < n; ++i) {
            array[offset] |= (bytes[i] & 255) << shift;
            shift -= 8;
            if (shift < 0) {
               shift = 24;
               ++offset;
            }
         }

         return array;
      } catch (UnsupportedEncodingException var8) {
         throw new RuntimeException(var8.getMessage());
      }
   }

   public static String decode(int[] array) {
      return decode(array, 0);
   }

   public static String decode(int[] array, int offset) {
      int n = getByteLength(array, offset);
      byte[] bytes = new byte[n];
      int shift = 24;

      for(int i = 0; i < n; ++i) {
         bytes[i] = (byte)(array[offset] >> shift & 255);
         shift -= 8;
         if (shift < 0) {
            shift = 24;
            ++offset;
         }
      }

      try {
         return new String(bytes, "UTF-8");
      } catch (UnsupportedEncodingException var6) {
         throw new RuntimeException(var6.getMessage());
      }
   }

   private static int getByteLength(int[] array, int offset) {
      int shift = 24;

      int i;
      for(i = 0; (array[offset] >> shift & 255) != 0; ++i) {
         shift -= 8;
         if (shift < 0) {
            shift = 24;
            ++offset;
         }
      }

      return i;
   }
}
