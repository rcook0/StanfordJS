package edu.stanford.cs.sjslib.util;

import edu.stanford.cs.svm.SVMClass;

public class SJSRandomClass extends SVMClass {
   public SJSRandomClass() {
      this.defineMethod("nextBoolean", new Random_nextBoolean());
      this.defineMethod("nextDouble", new Random_nextDouble());
      this.defineMethod("nextInt", new Random_nextInt());
      this.defineMethod("setSeed", new Random_setSeed());
   }
}
