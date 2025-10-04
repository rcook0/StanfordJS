package edu.stanford.cs.java2js;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class JSPackage {
   public abstract void init(Object var1);

   public static JSPackage load(String name, Object arg) {
      try {
         String suffix = name.substring(name.lastIndexOf(".") + 1);
         Class<?> c = Class.forName(name + ".Package_" + suffix);
         JSPackage pkg = (JSPackage)c.newInstance();
         pkg.init(arg);
         return pkg;
      } catch (Exception var5) {
         throw new RuntimeException("No package " + name);
      }
   }

   public static void load(String[] packages, Object arg) {
      String[] var5 = packages;
      int var4 = packages.length;

      for(int var3 = 0; var3 < var4; ++var3) {
         String name = var5[var3];
         load(name, arg);
      }

   }

   public static void require(String name, Object arg, ActionListener listener) {
      String[] packages = new String[]{name};
      require(packages, arg, listener);
   }

   public static void require(String[] packages, Object arg, ActionListener listener) {
      ActionEvent e = null;
      String name = "None";

      try {
         String[] var8 = packages;
         int var7 = packages.length;

         for(int var6 = 0; var6 < var7; ++var6) {
            String str = var8[var6];
            String suffix = str.substring(str.lastIndexOf(".") + 1);
            Class<?> c = Class.forName(str + ".Package_" + suffix);
            JSPackage pkg = (JSPackage)c.newInstance();
            pkg.init(arg);
         }

         e = JSEvent.createActionEvent(arg, "OK");
      } catch (Exception var12) {
         e = new JSErrorEvent(arg, "No package " + name);
      }

      JSEvent.dispatch(listener, (ActionEvent)e);
   }
}
