package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.java2js.JSProgram;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMMethod;

class Program_alert extends SVMMethod {
   public void execute(SVM svm, Value closure) {
      String msg = "";
      if (svm.getArgumentCount() == 1) {
         msg = msg + svm.pop().toString();
      }

      JSProgram.alert(msg);
      svm.push(Value.UNDEFINED);
   }
}
