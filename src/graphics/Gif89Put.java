package edu.stanford.cs.graphics;

import java.io.IOException;
import java.io.OutputStream;

final class Gif89Put {
   static void ascii(String s, OutputStream os) throws IOException {
      byte[] bytes = new byte[s.length()];

      for(int i = 0; i < bytes.length; ++i) {
         bytes[i] = (byte)s.charAt(i);
      }

      os.write(bytes);
   }

   static void leShort(int i16, OutputStream os) throws IOException {
      os.write(i16 & 255);
      os.write(i16 >> 8 & 255);
   }
}
