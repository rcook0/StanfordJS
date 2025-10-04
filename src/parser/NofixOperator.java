package edu.stanford.cs.parser;

import edu.stanford.cs.exp.Expression;

public abstract class NofixOperator extends Operator {
   public Expression prefixAction(Parser p) {
      return p.createCompound0(this);
   }
}
