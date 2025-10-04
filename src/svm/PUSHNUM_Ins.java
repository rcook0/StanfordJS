package edu.stanford.cs.svm;

import edu.stanford.cs.parser.CodeVector;
import edu.stanford.cs.tokenscanner.TokenScanner;

class PUSHNUM_Ins extends SVMInstruction {
   public PUSHNUM_Ins() {
      super("PUSHNUM", 17);
   }

   public void assemble(CodeVector cv, TokenScanner scanner) {
      String token = scanner.nextToken();
      if (token.equals("-")) {
         token = token + scanner.nextToken();
      }

      cv.addWord(this.getCode() << 24 | cv.stringRef(token));
   }

   public void execute(SVM svm, int addr) {
      String str = svm.getString(addr);
      if (str.indexOf(".") == -1 && str.toUpperCase().indexOf("E") == -1) {
         svm.pushInteger(Integer.parseInt(str));
      } else {
         svm.pushDouble(Double.parseDouble(str));
      }

   }

   public String unparse(SVM svm, int addr) {
      return "PUSHNUM " + svm.getString(addr);
   }
}
