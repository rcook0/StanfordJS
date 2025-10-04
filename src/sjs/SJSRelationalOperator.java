package edu.stanford.cs.sjs;

import edu.stanford.cs.exp.Expression;
import edu.stanford.cs.parser.CodeVector;
import edu.stanford.cs.parser.InfixOperator;
import edu.stanford.cs.parser.Parser;

public abstract class SJSRelationalOperator extends InfixOperator {
   public void compile(Parser parser, Expression[] args, CodeVector cv) {
      parser.compile(args[0], cv);
      parser.compile(args[1], cv);
      cv.addInstruction(this.getInstructionCode(), 0);
   }

   public boolean applyInteger(int lhs, int rhs) {
      throw new RuntimeException("Illegal types for " + this);
   }

   public boolean applyDouble(double lhs, double rhs) {
      throw new RuntimeException("Illegal types for " + this);
   }

   public int getInstructionCode() {
      throw new RuntimeException("Undefined instruction");
   }
}
