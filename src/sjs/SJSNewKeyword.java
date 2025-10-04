package edu.stanford.cs.sjs;

import edu.stanford.cs.exp.Expression;
import edu.stanford.cs.parser.CodeVector;
import edu.stanford.cs.parser.Parser;
import edu.stanford.cs.parser.PrefixOperator;

public class SJSNewKeyword extends PrefixOperator {
   public Expression prefixAction(Parser p) {
      Expression exp = p.readE(this.getPrefixPrecedence());
      return p.createCompound1(this, exp);
   }

   public void compile(Parser p, Expression[] args, CodeVector cv) {
      Expression exp = args[0];
      Expression fn = exp.getFunction();
      Expression[] actuals = exp.getArgs();
      int n = actuals.length;

      for(int i = 0; i < n; ++i) {
         p.compile(actuals[i], cv);
      }

      cv.addInstruction(97, cv.stringRef(fn.toString() + ".new"));
      cv.addInstruction(106, n);
   }
}
