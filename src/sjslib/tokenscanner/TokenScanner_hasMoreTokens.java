package edu.stanford.cs.sjslib.tokenscanner;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class TokenScanner_hasMoreTokens extends TokenScannerMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("TokenScanner.hasMoreTokens", "");
      svm.pushBoolean(this.getTokenScanner(svm, receiver).hasMoreTokens());
   }
}
