package edu.stanford.cs.sjs;

import edu.stanford.cs.exp.Expression;
import edu.stanford.cs.parser.CodeVector;
import edu.stanford.cs.parser.Parser;

public abstract class SJSOpEqualOperator extends SJSArithmeticOperator {
   public boolean isAssignmentOperator() {
      return true;
   }

   public void compile(Parser p, Expression[] args, CodeVector cv) {
      p.compile(args[0], cv);
      p.compile(args[1], cv);
      cv.addInstruction(this.getInstructionCode(), 0);
      cv.addInstruction(21, 0);
      SJSAssignmentOperator.compileSetLV(p, args[0], cv);
   }
}
