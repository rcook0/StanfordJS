package edu.stanford.cs.sjs;

import edu.stanford.cs.exp.Expression;
import edu.stanford.cs.parser.CodeVector;
import edu.stanford.cs.parser.Parser;
import edu.stanford.cs.parser.Statement;

public class SJSForStatement extends Statement {
   public Expression prefixAction(Parser p) {
      Expression init = null;
      Expression test = null;
      Expression step = null;
      p.verifyToken("(");
      String token = p.nextToken();
      Expression stmt;
      if (!token.equals(";")) {
         p.saveToken(token);
         init = p.readE(0);
         token = p.nextToken();
         if (token.equals("in")) {
            stmt = p.readE(0);
            p.verifyToken(")");
            Expression stmt = ((SJSParser)p).readCompoundStatement();
            return p.createCompound3(this, init, stmt, stmt);
         }

         p.saveToken(token);
         p.verifyToken(";");
      }

      token = p.nextToken();
      if (!token.equals(";")) {
         p.saveToken(token);
         test = p.readE(0);
         p.verifyToken(";");
      }

      token = p.nextToken();
      if (!token.equals(")")) {
         p.saveToken(token);
         step = p.readE(0);
         p.verifyToken(")");
      }

      stmt = ((SJSParser)p).readCompoundStatement();
      return p.createCompound4(this, init, test, step, stmt);
   }

   public void compile(Parser p, Expression[] args, CodeVector cv) {
      if (args.length == 3) {
         this.compileForeach(p, args, cv);
      } else {
         this.compileFor(p, args, cv);
      }

   }

   private void compileFor(Parser p, Expression[] args, CodeVector cv) {
      SJSParser jsp = (SJSParser)p;
      Expression init = args[0];
      Expression test = args[1];
      Expression step = args[2];
      Expression stmt = args[3];
      String tag1 = cv.newLabel();
      String tag2 = cv.newLabel();
      String tag3 = cv.newLabel();
      jsp.pushStatementContext(tag3, tag2);
      if (init != null) {
         jsp.compile(init, cv);
      }

      cv.defineLabel(tag1);
      if (test != null) {
         jsp.compile(test, cv);
         cv.addInstruction(66, cv.labelRef(tag3));
      }

      jsp.compile(stmt, cv);
      cv.defineLabel(tag2);
      if (step != null) {
         jsp.compile(step, cv);
      }

      cv.addInstruction(64, cv.labelRef(tag1));
      cv.defineLabel(tag3);
      jsp.popStatementContext();
   }

   private void compileForeach(Parser p, Expression[] args, CodeVector cv) {
      SJSParser jsp = (SJSParser)p;
      Expression id = args[0];
      if (id.getType() == 3) {
         id = id.getArgs()[0].getArgs()[0];
      }

      Expression exp = args[1];
      Expression stmt = args[2];
      String tag1 = cv.newLabel();
      String tag2 = cv.newLabel();
      String tag3 = cv.newLabel();
      String index = "i_" + jsp.getStatementDepth();
      String max = "n_" + jsp.getStatementDepth();
      String array = "a_" + jsp.getStatementDepth();
      jsp.pushStatementContext(tag3, tag2);
      jsp.compile(exp, cv);
      cv.addInstruction(104, cv.stringRef(index));
      cv.addInstruction(104, cv.stringRef(max));
      cv.addInstruction(104, cv.stringRef(array));
      cv.addInstruction(97, cv.stringRef("keyArray"));
      cv.addInstruction(106, 0);
      cv.addInstruction(21, 0);
      cv.addInstruction(18, cv.stringRef("length"));
      cv.addInstruction(97, cv.stringRef("Core.select"));
      cv.addInstruction(106, 2);
      cv.addInstruction(109, cv.stringRef(max));
      cv.addInstruction(109, cv.stringRef(array));
      cv.addInstruction(16, 0);
      cv.addInstruction(109, cv.stringRef(index));
      cv.defineLabel(tag1);
      cv.addInstruction(108, cv.stringRef(index));
      cv.addInstruction(108, cv.stringRef(max));
      cv.addInstruction(50, 0);
      cv.addInstruction(66, cv.labelRef(tag3));
      cv.addInstruction(108, cv.stringRef(array));
      cv.addInstruction(108, cv.stringRef(index));
      cv.addInstruction(97, cv.stringRef("Core.select"));
      cv.addInstruction(106, 2);
      cv.addInstruction(109, cv.stringRef(id.getName()));
      jsp.compile(stmt, cv);
      cv.defineLabel(tag2);
      cv.addInstruction(108, cv.stringRef(index));
      cv.addInstruction(16, 1);
      cv.addInstruction(32, 0);
      cv.addInstruction(109, cv.stringRef(index));
      cv.addInstruction(64, cv.labelRef(tag1));
      cv.defineLabel(tag3);
      jsp.popStatementContext();
   }
}
