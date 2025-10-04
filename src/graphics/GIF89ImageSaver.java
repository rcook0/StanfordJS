package edu.stanford.cs.graphics;

import java.awt.Image;
import java.io.IOException;

class GIF89ImageSaver extends ImageSaver {
   public void saveImage(Image image) {
      try {
         Gif89Encoder encoder = new Gif89Encoder(image);
         encoder.setTransparentIndex(0);
         encoder.getFrameAt(0).setInterlaced(true);
         encoder.encode(this.getOutputStream());
      } catch (IOException var3) {
         throw new RuntimeException("saveImage: " + var3.getMessage());
      }
   }
}
