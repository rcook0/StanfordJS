package edu.stanford.cs.svm;

import edu.stanford.cs.parser.CodeVector;
import edu.stanford.cs.parser.SyntaxError;
import edu.stanford.cs.tokenscanner.TokenScanner;

abstract class SVMOffsetInstruction extends SVMInstruction {
   public SVMOffsetInstruction(String name, int code) {
      super(name, code);
   }

   public void assemble(CodeVector cv, TokenScanner scanner) {
      String token = scanner.nextToken();
      int type = scanner.getTokenType(token);
      switch(type) {
      case 1:
         cv.addWord(this.getCode() << 24 | cv.getLabel(token));
         break;
      case 2:
         cv.addWord(this.getCode() << 24 | Integer.parseInt(token));
         break;
      default:
         throw new SyntaxError("Illegal argument " + token);
      }

   }

   public String unparse(SVM svm, int addr) {
      return this.getName() + " " + addr;
   }
}
