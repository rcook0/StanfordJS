package edu.stanford.cs.sjs;

import edu.stanford.cs.exp.Expression;
import edu.stanford.cs.exp.Value;
import edu.stanford.cs.parser.CodeVector;
import edu.stanford.cs.parser.Parser;
import edu.stanford.cs.parser.PrefixOperator;
import edu.stanford.cs.parser.SyntaxError;

public class SJSVarKeyword extends PrefixOperator {
   public Expression prefixAction(Parser p) {
      SJSParser jsp = (SJSParser)p;
      String variable = jsp.nextToken();
      if (jsp.getTokenType(variable) != 1) {
         throw new SyntaxError("Illegal variable name");
      } else {
         jsp.declareLocal(variable);
         String token = jsp.nextToken();
         Expression exp = null;
         if (token.equals("=")) {
            exp = jsp.createCompound2(jsp.getOperator("="), jsp.createIdentifier(variable), jsp.readE(0));
         } else {
            if (!token.equals(";") && !token.equals("in")) {
               throw new SyntaxError("Missing = in declaration");
            }

            jsp.saveToken(token);
            exp = jsp.createCompound2(jsp.getOperator("="), jsp.createIdentifier(variable), jsp.createConstant(Value.UNDEFINED));
         }

         return jsp.createCompound1(this, exp);
      }
   }

   public void compile(Parser p, Expression[] args, CodeVector cv) {
      p.compile(args[0], cv);
   }
}
