package edu.stanford.cs.sjs;

import edu.stanford.cs.exp.Expression;
import edu.stanford.cs.parser.CodeVector;
import edu.stanford.cs.parser.Parser;
import edu.stanford.cs.parser.Statement;
import edu.stanford.cs.parser.SyntaxError;

public class SJSDefaultStatement extends Statement {
   public Expression prefixAction(Parser p) {
      throw new SyntaxError("A " + p.markCode("default") + " statement must be inside a " + p.markCode("switch"));
   }

   public void compile(Parser p, Expression[] args, CodeVector cv) {
      SJSParser jsp = (SJSParser)p;
      String tag = jsp.getNextLabel();
      String skip = null;
      if (tag != null) {
         skip = cv.newLabel();
         cv.addInstruction(64, cv.labelRef(skip));
         cv.defineLabel(tag);
      }

      tag = cv.newLabel();
      jsp.setNextLabel(tag);
      if (skip != null) {
         cv.defineLabel(skip);
      }

   }
}
