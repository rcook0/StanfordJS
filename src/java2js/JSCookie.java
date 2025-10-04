package edu.stanford.cs.java2js;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.TreeMap;

public class JSCookie {
   public static final String COOKIE_PATH = "/tmp/cookies.txt";

   public static void set(String name, String value) {
      TreeMap<String, String> cookies = readCookies();
      cookies.put(name, value);
      writeCookies(cookies);
   }

   public static void set(String name, String value, int days) {
      set(name, value);
   }

   public static void remove(String name) {
      TreeMap<String, String> cookies = readCookies();
      cookies.remove(name);
      writeCookies(cookies);
   }

   public static String get(String name) {
      return (String)readCookies().get(name);
   }

   public static String[] getNames() {
      TreeMap<String, String> cookies = readCookies();
      String[] names = new String[cookies.size()];
      int n = 0;

      String name;
      for(Iterator var4 = cookies.keySet().iterator(); var4.hasNext(); names[n++] = name) {
         name = (String)var4.next();
      }

      return names;
   }

   private static TreeMap<String, String> readCookies() {
      TreeMap<String, String> cookies = new TreeMap();
      BufferedReader rd = null;

      try {
         rd = new BufferedReader(new FileReader("/tmp/cookies.txt"));

         while(true) {
            String line = rd.readLine();
            if (line == null) {
               break;
            }

            int equals = line.indexOf("=");
            if (equals == -1) {
               rd.close();
               throw new RuntimeException("Illegal cookie file");
            }

            String name = line.substring(0, equals);
            String value = line.substring(equals + 1);
            cookies.put(name, value);
         }
      } catch (IOException var7) {
      }

      if (rd != null) {
         try {
            rd.close();
         } catch (IOException var6) {
         }
      }

      return cookies;
   }

   private static void writeCookies(TreeMap<String, String> cookies) {
      try {
         PrintWriter wr = new PrintWriter(new BufferedWriter(new FileWriter("/tmp/cookies.txt")));
         Iterator var3 = cookies.keySet().iterator();

         while(var3.hasNext()) {
            String name = (String)var3.next();
            String value = (String)cookies.get(name);
            wr.println(name + "=" + value);
         }

         wr.close();
      } catch (IOException var5) {
         throw new RuntimeException("Can't create cookie file");
      }
   }
}
