package edu.stanford.cs.graphics;

import java.awt.Image;
import javax.imageio.ImageIO;

class GIFImageSaver extends ImageSaver {
   private GIF89ImageSaver gif89Saver;

   public GIFImageSaver() {
      super("GIF", 2);
      if (!ImageIO.getImageWritersBySuffix("gif").hasNext()) {
         this.gif89Saver = new GIF89ImageSaver();
      }

   }

   public void saveImage(Image image) {
      if (this.gif89Saver != null) {
         this.gif89Saver.setOutputStream(this.getOutputStream());
         this.gif89Saver.saveImage(image);
      } else {
         super.saveImage(image);
      }

   }
}
