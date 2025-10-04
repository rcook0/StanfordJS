package edu.stanford.cs.sjs;

import edu.stanford.cs.exp.Expression;
import edu.stanford.cs.parser.CodeVector;
import edu.stanford.cs.parser.Operator;
import edu.stanford.cs.parser.Parser;
import edu.stanford.cs.parser.SyntaxError;
import java.util.ArrayList;

public class SJSBracketOperator extends Operator {
   public Expression prefixAction(Parser p) {
      ArrayList<Expression> list = new ArrayList();
      String token = p.nextToken();
      if (!token.equals("]")) {
         p.saveToken(token);

         while(true) {
            Expression exp = p.readE(0);
            list.add(exp);
            token = p.nextToken();
            if (token.equals("]")) {
               break;
            }

            if (!token.equals(",")) {
               throw new SyntaxError("Found " + p.markCode(token) + " when expecting " + p.markCode(",") + " or " + p.markCode("]"));
            }
         }
      }

      Expression[] args = new Expression[list.size()];

      for(int i = 0; i < args.length; ++i) {
         args[i] = (Expression)list.get(i);
      }

      return p.createCompound(p.getOperator("[...]"), args);
   }

   public Expression infixAction(Parser p, Expression lhs) {
      Expression exp = p.readE(0);
      p.verifyToken("]");
      return p.createCompound2(this, lhs, exp);
   }

   public void compile(Parser p, Expression[] args, CodeVector cv) {
      p.compile(args[0], cv);
      p.compile(args[1], cv);
      cv.addInstruction(97, cv.stringRef("Core.select"));
   }

   public String unparse(Parser p, Expression[] args) {
      return p.unparse(args[0]) + "[" + p.unparse(args[1]) + "]";
   }
}
