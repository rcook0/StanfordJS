package edu.stanford.cs.sjs;

import edu.stanford.cs.exp.Expression;
import edu.stanford.cs.parser.CodeVector;
import edu.stanford.cs.parser.Parser;
import edu.stanford.cs.parser.Statement;

public class SJSWhileStatement extends Statement {
   public Expression prefixAction(Parser p) {
      SJSParser jsp = (SJSParser)p;
      jsp.verifyToken("(");
      Expression exp = jsp.readE(0);
      jsp.verifyToken(")");
      Expression s = jsp.readStatement();
      return jsp.createCompound2(this, exp, s);
   }

   public void compile(Parser p, Expression[] args, CodeVector cv) {
      SJSParser jsp = (SJSParser)p;
      String tag1 = cv.newLabel();
      String tag2 = cv.newLabel();
      jsp.pushStatementContext(tag2, tag1);
      cv.defineLabel(tag1);
      jsp.compile(args[0], cv);
      cv.addInstruction(66, cv.labelRef(tag2));
      jsp.compile(args[1], cv);
      cv.addInstruction(64, cv.labelRef(tag1));
      cv.defineLabel(tag2);
      jsp.popStatementContext();
   }
}
