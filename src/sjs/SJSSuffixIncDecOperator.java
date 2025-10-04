package edu.stanford.cs.sjs;

import edu.stanford.cs.exp.Expression;
import edu.stanford.cs.parser.CodeVector;
import edu.stanford.cs.parser.Operator;
import edu.stanford.cs.parser.Parser;

public abstract class SJSSuffixIncDecOperator extends Operator {
   public abstract int getInstructionCode();

   public abstract int getSign();

   public String unparse(Parser p, Expression[] args) {
      return p.unparse(args[0]) + this.toString();
   }

   public void compile(Parser p, Expression[] args, CodeVector cv) {
      p.compile(args[0], cv);
      cv.addInstruction(21, 0);
      cv.addInstruction(16, 1);
      cv.addInstruction(this.getInstructionCode(), 0);
      SJSAssignmentOperator.compileSetLV(p, args[0], cv);
   }
}
