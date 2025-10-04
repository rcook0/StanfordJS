package edu.stanford.cs.java2js;

import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.xml.bind.DatatypeConverter;

public class JSImage extends BufferedImage {
   private String name;
   private static JPanel EMPTY_CONTAINER = new JPanel();
   private static HashMap<String, Image> imageTable = new HashMap();

   public JSImage(String src, int width, int height) {
      super(width, height, 2);
      this.getGraphics().drawImage(staticImage(src), 0, 0, width, height, (ImageObserver)null);
      loadImage(this);
   }

   public JSImage(String src) {
      this(src, staticWidth(src), staticHeight(src));
   }

   public String toString() {
      return this.name == null ? super.toString() : this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getName() {
      return this.name;
   }

   public static int[][] createPixelArray(int width, int height) {
      return new int[height][width];
   }

   public int[][] getPixelArray() {
      int width = this.getWidth(EMPTY_CONTAINER);
      int height = this.getHeight(EMPTY_CONTAINER);
      int[] pixels = new int[width * height];
      int[][] array = new int[height][width];
      PixelGrabber pg = new PixelGrabber(this.getSource(), 0, 0, width, height, pixels, 0, width);

      try {
         pg.grabPixels();
      } catch (InterruptedException var7) {
         throw new RuntimeException("Transfer interrupted");
      }

      if ((pg.getStatus() & 128) != 0) {
         throw new RuntimeException("Transfer aborted");
      } else {
         for(int i = 0; i < height; ++i) {
            System.arraycopy(pixels, i * width, array[i], 0, width);
         }

         return array;
      }
   }

   public static Image readDataImage(String url) {
      int p0 = url.indexOf(",") + 1;
      if (p0 == 0) {
         throw new RuntimeException("Malformed data URL");
      } else {
         byte[] data = DatatypeConverter.parseBase64Binary(url.substring(p0));
         return Toolkit.getDefaultToolkit().createImage(data);
      }
   }

   public static boolean isComplete(Image image) {
      return true;
   }

   public static void addActionListener(Image image, ActionListener listener) {
   }

   private static int staticWidth(String src) {
      return staticImage(src).getWidth((ImageObserver)null);
   }

   private static int staticHeight(String src) {
      return staticImage(src).getHeight((ImageObserver)null);
   }

   private static Image staticImage(String src) {
      Image image = (Image)imageTable.get(src);
      if (image != null) {
         return image;
      } else {
         String url = src;
         Object image;
         if (src.startsWith("data:")) {
            image = readDataImage(src);
         } else if (isReadable(src)) {
            try {
               image = ImageIO.read(new URL("file:" + src));
            } catch (IOException var6) {
               throw new RuntimeException(var6);
            }
         } else {
            String tail = src.substring(src.lastIndexOf(47) + 1);
            if (isReadable("images/" + tail)) {
               url = "file:images/" + tail;
            }

            try {
               image = ImageIO.read(new URL(url));
            } catch (IOException var5) {
               throw new RuntimeException(var5);
            }
         }

         loadImage((Image)image);
         imageTable.put(src, image);
         return (Image)image;
      }
   }

   private static boolean isReadable(String filename) {
      try {
         return (new File(filename)).canRead();
      } catch (Exception var2) {
         return false;
      }
   }

   private static void loadImage(Image image) {
      MediaTracker tracker = new MediaTracker(EMPTY_CONTAINER);
      tracker.addImage(image, 0);

      try {
         tracker.waitForID(0);
      } catch (InterruptedException var3) {
         throw new RuntimeException("Image loading process interrupted");
      }
   }
}
