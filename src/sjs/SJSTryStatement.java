package edu.stanford.cs.sjs;

import edu.stanford.cs.exp.Expression;
import edu.stanford.cs.parser.CodeVector;
import edu.stanford.cs.parser.Parser;
import edu.stanford.cs.parser.Statement;

public class SJSTryStatement extends Statement {
   public Expression prefixAction(Parser p) {
      SJSParser jsp = (SJSParser)p;
      Expression body = jsp.readCompoundStatement();
      jsp.verifyToken("catch");
      jsp.verifyToken("(");
      Expression name = jsp.createIdentifier(jsp.nextToken());
      jsp.verifyToken(")");
      Expression handler = jsp.readCompoundStatement();
      jsp.declareLocal(name.getName());
      return jsp.createCompound3(this, body, name, handler);
   }

   public void compile(Parser p, Expression[] args, CodeVector cv) {
      SJSParser jsp = (SJSParser)p;
      String tag1 = cv.newLabel();
      String tag2 = cv.newLabel();
      jsp.pushStatementContext((String)null, (String)null);
      cv.addInstruction(68, cv.labelRef(tag1));
      jsp.compile(args[0], cv);
      cv.addInstruction(69, 0);
      cv.addInstruction(64, cv.labelRef(tag2));
      cv.defineLabel(tag1);
      cv.addInstruction(109, cv.stringRef(args[1].getName()));
      jsp.compile(args[2], cv);
      cv.defineLabel(tag2);
      jsp.popStatementContext();
   }
}
