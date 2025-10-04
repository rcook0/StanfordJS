package edu.stanford.cs.svm;

import edu.stanford.cs.parser.CodeVector;
import edu.stanford.cs.tokenscanner.TokenScanner;

abstract class SVMStringInstruction extends SVMInstruction {
   public SVMStringInstruction(String name, int code) {
      super(name, code);
   }

   public void assemble(CodeVector cv, TokenScanner scanner) {
      String str = scanner.getStringValue(scanner.nextToken());
      cv.addWord(this.getCode() << 24 | cv.stringRef(str));
   }

   public String unparse(SVM svm, int addr) {
      return this.getName() + " \"" + svm.getString(addr) + "\"";
   }
}
