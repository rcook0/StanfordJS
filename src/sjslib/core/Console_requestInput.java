package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.jsconsole.NBConsole;
import edu.stanford.cs.svm.SVM;

class Console_requestInput extends ConsoleMethod {
   public void execute(SVM svm, Value receiver) {
      String prompt = "";
      Value callback = null;
      if (svm.getArgumentCount() == 2) {
         svm.checkSignature("Console.requestInput", "S*");
         callback = svm.pop();
         prompt = svm.popString();
      } else {
         svm.checkSignature("Console.requestInput", "*");
         callback = svm.pop();
      }

      NBConsole console = this.getConsole(svm, receiver);
      svm.setGlobal("CONSOLE_WAIT", callback);
      svm.setState(6);
      console.requestInput(prompt);
   }
}
