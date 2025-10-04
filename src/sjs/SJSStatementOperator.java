package edu.stanford.cs.sjs;

import edu.stanford.cs.exp.Expression;
import edu.stanford.cs.parser.CodeVector;
import edu.stanford.cs.parser.Operator;
import edu.stanford.cs.parser.Parser;

public class SJSStatementOperator extends Operator {
   public SJSStatementOperator() {
      this.setName("STMT");
   }

   public void compile(Parser p, Expression[] args, CodeVector cv) {
      cv.addInstruction(3, args[0].getValue().getIntegerValue());
      p.compile(args[1], cv);
   }
}
