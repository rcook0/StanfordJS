package edu.stanford.cs.parser;

import edu.stanford.cs.exp.Expression;

public abstract class PrefixForm extends Operator {
   public Expression prefixAction(Parser p) {
      return p.createCompound1(this, p.readE(this.getPrefixPrecedence()));
   }
}
