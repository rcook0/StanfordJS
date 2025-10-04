package edu.stanford.cs.parser;

import edu.stanford.cs.exp.Compound;
import edu.stanford.cs.exp.Constant;
import edu.stanford.cs.exp.Expression;
import edu.stanford.cs.exp.Identifier;
import edu.stanford.cs.exp.Value;
import edu.stanford.cs.tokenscanner.TokenScanner;
import java.util.HashMap;

public class Parser {
   private TokenScanner scanner = this.createTokenScanner();
   private HashMap<String, Operator> operators = new HashMap();
   private boolean markCodeFlag;

   public Expression parse() {
      Expression exp = this.readE(0);
      String token = this.nextToken();
      if (!token.equals("")) {
         throw new SyntaxError("Unexpected token: " + this.markCode(token));
      } else {
         return exp;
      }
   }

   public Expression readE(int precedence) {
      Expression exp = this.readT();

      String token;
      for(token = this.nextToken(); this.takesPrecedence(token, precedence); token = this.nextToken()) {
         Operator op = this.getOperator(token);
         if (op.isStatement()) {
            throw new SyntaxError("Illegal context for " + this.markCode("" + op));
         }

         exp = op.infixAction(this, exp);
      }

      this.saveToken(token);
      return exp;
   }

   public Expression readT() {
      String token = this.nextToken();
      if (token == null) {
         throw new SyntaxError("Unexpected end of line");
      } else {
         switch(this.scanner.getTokenType(token)) {
         case 1:
         case 4:
            Operator op = this.getOperator(token);
            if (op == null) {
               return this.createIdentifier(token);
            } else {
               if (op.isStatement()) {
                  throw new SyntaxError("Illegal context for " + this.markCode("" + op));
               }

               return op.prefixAction(this);
            }
         case 2:
         case 3:
         default:
            return this.parseConstant(token);
         }
      }
   }

   public String unparse(Expression exp) {
      switch(exp.getType()) {
      case 1:
         return this.unparseConstant((Constant)exp);
      case 2:
         return this.unparseIdentifier((Identifier)exp);
      case 3:
         Expression fn = exp.getFunction();
         Expression[] args = exp.getArgs();
         if (fn.getType() == 4) {
            return ((Operator)fn).unparse(this, args);
         } else {
            String result = fn.toString();
            result = result + "(";

            for(int i = 0; i < args.length; ++i) {
               if (i > 0) {
                  result = result + ",";
               }

               result = result + this.unparse(args[i]);
            }

            return result + ")";
         }
      default:
         return exp.toString();
      }
   }

   public String unparseIdentifier(Identifier id) {
      return id.getName();
   }

   public String unparseConstant(Constant c) {
      Value value = c.getValue();
      switch(value.getType()) {
      case 67:
         return unparseChar((Character)value.getValue());
      case 83:
         return unparseString((String)value.getValue());
      default:
         return value.toString();
      }
   }

   public static String unparseString(String str) {
      String result = "\"";
      int len = str.length();

      for(int i = 0; i < len; ++i) {
         int ch = str.charAt(i);
         switch(ch) {
         case '\b':
            result = result + "\\b";
            break;
         case '\t':
            result = result + "\\t";
            break;
         case '\n':
            result = result + "\\n";
            break;
         case '\f':
            result = result + "\\f";
            break;
         case '\r':
            result = result + "\\r";
            break;
         case '"':
            result = result + "\\\"";
            break;
         case '\\':
            result = result + "\\\\";
            break;
         default:
            if (ch >= ' ' && ch < 127) {
               result = result + (char)ch;
            } else {
               String oct;
               if (ch < 256) {
                  oct = "000" + Integer.toString(ch, 8);
                  result = result + "\\" + oct.substring(oct.length() - 3);
               } else {
                  oct = "0000" + Integer.toString(ch, 16).toUpperCase();
                  result = result + "\\u" + oct.substring(oct.length() - 4);
               }
            }
         }
      }

      return result + "\"";
   }

   public static String unparseChar(char ch) {
      String result = "'";
      switch(ch) {
      case '\b':
         result = result + "\\b";
         break;
      case '\t':
         result = result + "\\t";
         break;
      case '\n':
         result = result + "\\n";
         break;
      case '\f':
         result = result + "\\f";
         break;
      case '\r':
         result = result + "\\r";
         break;
      case '\'':
         result = result + "\\'";
         break;
      case '\\':
         result = result + "\\\\";
         break;
      default:
         if (ch >= ' ' && ch < 127) {
            result = result + ch;
         } else {
            String oct;
            if (ch < 256) {
               oct = "000" + Integer.toString(ch, 8);
               result = result + "\\" + oct.substring(oct.length() - 3);
            } else {
               oct = "0000" + Integer.toString(ch, 16).toUpperCase();
               result = result + "\\u" + oct.substring(oct.length() - 4);
            }
         }
      }

      return result + "'";
   }

   public void compile(Expression exp, CodeVector cv) {
      throw new SyntaxError("No compiler defined");
   }

