package edu.stanford.cs.parser;

import edu.stanford.cs.exp.EvalContext;
import edu.stanford.cs.exp.Expression;
import edu.stanford.cs.exp.Value;

public abstract class Statement extends Operator {
   public Value apply(EvalContext ec, Expression[] args) {
      throw new RuntimeException("Statements are not expressions");
   }

   public boolean isStatement() {
      return true;
   }
}
