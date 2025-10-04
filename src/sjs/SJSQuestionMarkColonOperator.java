package edu.stanford.cs.sjs;

import edu.stanford.cs.exp.Expression;
import edu.stanford.cs.parser.CodeVector;
import edu.stanford.cs.parser.Operator;
import edu.stanford.cs.parser.Parser;

public class SJSQuestionMarkColonOperator extends Operator {
   public Expression infixAction(Parser p, Expression lhs) {
      Expression e1 = p.readE(0);
      p.verifyToken(":");
      Expression e2 = p.readE(this.getInfixPrecedence());
      return p.createCompound3(this, lhs, e1, e2);
   }

   public void compile(Parser p, Expression[] args, CodeVector cv) {
      String tag1 = cv.newLabel();
      String tag2 = cv.newLabel();
      p.compile(args[0], cv);
      cv.addInstruction(66, cv.labelRef(tag1));
      p.compile(args[1], cv);
      cv.addInstruction(64, cv.labelRef(tag2));
      cv.defineLabel(tag1);
      p.compile(args[2], cv);
      cv.defineLabel(tag2);
   }
}
