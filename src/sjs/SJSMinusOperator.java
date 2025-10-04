package edu.stanford.cs.sjs;

import edu.stanford.cs.exp.Expression;
import edu.stanford.cs.parser.Parser;

public class SJSMinusOperator extends SJSArithmeticOperator {
   public Expression prefixAction(Parser p) {
      return p.createCompound1(p.getOperator("-x"), p.readE(this.getPrefixPrecedence()));
   }

   public int getInstructionCode() {
      return 33;
   }
}
