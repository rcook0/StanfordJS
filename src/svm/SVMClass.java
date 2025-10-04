package edu.stanford.cs.svm;

import edu.stanford.cs.exp.Value;
import java.util.HashMap;

public abstract class SVMClass {
   private static HashMap<String, SVMClass> classTable = null;
   private HashMap<String, SVMMethod> methodTable = new HashMap();

   public SVMMethod getMethod(String name) {
      SVMMethod m = (SVMMethod)this.methodTable.get(name);
      if (m == null) {
         throw new RuntimeException(name + " is not defined");
      } else {
         return m;
      }
   }

   public void defineMethod(String name, SVMMethod m) {
      this.methodTable.put(name, m);
   }

   public static boolean isDefined(String name) {
      return classTable.containsKey(name);
   }

   public static SVMClass forName(String name) {
      SVMClass c = (SVMClass)classTable.get(name);
      if (c == null) {
         throw new RuntimeException(name + " is not defined");
      } else {
         return c;
      }
   }

   public static void defineClass(SVM svm, String name, SVMClass c) {
      if (classTable == null) {
         classTable = new HashMap();
      }

      classTable.put(name, c);
      svm.setGlobal(name, Value.createObject(name, "Class"));
   }
}
