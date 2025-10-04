package edu.stanford.cs.sjs;

import edu.stanford.cs.exp.Expression;
import edu.stanford.cs.parser.CodeVector;
import edu.stanford.cs.parser.Operator;
import edu.stanford.cs.parser.Parser;

public class SJSBlockOperator extends Operator {
   public SJSBlockOperator() {
      this.setName("BLOCK");
   }

   public void compile(Parser p, Expression[] args, CodeVector cv) {
      for(int i = 0; i < args.length; ++i) {
         p.compile(args[i], cv);
      }

   }
}
