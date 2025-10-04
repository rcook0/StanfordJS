package edu.stanford.cs.parser;

import edu.stanford.cs.exp.EvalContext;
import edu.stanford.cs.exp.Expression;
import edu.stanford.cs.exp.Value;

public abstract class Operator extends Expression {
   public static final int LEFT = 0;
   public static final int RIGHT = 1;
   private int prefixPrec;
   private int infixPrec;
   private int associativity;
   private String opName;

   protected Operator() {
   }

   public boolean isStatement() {
      return false;
   }

   public void setPrefixPrecedence(int prec) {
      this.prefixPrec = prec;
   }

   public int getPrefixPrecedence() {
      return this.prefixPrec;
   }

   public void setInfixPrecedence(int prec) {
      this.infixPrec = prec;
   }

   public int getInfixPrecedence() {
      return this.infixPrec;
   }

   public void setAssociativity(int assoc) {
      this.associativity = assoc;
   }

   public int getAssociativity() {
      return this.associativity;
   }

   public int getType() {
      return 4;
   }

   public boolean isAssignmentOperator() {
      return false;
   }

   public void setName(String name) {
      this.opName = name;
   }

   public String getName() {
      return this.opName;
   }

   public Expression prefixAction(Parser p) {
      throw new SyntaxError("Illegal use of operator " + p.markCode("" + this));
   }

   public Expression infixAction(Parser p, Expression lhs) {
      throw new SyntaxError("Illegal use of operator " + p.markCode("" + this));
   }

   public String toString() {
      return this.opName;
   }

   public String unparse(Parser p, Expression[] args) {
      boolean parenFlag = Character.isJavaIdentifierStart(this.opName.charAt(0));
      String result = "";
      switch(args.length) {
      case 0:
         result = this.opName;
         break;
      case 1:
         result = this.opName;
         if (parenFlag) {
            result = result + "(";
         }

         result = result + p.unparse(args[0]);
         if (parenFlag) {
            result = result + ")";
         }
         break;
      case 2:
         if (parenFlag) {
            result = this.opName + "(";
            result = result + p.unparse(args[0]);
            result = result + ", ";
            result = result + p.unparse(args[1]);
            result = result + ")";
         } else {
            result = p.unparse(args[0]);
            result = result + " " + this.opName + " ";
            result = result + p.unparse(args[1]);
         }
         break;
      default:
         throw new RuntimeException("unparse: Nonstandard operator " + this.opName);
      }

      return result;
   }

   public Value apply(EvalContext ec, Expression[] args) {
      throw new RuntimeException("Undefined operator " + this.opName);
   }

   public boolean matches(String name) {
      return this.opName.equals(name);
   }

   public void compile(Parser p, Expression[] args, CodeVector cv) {
      throw new SyntaxError("No compile method defined for " + p.markCode("" + this));
   }

   public Value eval(EvalContext ec) {
      throw new RuntimeException("Illegal evaluation of an operator");
   }

   public boolean isOperator() {
      return true;
   }
}
