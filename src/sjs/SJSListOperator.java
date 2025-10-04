package edu.stanford.cs.sjs;

import edu.stanford.cs.exp.Expression;
import edu.stanford.cs.parser.CodeVector;
import edu.stanford.cs.parser.Operator;
import edu.stanford.cs.parser.Parser;

public class SJSListOperator extends Operator {
   public void compile(Parser p, Expression[] args, CodeVector cv) {
      for(int i = 0; i < args.length; ++i) {
         p.compile(args[i], cv);
      }

      cv.addInstruction(97, cv.stringRef("Array.create"));
      cv.addInstruction(106, args.length);
   }

   public String unparse(Parser p, Expression[] args) {
      return "(" + p.unparse(args[0]) + ")";
   }
}
