package edu.stanford.cs.sjslib.tokenscanner;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class TokenScanner_saveToken extends TokenScannerMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("TokenScanner.saveToken", "S");
      String token = svm.popString();
      this.getTokenScanner(svm, receiver).saveToken(token);
      svm.push(Value.UNDEFINED);
   }
}
