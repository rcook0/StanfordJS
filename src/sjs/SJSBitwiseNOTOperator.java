package edu.stanford.cs.sjs;

import edu.stanford.cs.exp.Expression;
import edu.stanford.cs.parser.CodeVector;
import edu.stanford.cs.parser.Parser;
import edu.stanford.cs.parser.PrefixOperator;

public class SJSBitwiseNOTOperator extends PrefixOperator {
   public void compile(Parser p, Expression[] args, CodeVector cv) {
      p.compile(args[0], cv);
      cv.addInstruction(80, 0);
   }
}
