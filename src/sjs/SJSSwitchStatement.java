package edu.stanford.cs.sjs;

import edu.stanford.cs.exp.Expression;
import edu.stanford.cs.parser.CodeVector;
import edu.stanford.cs.parser.Parser;
import edu.stanford.cs.parser.Statement;
import edu.stanford.cs.parser.SyntaxError;
import java.util.ArrayList;

public class SJSSwitchStatement extends Statement {
   public Expression prefixAction(Parser p) {
      SJSParser jsp = (SJSParser)p;
      ArrayList<Expression> body = new ArrayList();
      jsp.verifyToken("(");
      body.add(jsp.readE(0));
      jsp.verifyToken(")");
      jsp.verifyToken("{");
      String token = jsp.nextToken();
      if (!token.equals("}")) {
         boolean started = false;

         do {
            if (token.equals("case")) {
               body.add(this.readCaseClause(jsp));
               started = true;
            } else if (token.equals("default")) {
               body.add(this.readDefaultClause(jsp));
               started = true;
            } else {
               if (!started) {
                  throw new SyntaxError("Missing " + p.markCode("case") + " clause");
               }

               jsp.saveToken(token);
               body.add(jsp.readStatement());
            }

            token = jsp.nextToken();
         } while(!token.equals("}"));
      }

      int n = body.size();
      Expression[] array = new Expression[n];

      for(int i = 0; i < n; ++i) {
         array[i] = (Expression)body.get(i);
      }

      return jsp.createCompound(this, array);
   }

   public void compile(Parser p, Expression[] args, CodeVector cv) {
      SJSParser jsp = (SJSParser)p;
      String exit = cv.newLabel();
      jsp.pushStatementContext(exit, (String)null);
      jsp.compile(args[0], cv);
      String temp = "v_" + jsp.getStatementDepth();
      cv.addInstruction(104, cv.stringRef(temp));
      cv.addInstruction(109, cv.stringRef(temp));

      for(int i = 1; i < args.length; ++i) {
         jsp.compile(args[i], cv);
      }

      String tag = jsp.getNextLabel();
      if (tag != null) {
         cv.defineLabel(tag);
      }

      cv.defineLabel(exit);
      jsp.popStatementContext();
   }

   private Expression readCaseClause(Parser p) {
      Expression exp = p.readE(0);
      p.verifyToken(":");
      return p.createCompound1(p.getOperator("case"), exp);
   }

   private Expression readDefaultClause(Parser p) {
      p.verifyToken(":");
      return p.createCompound0(p.getOperator("default"));
   }
}
