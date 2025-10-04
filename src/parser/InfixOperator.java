package edu.stanford.cs.parser;

import edu.stanford.cs.exp.EvalContext;
import edu.stanford.cs.exp.Expression;
import edu.stanford.cs.exp.Value;

public abstract class InfixOperator extends InfixForm {
   public Value apply(EvalContext ec, Expression[] args) {
      return this.apply2(ec, args[0].eval(ec), args[1].eval(ec));
   }

   public Value apply2(EvalContext ec, Value lhs, Value rhs) {
      throw new RuntimeException("Undefined operator '" + this + "'");
   }
}
