package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMMethod;
import java.awt.Component;

class Program_add extends SVMMethod {
   public void execute(SVM svm, Value closure) {
      svm.checkSignature("Program.add", "OS");
      String name = svm.popString();
      Component c = (Component)svm.pop().getValue();
      svm.getProgram().add(c, name);
      svm.push(Value.UNDEFINED);
   }
}
