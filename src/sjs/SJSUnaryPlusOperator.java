package edu.stanford.cs.sjs;

import edu.stanford.cs.exp.Expression;
import edu.stanford.cs.parser.CodeVector;
import edu.stanford.cs.parser.Operator;
import edu.stanford.cs.parser.Parser;

public class SJSUnaryPlusOperator extends Operator {
   public void compile(Parser p, Expression[] args, CodeVector cv) {
      p.compile(args[0], cv);
   }
}
