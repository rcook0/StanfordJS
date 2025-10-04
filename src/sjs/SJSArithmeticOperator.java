package edu.stanford.cs.sjs;

import edu.stanford.cs.exp.Expression;
import edu.stanford.cs.parser.CodeVector;
import edu.stanford.cs.parser.InfixOperator;
import edu.stanford.cs.parser.Parser;

public abstract class SJSArithmeticOperator extends InfixOperator {
   public void compile(Parser parser, Expression[] args, CodeVector cv) {
      parser.compile(args[0], cv);
      parser.compile(args[1], cv);
      cv.addInstruction(this.getInstructionCode(), 0);
   }

   public int getInstructionCode() {
      throw new RuntimeException("Undefined instruction");
   }
}
