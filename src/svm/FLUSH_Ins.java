package edu.stanford.cs.svm;

import edu.stanford.cs.parser.CodeVector;
import edu.stanford.cs.tokenscanner.TokenScanner;

class FLUSH_Ins extends SVMInstruction {
   public FLUSH_Ins() {
      super("FLUSH", 20);
   }

   public void assemble(CodeVector cv, TokenScanner scanner) {
      String token = scanner.nextToken();
      if (scanner.getTokenType(token) == 2) {
         cv.addWord(335544320 | Integer.parseInt(token));
      } else {
         scanner.saveToken(token);
         cv.addWord(335544321);
      }

   }

   public void execute(SVM svm, int addr) {
      for(int i = 0; i < Math.min(1, addr); ++i) {
         svm.pop();
      }

   }
}
