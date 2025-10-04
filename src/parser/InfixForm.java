package edu.stanford.cs.parser;

import edu.stanford.cs.exp.Expression;

public abstract class InfixForm extends Operator {
   public Expression infixAction(Parser p, Expression lhs) {
      return p.createCompound2(this, lhs, p.readE(this.getInfixPrecedence()));
   }
}
