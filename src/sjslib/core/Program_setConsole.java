package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.jsconsole.NBConsole;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMMethod;

class Program_setConsole extends SVMMethod {
   public void execute(SVM svm, Value closure) {
      svm.checkSignature("Program.setConsole", "O");
      svm.setConsole((NBConsole)svm.pop().getValue());
      svm.push(Value.UNDEFINED);
   }
}
