package edu.stanford.cs.parser;

import edu.stanford.cs.exp.EvalContext;
import edu.stanford.cs.exp.Expression;
import edu.stanford.cs.exp.Value;

public abstract class PrefixOperator extends PrefixForm {
   public Value apply(EvalContext ec, Expression[] args) {
      return this.apply1(ec, args[0].eval(ec));
   }

   public Value apply1(EvalContext ec, Value rhs) {
      throw new RuntimeException("Undefined operator '" + this + "'");
   }
}
