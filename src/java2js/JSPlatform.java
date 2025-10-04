package edu.stanford.cs.java2js;

import javax.swing.UIManager;

public class JSPlatform {
   public static void exit(int status) {
      System.exit(status);
   }

   public static boolean isJavaScript() {
      return false;
   }

   public static boolean elementExists(String id) {
      throw new RuntimeException("elementExists called from Java");
   }

   public static boolean isMacOSX() {
      return System.getProperty("os.name").equals("Mac OS X");
   }

   public static String[] splitLines(String text) {
      return text.split("\\r?\\n|\\r");
   }

   public static void printStackTrace() {
      printStackTrace(new Throwable());
   }

   public static void printStackTrace(Throwable ex) {
      ex.printStackTrace();
   }

   public static int getScrollBarWidth() {
      return UIManager.getInt("ScrollBar.width");
   }
}
