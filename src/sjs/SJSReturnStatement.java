package edu.stanford.cs.sjs;

import edu.stanford.cs.exp.Expression;
import edu.stanford.cs.parser.CodeVector;
import edu.stanford.cs.parser.Parser;
import edu.stanford.cs.parser.Statement;

public class SJSReturnStatement extends Statement {
   public Expression prefixAction(Parser p) {
      String token = p.nextToken();
      if (token.equals(";")) {
         return p.createCompound0(this);
      } else {
         p.saveToken(token);
         Expression exp = p.readE(0);
         p.verifyToken(";");
         return p.createCompound1(this, exp);
      }
   }

   public void compile(Parser p, Expression[] args, CodeVector cv) {
      if (args.length == 0) {
         cv.addInstruction(97, cv.stringRef("Core.UNDEFINED"));
      } else {
         p.compile(args[0], cv);
      }

      cv.addInstruction(99, 0);
   }
}
