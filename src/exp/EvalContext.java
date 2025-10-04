package edu.stanford.cs.exp;

public interface EvalContext {
   Value getValue(String var1);

   void setValue(String var1, Value var2);

   boolean isDefined(String var1);

   LValue getLValue(Expression var1);

   Value evalConstant(Expression var1);

   Value evalIdentifier(Expression var1);

   Value evalCompound(Expression var1);
}