   public void definePrefixOperator(String name, Operator op, int prec) {
      this.defineOperator(name, op, prec, 0, 0);
   }

   public void defineInfixOperator(String name, Operator op, int prec, int assoc) {
      this.defineOperator(name, op, 0, prec, assoc);
   }

   public void defineOperator(String name, Operator op, int prefix, int infix, int assoc) {
      op.setName(name);
      op.setPrefixPrecedence(prefix);
      op.setInfixPrecedence(infix);
      op.setAssociativity(assoc);
      this.operators.put(name, op);
   }

   public void removeOperator(String name) {
      this.operators.remove(name);
   }

   public HashMap<String, Operator> getOperatorTable() {
      return this.operators;
   }

   public Operator getOperator(String name) {
      return (Operator)this.operators.get(name);
   }

   public TokenScanner getTokenScanner() {
      return this.scanner;
   }

   public void setInput(String str) {
      this.scanner.setInput(str);
   }

   public boolean hasMoreTokens() {
      return this.scanner.hasMoreTokens();
   }

   public String nextToken() {
      return this.scanner.nextToken();
   }

   public void saveToken(String token) {
      this.scanner.saveToken(token);
   }

   public int getTokenType(String token) {
      return this.scanner.getTokenType(token);
   }

   public String getStringValue(String token) {
      return this.scanner.getStringValue(token);
   }

   public int getPosition() {
      return this.scanner.getPosition();
   }

   public void verifyToken(String expected) {
      String token = this.nextToken();
      if (!token.equals(expected)) {
         String msg = "";
         if (token.equals("")) {
            msg = "Missing " + this.markCode(expected);
         } else {
            msg = "Found " + this.markCode(token) + " when expecting " + this.markCode(expected);
         }

         throw new SyntaxError(msg);
      }
   }

   public boolean takesPrecedence(String token, int prec) {
      if (token == null) {
         return false;
      } else {
         Operator op = this.getOperator(token);
         if (op == null) {
            return false;
         } else {
            int newprec = op.getInfixPrecedence();
            if (newprec == prec) {
               return op.getAssociativity() == 1;
            } else {
               return newprec > prec;
            }
         }
      }
   }

   public void setMarkCodeFlag(boolean flag) {
      this.markCodeFlag = flag;
   }

   public boolean getMarkCodeFlag() {
      return this.markCodeFlag;
   }

   public String markCode(String str) {
      return this.markCodeFlag ? "<code>" + str + "</code>" : str;
   }

   public TokenScanner createTokenScanner() {
      TokenScanner scanner = new TokenScanner();
      scanner.ignoreWhitespace();
      scanner.scanStrings();
      scanner.scanNumbers();
      return scanner;
   }

   public Identifier createIdentifier(String name) {
      return new Identifier(name);
   }

   public Constant createConstant(Value value) {
      return new Constant(value);
   }

   public Expression parseConstant(String str) {
      int type = this.scanner.getTokenType(str);
      if (type == 2) {
         return str.indexOf(".") >= 0 ? this.createConstant(Value.createDouble(Double.parseDouble(str))) : this.createConstant(Value.createInteger(Integer.parseInt(str)));
      } else if (type == 3) {
         if (str.startsWith("'")) {
            char c = this.scanner.getStringValue(str).charAt(0);
            return this.createConstant(Value.createCharacter(c));
         } else {
            String s = this.scanner.getStringValue(str);
            return this.createConstant(Value.createString(s));
         }
      } else {
         throw new SyntaxError("Illegal constant: " + this.markCode(str));
      }
   }

   public Expression createCompound(Expression op, Expression[] args) {
      return new Compound(op, args);
   }

   public final Expression createCompound0(Expression op) {
      return this.createCompound(op, new Expression[0]);
   }

   public final Expression createCompound1(Expression op, Expression a1) {
      Expression[] args = new Expression[]{a1};
      return this.createCompound(op, args);
   }

   public Expression createCompound2(Expression op, Expression a1, Expression a2) {
      Expression[] args = new Expression[]{a1, a2};
      return this.createCompound(op, args);
   }

   public Expression createCompound3(Expression op, Expression a1, Expression a2, Expression a3) {
      Expression[] args = new Expression[]{a1, a2, a3};
      return this.createCompound(op, args);
   }

   public Expression createCompound4(Expression op, Expression a1, Expression a2, Expression a3, Expression a4) {
      Expression[] args = new Expression[]{a1, a2, a3, a4};
      return this.createCompound(op, args);
   }

   public Expression createCompound5(Expression op, Expression a1, Expression a2, Expression a3, Expression a4, Expression a5) {
      Expression[] args = new Expression[]{a1, a2, a3, a4, a5};
      return this.createCompound(op, args);
   }

   public Expression createCompound6(Expression op, Expression a1, Expression a2, Expression a3, Expression a4, Expression a5, Expression a6) {
      Expression[] args = new Expression[]{a1, a2, a3, a4, a5, a6};
      return this.createCompound(op, args);
   }
}
