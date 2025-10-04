package edu.stanford.cs.exp;

public class Compound extends Expression {
   private Expression function;
   private Expression[] arguments;

   public Compound(Expression fn, Expression[] args) {
      this.function = fn;
      this.arguments = args;
   }

   public Expression getFunction() {
      return this.function;
   }

   public Expression[] getArgs() {
      return this.arguments;
   }

   public Value eval(EvalContext ec) {
      return ec.evalCompound(this);
   }

   public String toString() {
      String result = this.function.toString();
      result = result + "(";

      for(int i = 0; i < this.arguments.length; ++i) {
         if (i > 0) {
            result = result + ",";
         }

         result = result + this.arguments[i].toString();
      }

      return result + ")";
   }

   public int getType() {
      return 3;
   }
}
