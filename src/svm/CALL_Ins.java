package edu.stanford.cs.svm;

import edu.stanford.cs.parser.CodeVector;
import edu.stanford.cs.parser.SyntaxError;
import edu.stanford.cs.tokenscanner.TokenScanner;

class CALL_Ins extends SVMInstruction {
   public CALL_Ins() {
      super("CALL", 96);
   }

   public void assemble(CodeVector cv, TokenScanner scanner) {
      String token = scanner.nextToken();
      int type = scanner.getTokenType(token);
      if (type == 2) {
         cv.addWord(this.getCode() << 24 | Integer.parseInt(token));
      } else {
         if (type != 1) {
            throw new SyntaxError("Illegal argument " + token);
         }

         String next = scanner.nextToken();
         if (next.equals(".")) {
            token = token + "." + scanner.nextToken();
            cv.addWord(1627389952 | cv.stringRef(token));
         } else {
            scanner.saveToken(next);
            cv.addWord(1610612736 | cv.labelRef(token));
         }
      }

   }

   public void execute(SVM svm, int addr) {
      svm.pushFrame();
      SVMStackFrame cf = svm.getCurrentFrame();
      cf.setReturnAddress(svm.getPC());
      cf.setArgumentCount(svm.getNARGSCount());
      svm.setPC(addr);
   }

   public String unparse(SVM svm, int addr) {
      return "CALL " + addr;
   }
}
