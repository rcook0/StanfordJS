package edu.stanford.cs.sjs;

import edu.stanford.cs.exp.Expression;
import edu.stanford.cs.parser.CodeVector;
import edu.stanford.cs.parser.Parser;
import edu.stanford.cs.parser.PrefixOperator;
import java.util.ArrayList;

public class SJSFunctionKeyword extends PrefixOperator {
   public Expression prefixAction(Parser p) {
      SJSParser jsp = (SJSParser)p;
      ArrayList<String> oldFunctions = jsp.getFunctions();
      ArrayList<String> oldFormals = jsp.getFormals();
      ArrayList<String> oldLocals = jsp.getLocals();
      Expression fn = jsp.readFunction(false);
      jsp.setFormals(oldFunctions);
      jsp.setFormals(oldFormals);
      jsp.setLocals(oldLocals);
      return fn;
   }

   public void compile(Parser p, Expression[] args, CodeVector cv) {
      cv.addInstruction(19, cv.labelRef(args[0].getName()));
   }
}
