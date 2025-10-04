package edu.stanford.cs.graphics;

import java.awt.AlphaComposite;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import javax.imageio.ImageIO;
import javax.imageio.stream.MemoryCacheImageOutputStream;

class ImageSaver {
   private OutputStream out;
   private String formatName;
   private int bufferType;

   public ImageSaver() {
   }

   public ImageSaver(String format, int type) {
      this.formatName = format;
      this.bufferType = type;
   }

   public void setOutputStream(OutputStream output) {
      this.out = output;
   }

   public OutputStream getOutputStream() {
      return this.out;
   }

   public void saveImage(Image image) {
      BufferedImage bi = createBufferedImage(image, this.bufferType);
      MemoryCacheImageOutputStream ios = new MemoryCacheImageOutputStream(this.out);

      try {
         if (!ImageIO.write(bi, this.formatName, ios)) {
            throw new IOException("ImageIO.write failed");
         } else {
            ios.close();
         }
      } catch (IOException var5) {
         throw new RuntimeException("saveImage: " + var5.getMessage());
      }
   }

   public void dumpByte(int x) {
      try {
         this.out.write(x);
      } catch (IOException var3) {
         throw new RuntimeException("saveImage: " + var3.getMessage());
      }
   }

   public void dumpShort(int x) {
      try {
         this.out.write(x >> 8);
         this.out.write(x);
      } catch (IOException var3) {
         throw new RuntimeException("saveImage: " + var3.getMessage());
      }
   }

   public void dumpInt(int x) {
      try {
         this.out.write(x >> 24);
         this.out.write(x >> 16);
         this.out.write(x >> 8);
         this.out.write(x);
      } catch (IOException var3) {
         throw new RuntimeException("saveImage: " + var3.getMessage());
      }
   }

   public int getPixelComponent(int pixel, char color) {
      int alpha = pixel >> 24 & 255;
      switch(color) {
      case 'B':
         break;
      case 'G':
         pixel >>= 8;
         break;
      case 'R':
         pixel >>= 16;
         break;
      default:
         throw new RuntimeException("Illegal color");
      }

      pixel &= 255;
      return (alpha * pixel + (255 - alpha) * 255) / 255;
   }

   public static BufferedImage createBufferedImage(Image image, int type) {
      Component observer = GImageTools.getImageObserver();
      int width = image.getWidth(observer);
      int height = image.getHeight(observer);
      BufferedImage bimage = new BufferedImage(width, height, type);
      Graphics2D g = bimage.createGraphics();
      switch(type) {
      case 2:
      case 3:
      case 6:
      case 7:
         g.setComposite(AlphaComposite.Src);
      case 4:
      case 5:
      default:
         g.drawImage(image, 0, 0, observer);
         g.dispose();
         return bimage;
      }
   }
}
