package edu.stanford.cs.sjs;

import edu.stanford.cs.exp.Expression;
import edu.stanford.cs.parser.CodeVector;
import edu.stanford.cs.parser.InfixOperator;
import edu.stanford.cs.parser.Parser;

public class SJSBitwiseOROperator extends InfixOperator {
   public void compile(Parser p, Expression[] args, CodeVector cv) {
      p.compile(args[0], cv);
      p.compile(args[1], cv);
      cv.addInstruction(82, 0);
   }
}
