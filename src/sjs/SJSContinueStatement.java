package edu.stanford.cs.sjs;

import edu.stanford.cs.exp.Expression;
import edu.stanford.cs.parser.CodeVector;
import edu.stanford.cs.parser.Parser;
import edu.stanford.cs.parser.Statement;
import edu.stanford.cs.parser.SyntaxError;

public class SJSContinueStatement extends Statement {
   public Expression prefixAction(Parser p) {
      p.verifyToken(";");
      return p.createCompound0(this);
   }

   public void compile(Parser p, Expression[] args, CodeVector cv) {
      SJSParser jsp = (SJSParser)p;
      String tag = jsp.getContinueLabel();
      if (tag == null) {
         throw new SyntaxError("Illegal use of " + p.markCode("continue") + " statement");
      } else {
         cv.addInstruction(64, cv.labelRef(tag));
      }
   }
}
