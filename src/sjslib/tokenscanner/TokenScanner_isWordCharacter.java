package edu.stanford.cs.sjslib.tokenscanner;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.tokenscanner.TokenScanner;

class TokenScanner_isWordCharacter extends TokenScannerMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("TokenScanner.isWordCharacter", "S");
      String ch = svm.popString();
      TokenScanner scanner = this.getTokenScanner(svm, receiver);
      svm.pushBoolean(scanner.isWordCharacter(ch.charAt(0)));
   }
}
