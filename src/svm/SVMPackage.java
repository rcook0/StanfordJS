package edu.stanford.cs.svm;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.java2js.JSPackage;

public abstract class SVMPackage extends JSPackage {
   public void init(Object obj) {
      if (obj != null) {
         this.defineClasses((SVM)obj);
         this.defineGlobals((SVM)obj);
      }

   }

   public void defineClasses(SVM svm) {
   }

   public void defineGlobals(SVM svm) {
   }

   public String[] getDependencies() {
      return new String[0];
   }

   public String[] getWrapper() {
      return new String[0];
   }

   public void defineClass(SVM svm, String name, SVMClass c) {
      SVMClass.defineClass(svm, name, c);
   }

   public void defineMethod(SVM svm, String name, String methodName) {
      int dot = methodName.indexOf(".");
      if (dot == -1) {
         throw new RuntimeException("Unqualified method name: " + methodName);
      } else {
         String className = methodName.substring(0, dot);
         methodName = methodName.substring(dot + 1);
         SVMMethodClosure obj = new SVMMethodClosure((Value)null, className, methodName);
         svm.setGlobal(name, Value.createObject(obj, "MethodClosure"));
      }
   }
}
