package edu.stanford.cs.sjs;

import edu.stanford.cs.exp.Expression;
import edu.stanford.cs.parser.CodeVector;
import edu.stanford.cs.parser.Operator;
import edu.stanford.cs.parser.Parser;

public abstract class SJSPrefixIncDecOperator extends Operator {
   public abstract int getInstructionCode();

   public abstract int getSign();

   public Expression prefixAction(Parser p) {
      return p.createCompound1(this, p.readE(this.getPrefixPrecedence()));
   }

   public Expression infixAction(Parser p, Expression lhs) {
      return p.createCompound1(p.getOperator("x" + this.getName()), lhs);
   }

   public String unparse(Parser p, Expression[] args) {
      return this.toString() + p.unparse(args[0]);
   }

   public void compile(Parser p, Expression[] args, CodeVector cv) {
      p.compile(args[0], cv);
      cv.addInstruction(16, 1);
      cv.addInstruction(this.getInstructionCode(), 0);
      cv.addInstruction(21, 0);
      SJSAssignmentOperator.compileSetLV(p, args[0], cv);
   }
}
