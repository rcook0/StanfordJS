package edu.stanford.cs.sjs;

import edu.stanford.cs.exp.Constant;
import edu.stanford.cs.exp.Expression;
import edu.stanford.cs.exp.Value;
import edu.stanford.cs.parser.CodeVector;
import edu.stanford.cs.parser.Operator;
import edu.stanford.cs.parser.Parser;
import edu.stanford.cs.parser.SyntaxError;
import java.util.ArrayList;

public class SJSBraceOperator extends Operator {
   public Expression prefixAction(Parser p) {
      ArrayList<Expression> list = new ArrayList();
      String name = p.nextToken();
      if (!name.equals("}")) {
         while(true) {
            switch(p.getTokenType(name)) {
            case 1:
               break;
            case 2:
            default:
               throw new SyntaxError("Illegal field name");
            case 3:
               name = p.getTokenScanner().getStringValue(name);
            }

            p.verifyToken(":");
            Expression exp = p.readE(0);
            list.add(new Constant(Value.createString(name)));
            list.add(exp);
            String token = p.nextToken();
            if (token.equals("}")) {
               break;
            }

            if (!token.equals(",")) {
               throw new SyntaxError("Found " + p.markCode(token) + " when expecting " + p.markCode(",") + " or " + p.markCode("}"));
            }

            name = p.nextToken();
         }
      }

      Expression[] args = new Expression[list.size()];

      for(int i = 0; i < args.length; ++i) {
         args[i] = (Expression)list.get(i);
      }

      return p.createCompound(this, args);
   }

   public void compile(Parser p, Expression[] args, CodeVector cv) {
      for(int i = 0; i < args.length; ++i) {
         p.compile(args[i], cv);
      }

      cv.addInstruction(97, cv.stringRef("Object.create"));
      cv.addInstruction(106, args.length);
   }

   public String unparse(Parser p, Expression[] args) {
      return "{" + p.unparse(args[0]) + "}";
   }
}
