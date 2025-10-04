package edu.stanford.cs.graphics;

import java.awt.Component;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.image.ImageProducer;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.StringTokenizer;
import javax.swing.JPanel;
import javax.xml.bind.DatatypeConverter;

public class GImageTools {
   public static final String DEFAULT_IMAGE_PATH = ".:images";
   private static boolean cachingEnabled = false;
   private static HashMap<String, Image> imageTable = new HashMap();
   private static HashMap<String, ImageSaver> suffixTable = new HashMap();
   private static Component emptyContainer;

   protected GImageTools() {
   }

   public static Image loadImage(String name) {
      return loadImage(name, ".:images");
   }

   public static Image loadImage(String name, String path) {
      Image image = (Image)imageTable.get(name);
      if (image != null) {
         return image;
      } else if (name.startsWith("data:")) {
         return readDataImage(name);
      } else if (name.startsWith("http:")) {
         try {
            image = loadImage(new URL(name));
            if (cachingEnabled) {
               imageTable.put(name, image);
            }

            return image;
         } catch (MalformedURLException var7) {
            throw new RuntimeException("loadImage: Malformed URL");
         }
      } else {
         Toolkit toolkit = Toolkit.getDefaultToolkit();
         StringTokenizer tokenizer = new StringTokenizer(path, ":");

         while(image == null && tokenizer.hasMoreTokens()) {
            String prefix = tokenizer.nextToken();
            prefix = prefix.equals(".") ? "" : prefix + "/";

            try {
               if ((new File(prefix + name)).canRead()) {
                  image = toolkit.getImage(prefix + name);
               }
            } catch (SecurityException var8) {
            }
         }

         if (image == null) {
            throw new RuntimeException("Cannot find an image named " + name);
         } else {
            loadImage(image);
            if (cachingEnabled) {
               imageTable.put(name, image);
            }

            return image;
         }
      }
   }

   public static Image loadImage(URL url) {
      return loadImage(url, true);
   }

   public static Image loadImage(Image image) {
      MediaTracker tracker = new MediaTracker(getImageObserver());
      tracker.addImage(image, 0);

      try {
         tracker.waitForID(0);
         return image;
      } catch (InterruptedException var3) {
         throw new RuntimeException("Image loading process interrupted");
      }
   }

   public static void defineImage(String name, Image image) {
      imageTable.put(name, image);
   }

   public static void flushImage(String name) {
      imageTable.remove(name);
   }

   public static Image createImage(int[][] array) {
      int height = array.length;
      int width = array[0].length;
      int[] pixels = new int[width * height];

      for(int i = 0; i < height; ++i) {
         System.arraycopy(array[i], 0, pixels, i * width, width);
      }

      return createImage(pixels, width, height);
   }

   public static Image createImage(int[] pixels, int width, int height) {
      MemoryImageSource source = new MemoryImageSource(width, height, pixels, 0, width);
      Image image = Toolkit.getDefaultToolkit().createImage(source);
      return loadImage(image);
   }

   public static Image createImage(InputStream in) {
      try {
         ByteArrayOutputStream out = new ByteArrayOutputStream();

         for(int ch = in.read(); ch != -1; ch = in.read()) {
            out.write(ch);
         }

         byte[] array = out.toByteArray();
         Image image = Toolkit.getDefaultToolkit().createImage(array);
         return loadImage(image);
      } catch (Exception var4) {
         throw new RuntimeException("Exception: " + var4);
      }
   }

   public static int[][] getPixelArray(Image image) {
      Component observer = getImageObserver();
      int width = image.getWidth(observer);
      int height = image.getHeight(observer);
      int[] pixels = new int[width * height];
      int[][] array = new int[height][width];
      PixelGrabber pg = new PixelGrabber(image.getSource(), 0, 0, width, height, pixels, 0, width);

      try {
         pg.grabPixels();
      } catch (InterruptedException var8) {
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

   public static void saveImage(Image image, String filename) {
      saveImage(image, new File(filename));
   }

   public static void saveImage(Image image, File file) {
      String filename = file.getName();
      int dot = filename.lastIndexOf(46);
      if (dot <= 0) {
         throw new RuntimeException("saveImage: No image suffix in file name");
      } else {
         String suffix = filename.substring(dot + 1);
         ImageSaver saver = findImageSaver(suffix);
         if (saver == null) {
            throw new RuntimeException("saveImage: No support for ." + suffix + " format");
         } else if (file.exists() && !file.delete()) {
            throw new RuntimeException("saveImage: Cannot write " + filename);
         } else {
            try {
               OutputStream out = new BufferedOutputStream(new FileOutputStream(file));
               saver.setOutputStream(out);
               saver.saveImage(image);
               out.close();
            } catch (IOException var7) {
               throw new RuntimeException("saveImage: " + var7.getMessage());
            }
         }
      }
   }

   public static Image loadImage(URL url, boolean topLevel) {
      Image image = null;
      Toolkit toolkit = Toolkit.getDefaultToolkit();

      try {
         URLConnection connection = url.openConnection();
         if (connection.getContentLength() > 0) {
            Object content = connection.getContent();
            if (content instanceof ImageProducer) {
               image = toolkit.createImage((ImageProducer)content);
            } else if (content != null) {
               image = toolkit.getImage(url);
            }
         }
      } catch (IOException var6) {
      }

      if (topLevel) {
         if (image == null) {
            throw new RuntimeException("Cannot load image from " + url);
         }

         loadImage(image);
      }

      return image;
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

   private static ImageSaver findImageSaver(String suffix) {
      String cname = GImageTools.class.getName();
      String pkg = cname.substring(0, cname.lastIndexOf("."));
      suffix = suffix.toUpperCase();
      synchronized(suffixTable) {
         ImageSaver saver = (ImageSaver)suffixTable.get(suffix);
         if (saver == null) {
            try {
               cname = pkg + "." + suffix + "ImageSaver";
               Class<?> imageSaverClass = Class.forName(cname);
               saver = (ImageSaver)imageSaverClass.newInstance();
            } catch (Exception var6) {
               return null;
            }

            suffixTable.put(suffix, saver);
         }

         return saver;
      }
   }

   public static Component getImageObserver() {
      if (emptyContainer == null) {
         emptyContainer = new JPanel();
      }

      return emptyContainer;
   }
}
