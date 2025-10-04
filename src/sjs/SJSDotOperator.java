package edu.stanford.cs.sjs;

import edu.stanford.cs.exp.Expression;
import edu.stanford.cs.parser.CodeVector;
import edu.stanford.cs.parser.Operator;
import edu.stanford.cs.parser.Parser;
import edu.stanford.cs.parser.SyntaxError;

public class SJSDotOperator extends Operator {
   public Expression infixAction(Parser p, Expression lhs) {
      String token = p.nextToken();
      if (p.getTokenType(token) != 1) {
         throw new SyntaxError("Illegal field name");
      } else {
         return p.createCompound2(this, lhs, p.createIdentifier(token));
      }
   }

   public void compile(Parser p, Expression[] args, CodeVector cv) {
      Expression lhs = args[0];
      Expression rhs = args[1];
      p.compile(lhs, cv);
      cv.addInstruction(18, cv.stringRef(rhs.getName()));
      cv.addInstruction(97, cv.stringRef("Core.select"));
   }

   public String unparse(Parser p, Expression[] args) {
      return p.unparse(args[0]) + "." + p.unparse(args[1]);
   }
}
