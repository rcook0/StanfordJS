package edu.stanford.cs.svm;

import edu.stanford.cs.parser.CodeVector;
import edu.stanford.cs.tokenscanner.TokenScanner;

abstract class SVMNameInstruction extends SVMInstruction {
   public SVMNameInstruction(String name, int code) {
      super(name, code);
   }

   public void assemble(CodeVector cv, TokenScanner scanner) {
      String name = "";

      while(true) {
         String token = scanner.nextToken();
         if (token.equals("\n")) {
            scanner.saveToken("\n");
            cv.addWord(this.getCode() << 24 | cv.stringRef(name));
            return;
         }

         name = name + token;
      }
   }
}
