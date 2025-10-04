package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.jsconsole.JSConsole;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMMethod;

class Console_new extends SVMMethod {
   public void execute(SVM svm, Value receiver) {
      svm.push(Value.createObject(new JSConsole(), "Console"));
   }
}
