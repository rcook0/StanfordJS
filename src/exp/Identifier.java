package edu.stanford.cs.exp;

public class Identifier extends Expression {
   private String name;

   public Identifier(String name) {
      this.name = name;
   }

   public Value eval(EvalContext ec) {
      return ec.evalIdentifier(this);
   }

   public int getType() {
      return 2;
   }

   public String getName() {
      return this.name;
   }

   public String toString() {
      return this.name;
   }

   public boolean matches(String name) {
      return this.name.equals(name);
   }
}
