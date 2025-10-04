package edu.stanford.cs.svm;

import edu.stanford.cs.exp.Value;

public class SVMMethodClosure {
   private Value receiver;
   private String className;
   private String methodName;

   public SVMMethodClosure(Value receiver, String className, String methodName) {
      this.receiver = receiver;
      this.className = className;
      this.methodName = methodName;
   }

   public Value getReceiver() {
      return this.receiver;
   }

   public String getClassName() {
      return this.className;
   }

   public String getMethodName() {
      return this.methodName;
   }

   public String toString() {
      return this.className + "." + this.methodName;
   }
}
