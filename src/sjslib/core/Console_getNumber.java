package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.jsconsole.NBConsole;
import edu.stanford.cs.svm.SVM;

class Console_getNumber extends ConsoleMethod {
   public void execute(SVM svm, Value receiver) {
      String prompt = null;
      if (svm.getArgumentCount() == 1) {
         svm.checkSignature("Console.getNumber", "S");
         prompt = svm.popString();
      } else {
         svm.checkSignature("Console.getNumber", "");
      }

      if (prompt != null) {
         svm.print(prompt);
      }

      NBConsole console = this.getConsole(svm, receiver);
      String action = "getNumber";
      if (prompt != null) {
         action = action + ":" + prompt;
      }

      svm.setGlobal("CONSOLE_WAIT", Value.createString(action));
      svm.setState(6);
      console.requestInput("");
   }
}
