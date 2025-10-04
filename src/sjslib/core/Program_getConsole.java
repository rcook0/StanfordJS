package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.jsconsole.NBConsole;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMMethod;

class Program_getConsole extends SVMMethod {
   public void execute(SVM svm, Value closure) {
      svm.checkSignature("Program.getConsole", "");
      NBConsole console = svm.getConsole();
      if (console == null) {
         svm.push(Value.UNDEFINED);
      } else {
         svm.push(Value.createObject(console, "Console"));
      }

   }
}
