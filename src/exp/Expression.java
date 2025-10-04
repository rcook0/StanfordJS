package edu.stanford.cs.exp;

public abstract class Expression {
   public static final int CONSTANT = 1;
   public static final int IDENTIFIER = 2;
   public static final int COMPOUND = 3;
   public static final int OPERATOR = 4;
   public static final int FUNCTION = 5;
   private Object data;

   public abstract Value eval(EvalContext var1);

   public abstract int getType();

   public Value getValue() {
      throw new RuntimeException("getValue must be called on a Constant");
   }

   public String getName() {
      throw new RuntimeException("getName must be called on an Identifier");
   }

   public Expression getFunction() {
      throw new RuntimeException("getFunction must be called on a Compound");
   }

   public Expression[] getArgs() {
      throw new RuntimeException("getArgs must be called on a Compound");
   }

   public static Value[] evalArgs(EvalContext ec, Expression[] args) {
      Value[] actuals = new Value[args.length];

      for(int i = 0; i < args.length; ++i) {
         actuals[i] = args[i].eval(ec);
      }

      return actuals;
   }

   public LValue getLValue(EvalContext ec) {
      return ec.getLValue(this);
   }

   public boolean matches(String str) {
      return false;
   }

   public Object getClientData() {
      return this.data;
   }

   public void setClientData(Object data) {
      this.data = data;
   }

   public boolean isOperator() {
      return false;
   }
}
