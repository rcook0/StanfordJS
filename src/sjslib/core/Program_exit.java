package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMMethod;

class Program_exit extends SVMMethod {
   public void execute(SVM svm, Value closure) {
      int status = 0;
      if (svm.getArgumentCount() == 1) {
         svm.checkSignature("Program.exit", "I");
         status = svm.popInteger();
      }

      System.exit(status);
   }
}
