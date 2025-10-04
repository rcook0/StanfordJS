package edu.stanford.cs.svm;

import edu.stanford.cs.parser.CodeVector;
import edu.stanford.cs.tokenscanner.TokenScanner;

class LOCALS_Ins extends SVMOffsetInstruction {
   public LOCALS_Ins() {
      super("LOCALS", 100);
   }

   public void execute(SVM svm, int addr) {
      svm.getCurrentFrame().setFrameSize(addr);
   }

   public void assemble(CodeVector cv, TokenScanner scanner) {
      int nLocals = 0;

      while(true) {
         String token = scanner.nextToken();
         if (token.equals("\n")) {
            scanner.saveToken("\n");
            cv.addWord(this.getCode() << 24 | nLocals);
            return;
         }

         cv.defineSymbol(token, nLocals++);
      }
   }
}
