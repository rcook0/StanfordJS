package edu.stanford.cs.sjslib.tokenscanner;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class TokenScanner_getStringValue extends TokenScannerMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("TokenScanner.getStringValue", "S");
      String token = svm.popString();
      svm.pushString(this.getTokenScanner(svm, receiver).getStringValue(token));
   }
}
