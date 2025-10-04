package edu.stanford.cs.sjslib.tokenscanner;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class TokenScanner_getTokenType extends TokenScannerMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("TokenScanner.getTokenType", "S");
      String token = svm.popString();
      svm.pushInteger(this.getTokenScanner(svm, receiver).getTokenType(token));
   }
}
