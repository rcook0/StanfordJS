package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.jsconsole.NBConsole;
import edu.stanford.cs.svm.SVM;

class Console_display extends ConsoleMethod {
   public void execute(SVM svm, Value receiver) {
      NBConsole console = this.getConsole(svm, receiver);
      svm.checkSignature("Console.println", "*");
      Value v = svm.getValueStackDepth() == 0 ? null : svm.pop();
      if (v != null && v != Value.UNDEFINED) {
         console.println(v);
      }

      svm.push(Value.UNDEFINED);
   }
}
