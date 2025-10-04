package edu.stanford.cs.svm;

import edu.stanford.cs.exp.Value;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SVMConsoleListener implements ActionListener {
   private SVM svm;

   public SVMConsoleListener(SVM svm) {
      this.svm = svm;
   }

   public void actionPerformed(ActionEvent e) {
      boolean ok = false;
      String key = "";
      Value action = this.svm.getGlobal("CONSOLE_WAIT");
      if (action != Value.UNDEFINED) {
         this.svm.setGlobal("CONSOLE_WAIT", Value.UNDEFINED);
         if (action.getType() == 83) {
            key = action.getStringValue();
            if (key.startsWith("getInt")) {
               try {
                  this.svm.pushInteger(Integer.parseInt(e.getActionCommand()));
                  ok = true;
               } catch (RuntimeException var8) {
                  this.svm.getConsole().showErrorMessage(var8.getMessage());
               }
            } else if (key.startsWith("getNumber")) {
               try {
                  this.svm.pushDouble(Double.parseDouble(e.getActionCommand()));
                  ok = true;
               } catch (RuntimeException var7) {
                  this.svm.getConsole().showErrorMessage(var7.getMessage());
               }
            } else {
               this.svm.pushString(e.getActionCommand());
               ok = true;
            }
         } else if (action.getClassName().equals("FunctionClosure")) {
            this.svm.pushString(e.getActionCommand());
            this.svm.push(action);
            this.svm.call(1);
            this.svm.run();
            return;
         }

         if (ok) {
            this.svm.run();
         } else {
            String prompt = "";
            int colon = key.indexOf(":");
            if (colon > 0) {
               prompt = key.substring(colon + 1);
            }

            this.svm.getConsole().requestInput(prompt);
         }
      } else {
         this.processConsoleLine(e.getActionCommand());
      }

   }

   protected void processConsoleLine(String line) {
   }
}
