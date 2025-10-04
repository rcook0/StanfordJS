package edu.stanford.cs.sjs;

import edu.stanford.cs.exp.Expression;
import edu.stanford.cs.parser.CodeVector;
import edu.stanford.cs.parser.InfixForm;
import edu.stanford.cs.parser.Parser;
import edu.stanford.cs.parser.SyntaxError;

public class SJSAssignmentOperator extends InfixForm {
   public void compile(Parser p, Expression[] args, CodeVector cv) {
      p.compile(args[1], cv);
      cv.addInstruction(21, 0);
      compileSetLV(p, args[0], cv);
   }

   public boolean isAssignmentOperator() {
      return true;
   }

   public static void compileSetLV(Parser p, Expression exp, CodeVector cv) {
      int type = exp.getType();
      if (type == 3) {
         Expression op = exp.getFunction();
         Expression lhs = exp.getArgs()[0];
         Expression rhs = exp.getArgs()[1];
         if (op.getType() == 4) {
            String name = op.getName();
            if (name.equals(".") && rhs.getType() == 2) {
               p.compile(lhs, cv);
               cv.addInstruction(18, cv.stringRef(rhs.getName()));
            } else {
               if (!name.equals("[")) {
                  throw new SyntaxError("Illegal assignment");
               }

               p.compile(lhs, cv);
               p.compile(rhs, cv);
            }
         }

         cv.addInstruction(22, 3);
         cv.addInstruction(22, 3);
         cv.addInstruction(97, cv.stringRef("Core.assign"));
      } else {
         if (type != 2) {
            throw new SyntaxError("Illegal assignment");
         }

         cv.addInstruction(109, cv.stringRef(exp.getName()));
      }

   }
}
