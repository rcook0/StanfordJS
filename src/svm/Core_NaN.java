package edu.stanford.cs.svm;

import edu.stanford.cs.exp.Value;

class Core_NaN extends SVMConstant {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("Core.NaN", "");
      svm.push(Value.createDouble(Double.NaN));
   }
}
