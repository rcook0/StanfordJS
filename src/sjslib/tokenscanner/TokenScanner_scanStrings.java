package edu.stanford.cs.sjslib.tokenscanner;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class TokenScanner_scanStrings extends TokenScannerMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("TokenScanner.scanStrings", "");
      this.getTokenScanner(svm, receiver).scanStrings();
      svm.push(Value.UNDEFINED);
   }
}
