package edu.stanford.cs.sjs;

import edu.stanford.cs.exp.Expression;
import edu.stanford.cs.parser.CodeVector;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SJSReadEvalPrintLoop implements ChangeListener {
   private SJSCommandMonitor monitor;
   private SJSParser parser;
   private SJSVM svm;

   public SJSReadEvalPrintLoop(SJSVM svm, SJSParser parser, SJSCommandMonitor monitor) {
      this.svm = svm;
      this.parser = parser;
      this.monitor = monitor;
      svm.addChangeListener(this);
   }

   public void processConsoleLine(String line) {
      String token;
      try {
         this.parser.setInput(line);
         boolean debug = false;
         token = this.parser.nextToken();
         if (token.equals("debug")) {
            debug = true;
         } else {
            this.parser.saveToken(token);
         }

         Expression exp = this.parser.readE(0);
         token = this.parser.nextToken();
         boolean showResult = true;
         if (token.equals(";")) {
            showResult = false;
         } else {
            this.parser.saveToken(token);
         }

         this.parser.verifyToken("");
         CodeVector cv = new CodeVector();
         if (exp.getType() == 3) {
            Expression fn = exp.getFunction();
            if (fn.getType() == 4) {
               String op = fn.getName();
               if (op.equals("var") || op.equals("const")) {
                  exp = exp.getArgs()[0];
                  if (exp.getType() == 3) {
                     String name = exp.getArgs()[0].getName();
                     cv.addInstruction(18, cv.stringRef(name));
                     this.parser.compile(exp.getArgs()[1], cv);
                     cv.addInstruction(97, cv.stringRef("Global.set"));
                  }

                  exp = null;
                  showResult = false;
               }
            }
         }

         if (exp != null) {
            this.parser.compile(exp, cv);
         }

         if (showResult) {
            cv.addInstruction(97, cv.stringRef("Console.$display"));
            cv.addInstruction(106, 1);
         }

         cv.addInstruction(4, 0);
         this.svm.reset();
         this.svm.clearAllTimers();
         this.svm.closeAllGWindows();
         this.svm.setCode(cv.getCode());
         this.svm.setPC(0);
         this.svm.pushExceptionFrame(-999);
         this.svm.start(debug ? 2 : 1);
      } catch (Exception var10) {
         token = var10.getMessage();
         if (token == null || token.equals("")) {
            token = "" + var10;
         }

         this.monitor.showError(token, 0);
      }

   }

   public void stateChanged(ChangeEvent e) {
      switch(this.svm.getState()) {
      case 5:
         this.monitor.signalFinished();
      case 6:
      default:
         break;
      case 7:
         this.monitor.showError(this.svm.getErrorMessage(), 0);
      }

      this.monitor.update();
   }
}
