package edu.stanford.cs.sjs;

import edu.stanford.cs.exp.Expression;
import edu.stanford.cs.parser.CodeVector;
import edu.stanford.cs.parser.NofixOperator;
import edu.stanford.cs.parser.Parser;

public class SJSTrueOperator extends NofixOperator {
   public void compile(Parser p, Expression[] args, CodeVector cv) {
      cv.addInstruction(97, cv.stringRef("Core.TRUE"));
   }
}
