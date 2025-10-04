package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.jsconsole.NBConsole;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMMethod;

abstract class ConsoleMethod extends SVMMethod {
   public NBConsole getConsole(SVM svm, Value receiver) {
      return receiver == null ? svm.getConsole() : (NBConsole)receiver.getValue();
   }
}
