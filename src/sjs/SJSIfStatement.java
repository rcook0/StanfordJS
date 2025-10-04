package edu.stanford.cs.sjs;

import edu.stanford.cs.exp.Expression;
import edu.stanford.cs.parser.CodeVector;
import edu.stanford.cs.parser.Parser;
import edu.stanford.cs.parser.Statement;

public class SJSIfStatement extends Statement {
   public Expression prefixAction(Parser p) {
      SJSParser jsp = (SJSParser)p;
      jsp.verifyToken("(");
      Expression exp = jsp.readE(0);
      jsp.verifyToken(")");
      Expression s1 = jsp.readStatement();
      String token = jsp.nextToken();
      if (token.equals("else")) {
         return jsp.createCompound3(this, exp, s1, jsp.readStatement());
      } else {
         jsp.saveToken(token);
         return jsp.createCompound2(this, exp, s1);
      }
   }

   public void compile(Parser p, Expression[] args, CodeVector cv) {
      String tag = cv.newLabel();
      p.compile(args[0], cv);
      cv.addInstruction(66, cv.labelRef(tag));
      p.compile(args[1], cv);
      if (args.length == 3) {
         String tag2 = cv.newLabel();
         cv.addInstruction(64, cv.labelRef(tag2));
         cv.defineLabel(tag);
         p.compile(args[2], cv);
         cv.defineLabel(tag2);
      } else {
         cv.defineLabel(tag);
      }

   }
}
