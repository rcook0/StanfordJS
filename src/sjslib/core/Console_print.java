package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.jsconsole.NBConsole;
import edu.stanford.cs.svm.SVM;

class Console_print extends ConsoleMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("Console.print", "*");
      NBConsole console = this.getConsole(svm, receiver);
      console.print(svm.stringify(svm.pop()));
      svm.push(Value.UNDEFINED);
   }
}
