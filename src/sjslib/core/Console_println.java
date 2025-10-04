package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.jsconsole.NBConsole;
import edu.stanford.cs.svm.SVM;

class Console_println extends ConsoleMethod {
   public void execute(SVM svm, Value receiver) {
      NBConsole console = this.getConsole(svm, receiver);
      if (svm.getArgumentCount() == 0) {
         console.println();
      } else {
         svm.checkSignature("Console.println", "*");
         console.println(svm.stringify(svm.pop()));
      }

      svm.push(Value.UNDEFINED);
   }
}
