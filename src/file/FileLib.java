package edu.stanford.cs.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.TreeSet;

public class FileLib {
   public static String getHead(String path) {
      int slashIndex = -1;

      for(int i = 0; i < path.length(); ++i) {
         if (path.charAt(i) == '/' || path.charAt(i) == '\\') {
            slashIndex = i;
         }
      }

      if (slashIndex == -1) {
         return "";
      } else if (slashIndex == 0) {
         return path.substring(0, 1);
      } else {
         return path.substring(0, slashIndex);
      }
   }

   public static String getTail(String path) {
      int slashIndex = -1;

      for(int i = 0; i < path.length(); ++i) {
         if (path.charAt(i) == '/' || path.charAt(i) == '\\') {
            slashIndex = i;
         }
      }

      if (slashIndex == -1) {
         return path;
      } else {
         return path.substring(slashIndex + 1);
      }
   }

   public static String getRoot(String path) {
      int dotIndex = -1;

      for(int i = 0; i < path.length(); ++i) {
         switch(path.charAt(i)) {
         case '.':
            dotIndex = i;
            break;
         case '/':
         case '\\':
            dotIndex = -1;
         }
      }

      if (dotIndex == -1) {
         return path;
      } else {
         return path.substring(0, dotIndex);
      }
   }

   public static String getExtension(String path) {
      int dotIndex = -1;

      for(int i = 0; i < path.length(); ++i) {
         switch(path.charAt(i)) {
         case '.':
            dotIndex = i;
            break;
         case '/':
         case '\\':
            dotIndex = -1;
         }
      }

      if (dotIndex == -1) {
         return "";
      } else {
         return path.substring(dotIndex);
      }
   }

   public static void createDirectory(String path) {
      (new File(path)).mkdir();
   }

   public static void createDirectoryPath(String path) {
      (new File(path)).mkdirs();
   }

   public static String defaultExtension(String filename, String ext) {
      boolean force = ext.startsWith("*");
      if (force) {
         ext = ext.substring(1);
      }

      int dot = -1;
      int len = filename.length();

      for(int i = 0; i < len; ++i) {
         char ch = filename.charAt(i);
         if (ch == '.') {
            dot = i;
         }

         if (ch == '/' || ch == '\\') {
            dot = -1;
         }
      }

      if (dot == -1) {
         force = true;
         dot = len;
      }

      if (force) {
         return filename.substring(0, dot) + ext;
      } else {
         return filename;
      }
   }

   public static void deleteFile(String path) {
      (new File(path)).delete();
   }

   public static String expandPathname(String path) {
      try {
         return (new File(path)).getCanonicalPath();
      } catch (IOException var2) {
         throw new RuntimeException(var2.toString());
      }
   }

   public static boolean fileExists(String path) {
      return (new File(path)).exists();
   }

   public static BufferedReader openOnPath(String path, String filename) {
      String[] var5;
      int var4 = (var5 = splitPath(path)).length;
      int var3 = 0;

      while(var3 < var4) {
         String dir = var5[var3];
         String pathname = dir + "/" + filename;

         try {
            return new BufferedReader(new FileReader(pathname));
         } catch (IOException var8) {
            ++var3;
         }
      }

      return null;
   }

   public static String findOnPath(String path, String filename) {
      String[] var5;
      int var4 = (var5 = splitPath(path)).length;

      for(int var3 = 0; var3 < var4; ++var3) {
         String dir = var5[var3];
         String pathname = dir + "/" + filename;
         if ((new File(pathname)).exists()) {
            return pathname;
         }
      }

      return null;
   }

   public static String getCurrentDirectory() {
      return System.getProperty("user.dir");
   }

   public String getDirectoryPathSeparator() {
      return File.separator;
   }

   public static String getSearchPathSeparator() {
      return File.pathSeparator;
   }

   public boolean isDirectory(String path) {
      return (new File(path)).isDirectory();
   }

   public static boolean isFile(String path) {
      return (new File(path)).isFile();
   }

   public static String[] listDirectory(String path) {
      String[] list = (new File(path)).list();
      if (list == null) {
         return null;
      } else {
         TreeSet<String> tree = new TreeSet();
         String[] var6 = list;
         int var5 = list.length;

         for(int var4 = 0; var4 < var5; ++var4) {
            String name = var6[var4];
            if (!name.equals(".") && !name.equals("..")) {
               tree.add(name);
            }
         }

         return (String[])tree.toArray(new String[tree.size()]);
      }
   }

   public static BufferedReader openReader(String path) {
      try {
         return new BufferedReader(new FileReader(path));
      } catch (IOException var2) {
         throw new RuntimeException(var2.toString());
      }
   }

   public static PrintWriter openWriter(String path) {
      try {
         return new PrintWriter(new BufferedWriter(new FileWriter(path)));
      } catch (IOException var2) {
         throw new RuntimeException(var2.toString());
      }
   }

   private static String[] splitPath(String path) {
      ArrayList<String> list = new ArrayList();
      String sep = path.contains(";") ? ";" : ":";
      path = path + sep;
      int start = 0;

      while(true) {
         int finish = path.indexOf(sep, start);
         if (finish == -1) {
            return (String[])list.toArray(new String[list.size()]);
         }

         if (finish > start) {
            list.add(path.substring(start, finish));
         }

         start = finish + 1;
      }
   }
}
