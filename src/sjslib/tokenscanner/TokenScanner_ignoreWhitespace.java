package edu.stanford.cs.sjslib.tokenscanner;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class TokenScanner_ignoreWhitespace extends TokenScannerMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("TokenScanner.ignoreWhitespace", "");
      this.getTokenScanner(svm, receiver).ignoreWhitespace();
      svm.push(Value.UNDEFINED);
   }
}
