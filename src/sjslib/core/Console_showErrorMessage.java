package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.jsconsole.NBConsole;
import edu.stanford.cs.svm.SVM;

class Console_showErrorMessage extends ConsoleMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("Console.showErrorMessage", "S");
      NBConsole console = this.getConsole(svm, receiver);
      console.showErrorMessage("Error: " + svm.pop());
      svm.push(Value.UNDEFINED);
   }
}
