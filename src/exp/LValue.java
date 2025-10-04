package edu.stanford.cs.exp;

public abstract class LValue extends Value {
   public LValue() {
      super(65, (Object)null);
   }

   public abstract Value get(EvalContext var1);

   public abstract void set(EvalContext var1, Value var2);

   public boolean isLValue() {
      return true;
   }
}
