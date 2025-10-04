package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.jsconsole.NBConsole;
import edu.stanford.cs.svm.SVM;

class Console_startConsoleLog extends ConsoleMethod {
   public void execute(SVM svm, Value receiver) {
      NBConsole console = this.getConsole(svm, receiver);
      svm.checkSignature("Console.startConsoleLog", "");
      console.startConsoleLog();
      svm.push(Value.UNDEFINED);
   }
}
