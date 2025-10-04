package edu.stanford.cs.sjslib.tokenscanner;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class TokenScanner_ignoreComments extends TokenScannerMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("TokenScanner.ignoreComments", "");
      this.getTokenScanner(svm, receiver).ignoreComments();
      svm.push(Value.UNDEFINED);
   }
}
