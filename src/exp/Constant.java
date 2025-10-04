package edu.stanford.cs.exp;

public class Constant extends Expression {
   private Value value;

   public Constant(Value v) {
      this.value = v;
   }

   public Value getValue() {
      return this.value;
   }

   public Value eval(EvalContext ec) {
      return ec.evalConstant(this);
   }

   public String toString() {
      return this.value.toString();
   }

   public int getType() {
      return 1;
   }
}
