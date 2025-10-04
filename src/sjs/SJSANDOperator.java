package edu.stanford.cs.sjs;

import edu.stanford.cs.exp.Expression;
import edu.stanford.cs.parser.CodeVector;
import edu.stanford.cs.parser.InfixForm;
import edu.stanford.cs.parser.Parser;

public class SJSANDOperator extends InfixForm {
   public void compile(Parser p, Expression[] args, CodeVector cv) {
      String tag1 = cv.newLabel();
      String tag2 = cv.newLabel();
      p.compile(args[0], cv);
      cv.addInstruction(66, cv.labelRef(tag1));
      p.compile(args[1], cv);
      cv.addInstruction(64, cv.labelRef(tag2));
      cv.defineLabel(tag1);
      cv.addInstruction(97, cv.stringRef("Core.FALSE"));
      cv.defineLabel(tag2);
   }
}
