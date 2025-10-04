package edu.stanford.cs.sjs;

import edu.stanford.cs.exp.Expression;
import edu.stanford.cs.parser.CodeVector;
import edu.stanford.cs.parser.Parser;
import edu.stanford.cs.parser.Statement;

public class SJSThrowStatement extends Statement {
   public Expression prefixAction(Parser p) {
      Expression exp = p.readE(0);
      p.verifyToken(";");
      return p.createCompound1(this, exp);
   }

   public void compile(Parser p, Expression[] args, CodeVector cv) {
      p.compile(args[0], cv);
      cv.addInstruction(70, 0);
   }
}
