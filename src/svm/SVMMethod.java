package edu.stanford.cs.svm;

import edu.stanford.cs.exp.Value;

public abstract class SVMMethod {
   public abstract void execute(SVM var1, Value var2);

   public boolean isConstant() {
      return false;
   }
}
