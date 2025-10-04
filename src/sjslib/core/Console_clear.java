package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.jsconsole.NBConsole;
import edu.stanford.cs.svm.SVM;

class Console_clear extends ConsoleMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("Console.clear", "");
      NBConsole console = this.getConsole(svm, receiver);
      if (console != null) {
         console.clear();
      }

      svm.push(Value.UNDEFINED);
   }
}
