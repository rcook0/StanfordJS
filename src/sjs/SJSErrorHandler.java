package edu.stanford.cs.sjs;

import edu.stanford.cs.java2js.JSErrorHandler;
import edu.stanford.cs.svm.SVM;

public class SJSErrorHandler implements JSErrorHandler {
   private SJS app;

   public SJSErrorHandler(SJS app) {
      this.app = app;
   }

   public void error(String msg) {
      SVM svm = this.app.getSVM();
      int index = svm.getStatementOffset();
      if (index > 0) {
         if (msg.endsWith("expecting ;")) {
            msg = "Missing ;";
            index -= 2;
         } else if (msg.startsWith("Illegal to apply ")) {
            msg = this.changeInstructionToOperator(msg);
         }
      }

      this.app.showErrorAtOffset(msg, index);
   }

   private String changeInstructionToOperator(String msg) {
      return msg.replace("apply ADD", "apply +").replace("apply SUB", "apply -").replace("apply MUL", "apply *").replace("apply DIV", "apply /").replace("apply IDIV", "apply /").replace("apply REM", "apply %").replace("apply NEG", "apply -");
   }
}
